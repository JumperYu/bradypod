package com.bradypod.util.redis.serializer;

import java.nio.charset.Charset;

import com.alibaba.fastjson.JSON;

public class FastjsonRedisSerializer implements RedisSerializer<Object> {

	@Override
	public byte[] serialize(Object object) {
		return JSON.toJSONBytes(object);
	}

	@Override
	public Object deserialize(byte[] bytes) {
		return JSON.parse(bytes, 0, bytes.length, Charset.forName("UTF8").newDecoder());
	}

}
