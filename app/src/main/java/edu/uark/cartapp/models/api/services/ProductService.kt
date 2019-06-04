package edu.uark.cartapp.models.api.services

import edu.uark.cartapp.models.api.Product
import edu.uark.cartapp.models.api.ProductListing
import edu.uark.cartapp.models.api.enums.ApiLevel
import edu.uark.cartapp.models.api.enums.ProductApiMethod
import edu.uark.cartapp.models.api.enums.ProductApiRequestStatus
import edu.uark.cartapp.models.api.interfaces.PathElementInterface
import java.util.*

/* ==== APP ProductService.java ====*/
class ProductService : BaseRemoteService() {

	val products: List<Product>?
		get() {
			val activities: List<Product>?
			val rawJsonObject = this.requestSingle(arrayOf(ProductApiMethod.PRODUCT, ApiLevel.ONE, ProductApiMethod.PRODUCTS))

			if (rawJsonObject != null) {
				activities = ProductListing().loadFromJson(rawJsonObject).getProducts()
			} else {
				activities = ArrayList(0)
			}
			return activities
		}

	fun getProduct(productId: UUID): Product {
		val rawJsonObject = this.requestSingle(arrayOf<PathElementInterface>(ProductApiMethod.PRODUCT, ApiLevel.ONE), productId)

		return if (rawJsonObject != null) {
			Product().loadFromJson(rawJsonObject)
		} else {
			Product().setApiRequestStatus(ProductApiRequestStatus.UNKNOWN_ERROR)
		}
	}

	fun getProductByLookupCode(productLookupCode: String): Product {
		val rawJsonObject = this.requestSingle(arrayOf<PathElementInterface>(ProductApiMethod.PRODUCT, ApiLevel.ONE, ProductApiMethod.BY_LOOKUP_CODE), productLookupCode)

		return if (rawJsonObject != null) {
			Product().loadFromJson(rawJsonObject)
		} else {
			Product().setApiRequestStatus(ProductApiRequestStatus.UNKNOWN_ERROR)
		}
	}

	fun putProduct(product: Product): Product {
		val rawJsonObject = this.putSingle(arrayOf(ProductApiMethod.PRODUCT, ApiLevel.ONE), product.convertToJson())

		return if (rawJsonObject != null) {
			Product().loadFromJson(rawJsonObject)
		} else {
			Product().setApiRequestStatus(ProductApiRequestStatus.UNKNOWN_ERROR)
		}
	}
}