package edu.uark.cartapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import edu.uark.cartapp.models.transition.ProductTransition;

/* ==== APP LandingActivity.java ====*/
public class LandingActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_landing);
	}

	public void displayAllProductsButtonOnClick(View view) {
		this.startActivity(new Intent(getApplicationContext(), ProductsListingActivity.class));
	}

	public void createProductButtonOnClick(View view) {
		Intent intent = new Intent(getApplicationContext(), CreateProductActivity.class);
		intent.putExtra(getString(R.string.intent_extra_product), new ProductTransition());
		this.startActivity(intent);
	}
}