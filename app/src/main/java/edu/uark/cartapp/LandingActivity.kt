package edu.uark.cartapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

import edu.uark.cartapp.models.transition.ProductTransition

/* ==== APP LandingActivity.java ====*/
class LandingActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_landing)
	}

	fun displayAllProductsButtonOnClick(view: View) {
		this.startActivity(Intent(applicationContext, ProductsListingActivity::class.java))
	}

	fun createProductButtonOnClick(view: View) {
		val intent = Intent(applicationContext, CreateProductActivity::class.java)
		intent.putExtra(getString(R.string.intent_extra_product), ProductTransition())
		this.startActivity(intent)
	}
}