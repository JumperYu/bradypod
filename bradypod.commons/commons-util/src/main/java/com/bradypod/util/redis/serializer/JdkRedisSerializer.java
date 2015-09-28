package com.bradypod.util.redis.serializer;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;

public class JdkRedisSerializer implements RedisSerializer<Object> {

	private Converter<Object, byte[]> serializer = new SerializingConverter();

	private Converter<byte[], Object> deserializer = new DeserializingConverter();

	@Override
	public Object deserialize( byte[] bytes ) {
		try {
			return deserializer.convert( bytes );
		} catch( Exception ex ) {
			throw new RuntimeException( "Cannot deserialize", ex );
		}
	}

	@Override
	public byte[] serialize( Object object ) {
		try {
			return serializer.convert( object );
		} catch( Exception ex ) {
			throw new RuntimeException( "Cannot serialize", ex );
		}
	}

}
