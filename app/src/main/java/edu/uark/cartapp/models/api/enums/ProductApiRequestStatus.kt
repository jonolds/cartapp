package edu.uark.cartapp.models.api.enums

import android.util.SparseArray

import java.util.HashMap

/* ==== APP ProductApiRequestStatus.java ====*/
enum class ProductApiRequestStatus private constructor(val value: Int) {
	OK(0),
	INVALID_INPUT(1),
	UNKNOWN_ERROR(2),
	NOT_FOUND(3),
	LOOKUP_CODE_ALREADY_EXISTS(4);

	companion object {
		private var nameMap: MutableMap<String, ProductApiRequestStatus>? = null
		private var valueMap: SparseArray<ProductApiRequestStatus>? = null

		fun mapValue(key: Int): ProductApiRequestStatus {
			if (valueMap == null) {
				valueMap = SparseArray()

				for (status in ProductApiRequestStatus.values()) {
					valueMap!!.put(status.value, status)
				}
			}
			return if (valueMap!!.indexOfKey(key) >= 0) valueMap!!.get(key) else ProductApiRequestStatus.UNKNOWN_ERROR
		}

		fun mapName(name: String): ProductApiRequestStatus? {
			if (nameMap == null) {
				nameMap = HashMap()

				for (status in ProductApiRequestStatus.values()) {
					nameMap!![status.name] = status
				}
			}
			return if (nameMap!!.containsKey(name)) nameMap!![name] else ProductApiRequestStatus.UNKNOWN_ERROR
		}
	}
}
