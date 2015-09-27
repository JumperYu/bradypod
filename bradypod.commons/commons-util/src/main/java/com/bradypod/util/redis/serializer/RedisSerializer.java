package com.bradypod.util.redis.serializer;

import org.springframework.data.redis.serializer.SerializationException;

public interface RedisSerializer<T> {

	/**
	 * 
	 * @param t
	 * @return
	 * @throws SerializationException
	 */
	byte[] serialize(T t) throws SerializationException;

	/**
	 * 
	 * @param bytes
	 * @return
	 * @throws SerializationException
	 */
	T deserialize(byte[] bytes) throws SerializationException;

}
