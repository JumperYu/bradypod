package com.bradypod.common.aop;

import java.lang.annotation.Annotation;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import redis.clients.jedis.Jedis;

import com.yu.util.redis.RedisPool;
import com.yu.util.validate.AssertUtil;

/**
 * Redis缓存AOP拦截解决方案结合注解@RedisCache
 *
 * @author zengxm
 * @date 2015年8月10日
 *
 */
@Aspect
@Component
public class RedisAOP {

	static Logger log = LoggerFactory.getLogger(RedisAOP.class);

	// @Before("")
	public void beforeCache() {
	}

	// @After("")
	public void afterCache() {

	}

	// @AfterReturning("")
	public void afterReturning() {

	}

	/**
	 * 当抛出异常时
	 */
	@AfterThrowing(value = "@annotation(cache)", throwing = "e")
	public void afterThrowing(JoinPoint jp, RedisCache cache, Exception e) {
		String key = getCacheKey(jp, cache);
		log.error(String.format("从redis中的%s获取缓存出现异常,[%s], 必须删除它", key,
				e.getMessage()));
		try (Jedis jedis = RedisPool.getJedis()) {
			// TODO 判断
			jedis.del(key.getBytes());
		} catch (Exception ex) {
			// TODO: handle exception
		}
	}

	@Around("@annotation(cache)")
	public Object around(ProceedingJoinPoint pjp, RedisCache cache)
			throws Throwable {

		String key = getCacheKey(pjp, cache);

		AssertUtil.hasText(key, "使用redis注解缓存错误, 因为无法生成对应的key");

		Object value = null;
		// 先查看缓存
		try (Jedis jedis = RedisPool.getJedis()) {
			byte[] bytes = jedis.get(key.getBytes());
			if (ArrayUtils.isNotEmpty(bytes)) {
				value = SerializationUtils.deserialize(bytes);
				if (value != null) {
					log.info(String.format("从redis的%s中获取到缓存值", key));
					return value;
				} else {
					// TODO 为空如何处理
				}
			} else {
				log.info(String.format("无法从redis的%s中获取到缓存值", key));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		value = pjp.proceed();

		// 设置缓存
		if (value != null) {
			try (Jedis jedis = RedisPool.getJedis()) {
				jedis.set(key.getBytes(), SerializationUtils.serialize(value));
				jedis.expire(key.getBytes(), cache.expire());
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		return value;
	}

	/**
	 * 拼接redis的key
	 * 
	 * @param pjp
	 * @param cache
	 * @return String
	 */
	private String getCacheKey(JoinPoint pjp, RedisCache cache) {
		StringBuilder buf = new StringBuilder();
		if (StringUtils.isBlank(cache.key())) {
			buf.append(pjp.getSignature().getDeclaringTypeName())
					.append(CONCAT_STRING).append(pjp.getSignature().getName());
			Object[] args = pjp.getArgs();
			Annotation[][] pas = ((MethodSignature) pjp.getSignature())
					.getMethod().getParameterAnnotations();
			for (int i = 0; i < pas.length; i++) {
				for (Annotation an : pas[i]) {
					if (an instanceof RedisCacheKey) {
						buf.append(CONCAT_STRING).append(args[i].toString());
						break;
					}
				}
			}
		} else {
			buf.append(cache.key());
		}

		return buf.toString();
	}

	private static final String CONCAT_STRING = ".";
}
