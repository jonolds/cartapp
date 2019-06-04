package edu.uark.cartapp.models.api.interfaces;

import org.json.JSONObject;

/* ==== APP LoadFromJsonInterface.java ====*/
public interface LoadFromJsonInterface<T> {
	T loadFromJson(JSONObject rawJsonObject);
}