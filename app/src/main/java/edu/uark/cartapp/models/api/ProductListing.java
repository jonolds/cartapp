package edu.uark.cartapp.models.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import edu.uark.cartapp.models.api.fields.ProductListingFieldName;
import edu.uark.cartapp.models.api.interfaces.LoadFromJsonInterface;

/* ==== APP ProductListing.java ====*/
public class ProductListing implements LoadFromJsonInterface<ProductListing> {
	private List<Product> productList;

	public ProductListing() {
		this.productList = new LinkedList<>();
	}

	public List<Product> getProductList() {
		return this.productList;
	}
	public ProductListing setProductList(List<Product> productList) {
		this.productList = productList;
		return this;
	}

	public ProductListing addProduct(Product product) {
		this.productList.add(product);
		return this;
	}

	@Override
	public ProductListing loadFromJson(JSONObject rawJsonObject) {
		JSONArray jsonActivities = rawJsonObject.optJSONArray(ProductListingFieldName.PRODUCTS.getFieldName());

		if (jsonActivities != null) {
			try {
				for (int i = 0; i < jsonActivities.length(); i++) {
					JSONObject jsonActivity = jsonActivities.getJSONObject(i);
					this.productList.add((new Product()).loadFromJson(jsonActivity));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return this;
	}
}