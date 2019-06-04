package edu.uark.cartapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Locale;

import edu.uark.cartapp.models.api.Product;
import edu.uark.cartapp.models.api.enums.ProductApiRequestStatus;
import edu.uark.cartapp.models.api.services.ProductService;
import edu.uark.cartapp.models.transition.ProductTransition;

/* ==== APP CreateProductActivity.java ====*/
public class CreateProductActivity extends AppCompatActivity {
	private AlertDialog savingProductAlert;
	private ProductTransition productTransition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_product);
		ActionBar actionBar = this.getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
		this.productTransition = this.getIntent().getParcelableExtra(this.getString(R.string.intent_extra_product));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Respond to the action bar's Up/Home button
		if (item.getItemId() == android.R.id.home) {
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.getProductLookupCodeEditText().setText(this.productTransition.getLookupCode());
		this.getProductCountEditText().setText(String.format(Locale.getDefault(), "%d", this.productTransition.getCount()));
		this.getProductCreatedOnEditText().setText((new SimpleDateFormat("MM/dd/yyyy", Locale.US)).format(this.productTransition.getCreatedOn()));
	}

	public void saveButtonOnClick(View view) {
		if (!this.validateInput()) {
			return;
		}
		this.savingProductAlert = new AlertDialog.Builder(this).setMessage(R.string.alert_dialog_product_save).create();

		(new SaveActivityTask(this, this.getProductLookupCodeEditText().getText().toString(), Integer.parseInt(this.getProductCountEditText().getText().toString()))).execute();
	}

	private EditText getProductLookupCodeEditText() {
		return (EditText) this.findViewById(R.id.edit_text_product_lookup_code);
	}
	private EditText getProductCountEditText() {
		return (EditText) this.findViewById(R.id.edit_text_product_count);
	}
	private EditText getProductCreatedOnEditText() {
		return (EditText) this.findViewById(R.id.edit_text_product_created_on);
	}

	private class SaveActivityTask extends AsyncTask<Void, Void, Boolean> {
		private int count;
		private String lookupCode;
		private CreateProductActivity activity;

		private SaveActivityTask(CreateProductActivity activity, String lookupCode, int count) {
			this.count = count;
			this.activity = activity;
			this.lookupCode = lookupCode;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			Product product = (new ProductService()).putProduct((new Product()).setId(productTransition.getId()).setLookupCode(this.lookupCode).setCount(this.count));

			if (product.getApiRequestStatus() == ProductApiRequestStatus.OK) {
				productTransition.setCount(this.count);
				productTransition.setLookupCode(this.lookupCode);
			}
			return (product.getApiRequestStatus() == ProductApiRequestStatus.OK);
		}

		@Override
		protected void onPostExecute(Boolean successfulSave) {
			savingProductAlert.dismiss();
			String message = (successfulSave) ? getString(R.string.MSG_product_save_success) : getString(R.string.MSG_product_save_fail);
			new AlertDialog.Builder(this.activity).setMessage(message).setPositiveButton(R.string.button_dismiss, (dialog, id) -> dialog.dismiss()).create().show();
		}
	}

	private boolean validateInput() {
		boolean inputIsValid = true;
		String validationMessage = StringUtils.EMPTY;

		if (StringUtils.isBlank(this.getProductLookupCodeEditText().getText().toString())) {
			validationMessage = this.getString(R.string.validation_product_lookup_code);
			inputIsValid = false;
		}

		if (!inputIsValid && StringUtils.isBlank(this.getProductCountEditText().getText().toString())) {
			validationMessage = this.getString(R.string.validation_product_count);
			inputIsValid = false;
		}

		try {
			if (Integer.parseInt(this.getProductCountEditText().getText().toString()) < 0) {
				validationMessage = this.getString(R.string.validation_product_count);
				inputIsValid = false;
			}
		} catch (NumberFormatException nfe) {
			validationMessage = this.getString(R.string.validation_product_count);
			inputIsValid = false;
		}

		if (!inputIsValid) {
			new AlertDialog.Builder(this).setMessage(validationMessage).setPositiveButton(R.string.button_dismiss, (dialog, id) -> dialog.dismiss()).create().show();
		}
		return inputIsValid;
	}
}