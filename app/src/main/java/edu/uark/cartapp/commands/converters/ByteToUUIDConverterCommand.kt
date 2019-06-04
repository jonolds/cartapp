package edu.uark.cartapp.commands.converters

import java.nio.ByteBuffer
import java.util.UUID

import edu.uark.cartapp.commands.interfaces.ResultInterface

/* ==== APP ByteToUUIDConverterCommand.java ====*/
class ByteToUUIDConverterCommand : ResultInterface<UUID> {
	private var valueToConvert: ByteArray? = null

	override fun execute(): UUID {
		val byteBuffer = ByteBuffer.wrap(this.valueToConvert)
		return UUID(byteBuffer.long, byteBuffer.long)
	}

	fun getValueToConvert(): ByteArray? {
		return this.valueToConvert
	}

	fun setValueToConvert(valueToConvert: ByteArray): ByteToUUIDConverterCommand {
		this.valueToConvert = valueToConvert
		return this
	}
}