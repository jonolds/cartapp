package edu.uark.cartapp.models.api

import edu.uark.cartapp.models.api.fields.ProductListingFieldName
import edu.uark.cartapp.models.api.interfaces.LoadFromJsonInterface
import org.json.JSONException
import org.json.JSONObject
import java.util.*

/* ==== APP ProductListing.java ====*/
class ProductListing : LoadFromJsonInterface<ProductListing> {
	private var products: MutableList<Product>? = null

	init {
		this.products = LinkedList()
	}

	fun getProducts(): List<Product>? {
		return this.products
	}

	fun setProducts(products: MutableList<Product>): ProductListing {
		this.products = products
		return this
	}

	fun addProduct(product: Product): ProductListing {
		this.products!!.add(product)
		return this
	}

	override fun loadFromJson(rawJsonObject: JSONObject): ProductListing {
		val jsonActivities = rawJsonObject.optJSONArray(ProductListingFieldName.PRODUCTS.fieldName)

		if (jsonActivities != null) {
			try {
				for (i in 0 until jsonActivities.length()) {
					val jsonActivity = jsonActivities.getJSONObject(i)
					this.products!!.add(Product().loadFromJson(jsonActivity))
				}
			} catch (e: JSONException) {
				e.printStackTrace()
			}
		}
		return this
	}
}