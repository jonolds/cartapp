package edu.uark.cartapp.commands.converters;

import java.nio.ByteBuffer;
import java.util.UUID;

import edu.uark.cartapp.commands.interfaces.ResultInterface;

/* ==== APP ByteToUUIDConverterCommand.java ====*/
public class ByteToUUIDConverterCommand implements ResultInterface<UUID> {
	private byte[] valueToConvert;

	@Override
	public UUID execute() {
		ByteBuffer byteBuffer = ByteBuffer.wrap(this.valueToConvert);
		return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
	}

	public byte[] getValueToConvert() {
		return this.valueToConvert;
	}

	public ByteToUUIDConverterCommand setValueToConvert(byte[] valueToConvert) {
		this.valueToConvert = valueToConvert;
		return this;
	}
}