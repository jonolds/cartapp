package edu.uark.cartapp.models.api.enums;

import java.util.HashMap;
import java.util.Map;

import edu.uark.cartapp.models.api.interfaces.PathElementInterface;

public enum ProductApiMethod implements PathElementInterface {
	NONE(""),
	PRODUCT("product"),
	BY_LOOKUP_CODE("byLookupCode"),
	PRODUCTS("products");

	@Override
	public String getPathValue() {
		return value;
	}

	public static ProductApiMethod map(String key) {
		if (valueMap == null) {
			valueMap = new HashMap<>();

			for (ProductApiMethod value : ProductApiMethod.values()) {
				valueMap.put(value.getPathValue(), value);
			}
		}

		return (valueMap.containsKey(key) ? valueMap.get(key) : ProductApiMethod.NONE);
	}

	private String value;

	private static Map<String, ProductApiMethod> valueMap = null;

	ProductApiMethod(String value) {
		this.value = value;
	}
}
