package edu.uark.cartapp.models.api;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import edu.uark.cartapp.models.api.enums.ProductApiRequestStatus;
import edu.uark.cartapp.models.api.fields.ProductFieldName;
import edu.uark.cartapp.models.api.interfaces.ConvertToJsonInterface;
import edu.uark.cartapp.models.api.interfaces.LoadFromJsonInterface;
import edu.uark.cartapp.models.transition.ProductTransition;

/* ==== APP Product.java ====*/
public class Product implements ConvertToJsonInterface, LoadFromJsonInterface<Product> {
	private UUID id = new UUID(0, 0);
	private String lookupCode = "";
	private int count = -1;
	private Date createdOn  = new Date();
	private ProductApiRequestStatus apiRequestStatus = ProductApiRequestStatus.OK;
	private String apiRequestMessage = StringUtils.EMPTY;

	public Product(){}
	public Product(ProductTransition productTransition) {
		this.id = productTransition.getId();
		this.count = productTransition.getCount();
		this.apiRequestMessage = StringUtils.EMPTY;
		this.createdOn = productTransition.getCreatedOn();
		this.apiRequestStatus = ProductApiRequestStatus.OK;
		this.lookupCode = productTransition.getLookupCode();
	}

	public UUID getId() { return this.id; }
	public Product setId(UUID id) {
		this.id = id;
		return this;
	}

	public String getLookupCode() { return this.lookupCode; }
	public Product setLookupCode(String lookupCode) {
		this.lookupCode = lookupCode;
		return this;
	}

	public int getCount() { return this.count; }
	public Product setCount(int count) {
		this.count = count;
		return this;
	}

	public Date getCreatedOn() { return this.createdOn; }
	public Product setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	public ProductApiRequestStatus getApiRequestStatus() { return this.apiRequestStatus; }
	public Product setApiRequestStatus(ProductApiRequestStatus apiRequestStatus) {
		if (this.apiRequestStatus != apiRequestStatus) {
			this.apiRequestStatus = apiRequestStatus;
		}
		return this;
	}

	public String getApiRequestMessage() { return this.apiRequestMessage; }
	public Product setApiRequestMessage(String apiRequestMessage) {
		if (!StringUtils.equalsIgnoreCase(this.apiRequestMessage, apiRequestMessage)) {
			this.apiRequestMessage = apiRequestMessage;
		}
		return this;
	}

	@Override
	public Product loadFromJson(JSONObject rawJsonObject) {
		String value = rawJsonObject.optString(ProductFieldName.ID.getFieldName());
		if (!StringUtils.isBlank(value)) {
			this.id = UUID.fromString(value);
		}
		this.lookupCode = rawJsonObject.optString(ProductFieldName.LOOKUP_CODE.getFieldName());
		this.count = rawJsonObject.optInt(ProductFieldName.COUNT.getFieldName());

		value = rawJsonObject.optString(ProductFieldName.CREATED_ON.getFieldName());
		if (!StringUtils.isBlank(value)) {
			try {
				this.createdOn = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US).parse(value);
			} catch (ParseException e) { e.printStackTrace(); }
		}

		this.apiRequestMessage = rawJsonObject.optString(ProductFieldName.API_REQUEST_MESSAGE.getFieldName());

		value = rawJsonObject.optString(ProductFieldName.API_REQUEST_STATUS.getFieldName());
		if (!StringUtils.isBlank(value)) {
			this.apiRequestStatus = ProductApiRequestStatus.mapName(value);
		}
		return this;
	}

	@Override
	public JSONObject convertToJson() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(ProductFieldName.ID.getFieldName(), this.id.toString());
			jsonObject.put(ProductFieldName.LOOKUP_CODE.getFieldName(), this.lookupCode);
			jsonObject.put(ProductFieldName.COUNT.getFieldName(), this.count);
			jsonObject.put(ProductFieldName.CREATED_ON.getFieldName(), (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US)).format(this.createdOn));
			jsonObject.put(ProductFieldName.API_REQUEST_MESSAGE.getFieldName(), this.apiRequestMessage);
			jsonObject.put(ProductFieldName.API_REQUEST_STATUS.getFieldName(), this.apiRequestStatus.name());
		} catch (JSONException e) { e.printStackTrace(); }
		return jsonObject;
	}
}