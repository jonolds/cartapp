package edu.uark.cartapp.models.api.enums;

import android.util.SparseArray;

import java.util.HashMap;
import java.util.Map;

/* ==== APP ProductApiRequestStatus.java ====*/
@SuppressWarnings("UnusedReturnValue")
public enum ProductApiRequestStatus {
	OK(0),
	INVALID_INPUT(1),
	UNKNOWN_ERROR(2),
	NOT_FOUND(3),
	LOOKUP_CODE_ALREADY_EXISTS(4);

	private int value;
	private static Map<String, ProductApiRequestStatus> nameMap = null;
	private static SparseArray<ProductApiRequestStatus> valueMap = null;

	ProductApiRequestStatus(int value) {
		this.value = value;
	}

	public int getValue() { return value; }

	public static ProductApiRequestStatus mapValue(int key) {
		if (valueMap == null) {
			valueMap = new SparseArray<>();

			for (ProductApiRequestStatus status : ProductApiRequestStatus.values()) {
				valueMap.put(status.getValue(), status);
			}
		}
		return ((valueMap.indexOfKey(key) >= 0) ? valueMap.get(key) : ProductApiRequestStatus.UNKNOWN_ERROR);
	}

	public static ProductApiRequestStatus mapName(String name) {
		if (nameMap == null) {
			nameMap = new HashMap<>();

			for (ProductApiRequestStatus status : ProductApiRequestStatus.values()) {
				nameMap.put(status.name(), status);
			}
		}
		return (nameMap.containsKey(name) ? nameMap.get(name) : ProductApiRequestStatus.UNKNOWN_ERROR);
	}
}
