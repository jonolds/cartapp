package edu.uark.cartapp.models.api.interfaces;

import org.json.JSONObject;

public interface LoadFromJsonInterface<T> {
	T loadFromJson(JSONObject rawJsonObject);
}
