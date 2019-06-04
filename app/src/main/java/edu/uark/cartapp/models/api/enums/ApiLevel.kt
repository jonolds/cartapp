package edu.uark.cartapp.models.api.enums

import edu.uark.cartapp.models.api.interfaces.PathElementInterface
import java.util.*

/* ==== APP ApiLevel.java ====*/
enum class ApiLevel private constructor(private val value: String) : PathElementInterface {
	NONE(""),
	ONE("apiv0");

	override fun getPathValue(): String {
		return value
	}

	companion object {
		private var valueMap: MutableMap<String, ApiLevel>? = null

		fun map(key: String): ApiLevel? {
			if (valueMap == null) {
				valueMap = HashMap()

				for (value in ApiLevel.values()) {
					valueMap!![value.pathValue] = value
				}
			}
			return if (valueMap!!.containsKey(key)) valueMap!![key] else ApiLevel.NONE
		}
	}
}