package edu.uark.cartapp

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import edu.uark.cartapp.models.api.Product
import edu.uark.cartapp.models.api.enums.ProductApiRequestStatus
import edu.uark.cartapp.models.api.services.ProductService
import edu.uark.cartapp.models.transition.ProductTransition
import org.apache.commons.lang3.StringUtils
import java.text.SimpleDateFormat
import java.util.*

/* ==== APP CreateProductActivity.java ====*/
class CreateProductActivity : AppCompatActivity() {
	private var savingProductAlert: AlertDialog? = null
	private var productTransition: ProductTransition? = null

	private val productLookupCodeEditText: EditText
		get() = this.findViewById<View>(R.id.edit_text_product_lookup_code) as EditText
	private val productCountEditText: EditText
		get() = this.findViewById<View>(R.id.edit_text_product_count) as EditText
	private val productCreatedOnEditText: EditText
		get() = this.findViewById<View>(R.id.edit_text_product_created_on) as EditText

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_create_product)
		val actionBar = this.supportActionBar
		actionBar?.setDisplayHomeAsUpEnabled(true)
		this.productTransition = this.intent.getParcelableExtra(this.getString(R.string.intent_extra_product))
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		// Respond to the action bar's Up/Home button
		if (item.itemId == android.R.id.home) {
			this.finish()
			return true
		}
		return super.onOptionsItemSelected(item)
	}

	override fun onResume() {
		super.onResume()
		this.productLookupCodeEditText.setText(this.productTransition!!.lookupCode)
		this.productCountEditText.setText(String.format(Locale.getDefault(), "%d", this.productTransition!!.count))
		this.productCreatedOnEditText.setText(SimpleDateFormat("MM/dd/yyyy", Locale.US).format(this.productTransition!!.createdOn))
	}

	fun saveButtonOnClick(view: View) {
		if (!this.validateInput()) {
			return
		}
		this.savingProductAlert = AlertDialog.Builder(this).setMessage(R.string.alert_dialog_product_save).create()

		SaveActivityTask(this, this.productLookupCodeEditText.text.toString(), Integer.parseInt(this.productCountEditText.text.toString())).execute()
	}

	private inner class SaveActivityTask internal constructor(private val activity: CreateProductActivity, private val lookupCode: String, private val count: Int) : AsyncTask<Void, Void, Boolean>() {

		override fun doInBackground(vararg params: Void): Boolean? {
			val product = ProductService().putProduct(Product().setId(productTransition!!.id!!).setLookupCode(this.lookupCode).setCount(this.count))

			if (product.getApiRequestStatus() === ProductApiRequestStatus.OK) {
				productTransition!!.count = this.count
				productTransition!!.lookupCode = this.lookupCode
			}
			return product.getApiRequestStatus() === ProductApiRequestStatus.OK
		}

		override fun onPostExecute(successfulSave: Boolean?) {
			savingProductAlert!!.dismiss()
			val message = if (successfulSave!!) getString(R.string.MSG_product_save_success) else getString(R.string.MSG_product_save_fail)
			AlertDialog.Builder(this.activity).setMessage(message).setPositiveButton(R.string.button_dismiss) { dialog, id -> dialog.dismiss() }.create().show()
		}
	}

	private fun validateInput(): Boolean {
		var inputIsValid = true
		var validationMessage = StringUtils.EMPTY

		if (StringUtils.isBlank(this.productLookupCodeEditText.text.toString())) {
			validationMessage = this.getString(R.string.validation_product_lookup_code)
			inputIsValid = false
		}

		if (!inputIsValid && StringUtils.isBlank(this.productCountEditText.text.toString())) {
			validationMessage = this.getString(R.string.validation_product_count)
			inputIsValid = false
		}

		try {
			if (Integer.parseInt(this.productCountEditText.text.toString()) < 0) {
				validationMessage = this.getString(R.string.validation_product_count)
				inputIsValid = false
			}
		} catch (nfe: NumberFormatException) {
			validationMessage = this.getString(R.string.validation_product_count)
			inputIsValid = false
		}

		if (!inputIsValid) {
			AlertDialog.Builder(this).setMessage(validationMessage).setPositiveButton(R.string.button_dismiss) { dialog, id -> dialog.dismiss() }.create().show()
		}
		return inputIsValid
	}
}