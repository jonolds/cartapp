package edu.uark.cartapp.models.api.enums

import java.util.HashMap

import edu.uark.cartapp.models.api.interfaces.PathElementInterface

/* ==== APP ProductApiMethod.java ====*/
enum class ProductApiMethod private constructor(private val value: String) : PathElementInterface {
	NONE(""),
	PRODUCT("product"),
	BY_LOOKUP_CODE("byLookupCode"),
	PRODUCTS("products");

	override fun getPathValue(): String {
		return value
	}

	companion object {
		private var valueMap: MutableMap<String, ProductApiMethod>? = null

		fun map(key: String): ProductApiMethod? {
			if (valueMap == null) {
				valueMap = HashMap()

				for (value in ProductApiMethod.values()) {
					valueMap!![value.pathValue] = value
				}
			}
			return if (valueMap!!.containsKey(key)) valueMap!![key] else ProductApiMethod.NONE
		}
	}
}