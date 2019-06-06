package edu.uark.cartapp.models.api.fields;

import edu.uark.cartapp.models.api.interfaces.FieldNameInterface;

/* ==== APP ProductListingFieldName.java ====*/
public enum ProductListingFieldName implements FieldNameInterface {
	PRODUCTS();
	private String fieldName;

	ProductListingFieldName() {
		this.fieldName = "products";
	}

	public String getFieldName() { return this.fieldName; }
}