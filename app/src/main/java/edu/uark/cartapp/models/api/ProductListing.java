package edu.uark.cartapp.models.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import edu.uark.cartapp.models.api.fields.ProductListingFieldName;
import edu.uark.cartapp.models.api.interfaces.LoadFromJsonInterface;

public class ProductListing implements LoadFromJsonInterface<ProductListing> {
	private List<Product> products;
	public List<Product> getProducts() {
		return this.products;
	}
	public ProductListing setProducts(List<Product> products) {
		this.products = products;
		return this;
	}
	public ProductListing addProduct(Product product) {
		this.products.add(product);
		return this;
	}

	@Override
	public ProductListing loadFromJson(JSONObject rawJsonObject) {
		JSONArray jsonActivities = rawJsonObject.optJSONArray(ProductListingFieldName.PRODUCTS.getFieldName());

		if (jsonActivities != null) {
			try {
				for (int i = 0; i < jsonActivities.length(); i++) {
					JSONObject jsonActivity = jsonActivities.getJSONObject(i);

					this.products.add((new Product()).loadFromJson(jsonActivity));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return this;
	}

	public ProductListing() {
		this.products = new LinkedList<Product>();
	}
}
