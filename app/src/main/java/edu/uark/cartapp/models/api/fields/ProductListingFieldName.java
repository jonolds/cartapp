package edu.uark.cartapp.models.api.fields;

import edu.uark.cartapp.models.api.interfaces.FieldNameInterface;

public enum ProductListingFieldName implements FieldNameInterface {
	PRODUCTS();
	private String fieldName;
	public String getFieldName() {
		return this.fieldName;
	}

	ProductListingFieldName() {
		this.fieldName = "products";
	}
}
