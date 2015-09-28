package com.bradypod.util.redis.serializer;

import org.apache.commons.lang.SerializationException;

public interface RedisSerializer<T> {

	/**
	 * 
	 * @param t
	 * @return
	 * @throws SerializationException
	 */
	byte[] serialize( T t );

	/**
	 * 
	 * @param bytes
	 * @return
	 * @throws SerializationException
	 */
	T deserialize( byte[] bytes );

}
