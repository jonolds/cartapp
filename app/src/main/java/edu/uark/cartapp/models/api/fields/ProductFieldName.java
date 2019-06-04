package edu.uark.cartapp.models.api.fields;

import edu.uark.cartapp.models.api.interfaces.FieldNameInterface;

/* ==== APP ProductFieldName.java ====*/
public enum ProductFieldName implements FieldNameInterface {
	ID("id"),
	LOOKUP_CODE("lookupCode"),
	COUNT("count"),
	API_REQUEST_STATUS("apiRequestStatus"),
	API_REQUEST_MESSAGE("apiRequestMessage"),
	CREATED_ON("createdOn");

	private String fieldName;

	ProductFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldName() { return this.fieldName; }
}