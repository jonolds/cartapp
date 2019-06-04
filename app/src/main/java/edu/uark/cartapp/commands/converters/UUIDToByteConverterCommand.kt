package edu.uark.cartapp.commands.converters

import java.nio.ByteBuffer
import java.util.UUID

import edu.uark.cartapp.commands.interfaces.ResultInterface

/* ==== APP UUIDToByteConverterCommand.java ====*/
class UUIDToByteConverterCommand : ResultInterface<ByteArray> {
	private var valueToConvert: UUID? = null

	fun setValueToConvert(valueToConvert: UUID): UUIDToByteConverterCommand {
		this.valueToConvert = valueToConvert
		return this
	}

	fun getValueToConvert(): UUID? {
		return this.valueToConvert
	}

	override fun execute(): ByteArray {
		val byteBuffer = ByteBuffer.wrap(ByteArray(16))
		byteBuffer.putLong(this.valueToConvert!!.mostSignificantBits)
		byteBuffer.putLong(this.valueToConvert!!.leastSignificantBits)
		return byteBuffer.array()
	}
}