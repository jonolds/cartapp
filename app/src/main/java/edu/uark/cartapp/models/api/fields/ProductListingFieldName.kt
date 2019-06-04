package edu.uark.cartapp.models.api.fields

import edu.uark.cartapp.models.api.interfaces.FieldNameInterface

/* ==== APP ProductListingFieldName.java ====*/
enum class ProductListingFieldName private constructor() : FieldNameInterface {
	PRODUCTS;

	private val fieldName: String

	init {
		this.fieldName = "products"
	}

	override fun getFieldName(): String = this.fieldName

}