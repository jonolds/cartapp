package edu.uark.cartapp.models.api.interfaces

import org.json.JSONObject

/* ==== APP LoadFromJsonInterface.java ====*/
interface LoadFromJsonInterface<T> {
	fun loadFromJson(rawJsonObject: JSONObject): T
}