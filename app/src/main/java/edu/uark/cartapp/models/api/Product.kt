package edu.uark.cartapp.models.api

import edu.uark.cartapp.models.api.enums.ProductApiRequestStatus
import edu.uark.cartapp.models.api.fields.ProductFieldName
import edu.uark.cartapp.models.api.interfaces.ConvertToJsonInterface
import edu.uark.cartapp.models.api.interfaces.LoadFromJsonInterface
import org.apache.commons.lang3.StringUtils
import org.json.JSONException
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/* ==== APP Product.java ====*/
class Product : ConvertToJsonInterface, LoadFromJsonInterface<Product> {
	private var id = UUID(0, 0)
	private var lookupCode = ""
	private var count = -1
	private var createdOn = Date()
	private var apiRequestStatus = ProductApiRequestStatus.OK
	private var apiRequestMessage = StringUtils.EMPTY

	fun getId(): UUID = this.id
	fun setId(id: UUID): Product {
		this.id = id
		return this
	}

	fun getLookupCode(): String = this.lookupCode
	fun setLookupCode(lookupCode: String): Product {
		this.lookupCode = lookupCode
		return this
	}

	fun getCount(): Int = this.count
	fun setCount(count: Int): Product {
		this.count = count
		return this
	}

	fun getCreatedOn(): Date = this.createdOn

	fun getApiRequestStatus(): ProductApiRequestStatus = this.apiRequestStatus
	fun setApiRequestStatus(apiRequestStatus: ProductApiRequestStatus): Product {
		if (this.apiRequestStatus != apiRequestStatus) {
			this.apiRequestStatus = apiRequestStatus
		}
		return this
	}

	override fun loadFromJson(rawJsonObject: JSONObject): Product {
		var value = rawJsonObject.optString(ProductFieldName.ID.fieldName)
		if (!StringUtils.isBlank(value)) {
			this.id = UUID.fromString(value)
		}
		this.lookupCode = rawJsonObject.optString(ProductFieldName.LOOKUP_CODE.fieldName)
		this.count = rawJsonObject.optInt(ProductFieldName.COUNT.fieldName)

		value = rawJsonObject.optString(ProductFieldName.CREATED_ON.fieldName)
		if (!StringUtils.isBlank(value)) {
			try {
				this.createdOn = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US).parse(value)
			} catch (e: ParseException) {
				e.printStackTrace() }
		}

		this.apiRequestMessage = rawJsonObject.optString(ProductFieldName.API_REQUEST_MESSAGE.fieldName)

		value = rawJsonObject.optString(ProductFieldName.API_REQUEST_STATUS.fieldName)
		if (!StringUtils.isBlank(value)) {
			this.apiRequestStatus = ProductApiRequestStatus.mapName(value)!!
		}
		return this
	}

	override fun convertToJson(): JSONObject {
		println("kotlin")
		val jsonObject = JSONObject()
		try {
			jsonObject.put(ProductFieldName.ID.fieldName, this.id.toString())
			jsonObject.put(ProductFieldName.LOOKUP_CODE.fieldName, this.lookupCode)
			jsonObject.put(ProductFieldName.COUNT.fieldName, this.count)
			jsonObject.put(ProductFieldName.CREATED_ON.fieldName, SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US).format(this.createdOn))
			jsonObject.put(ProductFieldName.API_REQUEST_MESSAGE.fieldName, this.apiRequestMessage)
			jsonObject.put(ProductFieldName.API_REQUEST_STATUS.fieldName, this.apiRequestStatus.name)
		} catch (e: JSONException){ e.printStackTrace() }
		return jsonObject
	}
}