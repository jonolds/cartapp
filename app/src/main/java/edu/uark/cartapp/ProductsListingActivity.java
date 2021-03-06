package edu.uark.cartapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import edu.uark.cartapp.adapters.ProductListAdapter;
import edu.uark.cartapp.models.api.Product;
import edu.uark.cartapp.models.api.services.ProductService;
import edu.uark.cartapp.models.transition.ProductTransition;

/* ==== APP ProductsListingActivity.java ====*/
public class ProductsListingActivity extends AppCompatActivity {
	private List<Product> products;
	private AlertDialog loadingProductsAlert;
	private ProductListAdapter productListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_products_listing);

		ActionBar actionBar = this.getSupportActionBar();
		if (actionBar != null) {
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}

		this.products = new ArrayList<>();
		this.productListAdapter = new ProductListAdapter(this, this.products);
		this.getProductsListView().setAdapter(this.productListAdapter);
		this.getProductsListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getApplicationContext(), CreateProductActivity.class);
				intent.putExtra(getString(R.string.intent_extra_product), new ProductTransition((Product) getProductsListView().getItemAtPosition(position)));
				startActivity(intent);
			}
		});

		this.loadingProductsAlert = new AlertDialog.Builder(this).setMessage(R.string.alert_dialog_products_loading).create();
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.loadingProductsAlert.show();
		(new RetrieveProductsTask()).execute();
	}

	private ListView getProductsListView() {
		return (ListView) this.findViewById(R.id.list_view_products);
	}

	private class RetrieveProductsTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			products.clear();
			products.addAll((new ProductService()).getProducts());
			return null;
		}

		@Override
		protected void onPostExecute(Void param) {
			productListAdapter.notifyDataSetChanged();
			loadingProductsAlert.dismiss();
		}
	}
}