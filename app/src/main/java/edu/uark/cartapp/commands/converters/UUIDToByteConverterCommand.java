package edu.uark.cartapp.commands.converters;

import java.nio.ByteBuffer;
import java.util.UUID;

import edu.uark.cartapp.commands.interfaces.ResultInterface;

/* ==== APP UUIDToByteConverterCommand.java ====*/
public class UUIDToByteConverterCommand implements ResultInterface<byte[]> {
	private UUID valueToConvert;

	public UUIDToByteConverterCommand setValueToConvert(UUID valueToConvert) {
		this.valueToConvert = valueToConvert;
		return this;
	}
	public UUID getValueToConvert() {
		return this.valueToConvert;
	}

	@Override
	public byte[] execute() {
		ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
		byteBuffer.putLong(this.valueToConvert.getMostSignificantBits());
		byteBuffer.putLong(this.valueToConvert.getLeastSignificantBits());
		return byteBuffer.array();
	}
}