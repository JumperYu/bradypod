package com.bradypod.util.redis.serializer;

import java.nio.charset.Charset;

import com.yu.util.validate.AssertUtil;

/**
 * 数字序列化
 *
 * @author zengxm
 * @date 2015年10月3日
 *
 */
public class NumberRedisSerializer {

	private final Charset charset;

	public NumberRedisSerializer() {
		this(Charset.forName("UTF8"));
	}

	public NumberRedisSerializer(Charset charset) {
		AssertUtil.notNull(charset);
		this.charset = charset;
	}

	public byte[] serialize(Number value) {
		AssertUtil.notNull(value);
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
			throw new RuntimeException(
					"Prefer number type : [Integer,Long,Float,Double]");
		}
	}
	
	/* 解析不同的数字类型  */
	public Long deserializeLong(byte[] bytes) {
		return (bytes == null ? null : Long
				.parseLong(new String(bytes, charset)));
	}

	public Byte deserializeByte(byte[] bytes) {
		return (bytes == null ? null : Byte
				.parseByte(new String(bytes, charset)));
	}

	public Float deserializeFloat(byte[] bytes) {
		return (bytes == null ? null : Float.parseFloat(new String(bytes,
				charset)));
	}

	public Double deserializeDouble(byte[] bytes) {
		return (bytes == null ? null : Double.parseDouble(new String(bytes,
				charset)));
	}

	public Integer deserializeInteger(byte[] bytes) {
		return (bytes == null ? null : Integer.parseInt(new String(bytes,
				charset)));
	}

}
