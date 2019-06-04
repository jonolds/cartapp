package edu.uark.cartapp.models.api.fields

import edu.uark.cartapp.models.api.interfaces.FieldNameInterface

/* ==== APP ProductFieldName.java ====*/
enum class ProductFieldName private constructor(private val fieldName: String) : FieldNameInterface {
	ID("id"),
	LOOKUP_CODE("lookupCode"),
	COUNT("count"),
	API_REQUEST_STATUS("apiRequestStatus"),
	API_REQUEST_MESSAGE("apiRequestMessage"),
	CREATED_ON("createdOn");

	override fun getFieldName(): String {
		return this.fieldName
	}
}