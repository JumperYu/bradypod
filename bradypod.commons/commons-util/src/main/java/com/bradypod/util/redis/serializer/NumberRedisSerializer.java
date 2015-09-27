package com.bradypod.util.redis.serializer;

import java.nio.charset.Charset;

import org.springframework.data.redis.serializer.SerializationException;
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
	public byte[] serialize(Number number) throws SerializationException {
		return (number == null ? null : number.toString().getBytes(charset));
	}

	@Override
	public Number deserialize(byte[] bytes) throws SerializationException {
		return (bytes == null ? null : Long
				.parseLong(new String(bytes, charset)));
	}

}
