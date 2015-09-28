package com.bradypod.util.redis.serializer;

import java.nio.charset.Charset;

import org.springframework.util.Assert;

public class NumberRedisSerializer implements RedisSerializer<Number> {

	private final Charset charset;

	public NumberRedisSerializer() {
		this(Charset.forName("UTF8"));
	}

	public NumberRedisSerializer(Charset charset) {
		Assert.notNull(charset);
		this.charset = charset;
	}

	@Override
	public byte[] serialize(Number value) {
		if (value instanceof Integer) {
			return ((Integer) value).toString().getBytes(charset);
		} else if (value instanceof Long) {
			return ((Long) value).toString().getBytes(charset);
		} else if (value instanceof Float) {
			return ((Float) value).toString().getBytes(charset);
		} else if (value instanceof Double) {
			return ((Double) value).toString().getBytes(charset);
		} else if (value instanceof Byte) {
			return ((Byte) value).toString().getBytes(charset);
		} else {
			throw new RuntimeException("Prefer number type : [Integer,Long,Float,Double]");
		}
	}

	@Override
	public Number deserialize(byte[] bytes) {
		return (bytes == null ? null : Long.parseLong(new String(bytes, charset)));
	}

}
