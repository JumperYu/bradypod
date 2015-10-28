package com.bradypod.util.redis.serializer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yu.util.validate.AssertUtil;

/**
 * Jackson json序列化
 *
 * @author zengxm
 * @date 2015年10月6日
 *
 */
public class JacksonRedisSerializer {

	private ObjectMapper objectMapper;

	public JacksonRedisSerializer() {
		this.objectMapper = new ObjectMapper();
	}

	public JacksonRedisSerializer(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	/*
	 * 序列化和反序列化 优点: 相对于jdk序列化字节小, 直观(json格式) 缺点: 相比二进制序列化字节仍较大,
	 * 序列和反序列化占用的时间比较难优化
	 */

	public <T> T deserialize(byte[] bytes, Class<T> clazz) {
		try {
			return bytes == null ? null : this.objectMapper
					.readValue(bytes, 0, bytes.length, clazz);
		} catch (Exception ex) {
			throw new RuntimeException("Could not read JSON: " + ex.getMessage(), ex);
		}
	}

	public <T> T deserialize(byte[] bytes, TypeReference<T> typeReference) {
		try {
			if (bytes != null)
				return this.objectMapper.readValue(bytes, 0, bytes.length, typeReference);
		} catch (Exception ex) {
			throw new RuntimeException("Could not read JSON: " + ex.getMessage(), ex);
		}
		return null;
	}

	public byte[] serialize(Object t) {

		try {
			return t == null ? null : this.objectMapper.writeValueAsBytes(t);
		} catch (Exception ex) {
			throw new RuntimeException("Could not write JSON: " + ex.getMessage(), ex);
		}
	}

	public void setObjectMapper(ObjectMapper objectMapper) {
		AssertUtil.notNull(objectMapper, "'objectMapper' must not be null");
		this.objectMapper = objectMapper;
	}

}
