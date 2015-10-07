package com.bradypod.util.redis.serializer;

import java.nio.charset.Charset;

import com.yu.util.validate.AssertUtil;

/**
 * 字符串序列化, 转化为字节, 默认UTF-8编码
 *
 * @author    zengxm
 * @date      2015年10月3日
 *
 */
public class StringRedisSerializer implements RedisSerializer<String> {

	private final Charset charset;

	public StringRedisSerializer() {
		this( Charset.forName( "UTF8" ) );
	}

	public StringRedisSerializer( Charset charset ) {
		AssertUtil.notNull( charset );
		this.charset = charset;
	}

	public String deserialize(byte[] bytes) {
		return ( bytes == null ? null : new String( bytes, charset ) );
	}

	public byte[] serialize( String string ){
		return ( string == null ? null : string.getBytes( charset ) );
	}
}
