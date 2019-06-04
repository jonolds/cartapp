package edu.uark.cartapp.models.transition

import android.os.Parcel
import android.os.Parcelable
import edu.uark.cartapp.commands.converters.ByteToUUIDConverterCommand
import edu.uark.cartapp.commands.converters.UUIDToByteConverterCommand
import edu.uark.cartapp.models.api.Product
import org.apache.commons.lang3.StringUtils
import java.util.*

/* ==== APP ProductTransition.java ====*/
class ProductTransition : Parcelable {
	var id: UUID? = null
	var lookupCode: String? = null
	var count: Int = 0
	var createdOn: Date = Date()

	constructor() {
		this.count = -1
		this.id = UUID(0, 0)
		this.createdOn = Date()
		this.lookupCode = StringUtils.EMPTY
	}

	constructor(product: Product) {
		this.id = product.getId()
		this.count = product.getCount()
		this.createdOn = product.getCreatedOn()
		this.lookupCode = product.getLookupCode()
	}

	constructor(productTransitionParcel: Parcel) {
		this.id = ByteToUUIDConverterCommand().setValueToConvert(productTransitionParcel.createByteArray()).execute()
		this.lookupCode = productTransitionParcel.readString()
		this.count = productTransitionParcel.readInt()
		this.createdOn = Date()
		this.createdOn.time = productTransitionParcel.readLong()
	}

	override fun writeToParcel(destination: Parcel, flags: Int) {
		destination.writeByteArray(UUIDToByteConverterCommand().setValueToConvert(this.id!!).execute())
		destination.writeString(this.lookupCode)
		destination.writeInt(this.count)
		destination.writeLong(this.createdOn.time)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object {
		@JvmField
		val CREATOR: Parcelable.Creator<ProductTransition> = object : Parcelable.Creator<ProductTransition> {
			override fun createFromParcel(productTransitionParcel: Parcel): ProductTransition {
				return ProductTransition(productTransitionParcel)
			}

			override fun newArray(size: Int): Array<ProductTransition?> {
				return arrayOfNulls(size)
			}
		}
	}
}