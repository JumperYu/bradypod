package com.yu.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RedisCache {

	
	/**
	 * - 设置redis的key, 如果为空则由RedisAOP.getCacheKey(...)生成
	 */
	public String key() default ""; // 缓存key


	/**
	 * - 缓存多少秒,默认无限期
	 */
	public int expire() default 0;

}
