package com.bradypod.common.aop;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.time.StopWatch;
import org.apache.ibatis.ognl.Ognl;
import org.apache.ibatis.ognl.OgnlException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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

	static Logger logger = LoggerFactory.getLogger(RedisAOP.class);

	static Map<String, Object> jvmCache = new HashMap<>();

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
	// @AfterThrowing(value = "@annotation(cache)", throwing = "e")
	public void afterThrowing(JoinPoint jp, RedisCache cache, Exception e) {
		/*
		 * String key = getCacheKey(jp, cache);
		 * log.error(String.format("从redis中的%s获取缓存出现异常,[%s], 必须删除它", key,
		 * e.getMessage())); try (Jedis jedis = null) { // TODO 判断
		 * //jedis.del(key.getBytes()); } catch (Exception ex) { // TODO: handle
		 * exception }
		 */
	}
	
	@Around("@annotation(cache)")
	public Object handlerCacheClearAround(ProceedingJoinPoint pjp, RedisCacheClear cache) throws Throwable{
		// 优先执行方法
		Object value = pjp.proceed();
		// 如果原方法顺利执行完毕, 执行清空缓存
		jvmCache.clear();
		// 返回原方法的值
		return value;
	}
	
	@Around("@annotation(cache)")
	public Object around(ProceedingJoinPoint pjp, RedisCache cache)
			throws Throwable {

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		String cacheKey = getCacheKey(pjp, cache);// 1.获取缓存的键值
		
		Object value = jvmCache.get(cacheKey);// 2.优先查找缓存 
		
		if(value == null){
			// handler by method
			value = pjp.proceed();
			// store in cache
			if(value != null){
				jvmCache.put(cacheKey, value);
			}
		}//--> end if 3.如果緩存沒有由原方法产生
		
		stopWatch.stop();

		logger.info("cache key:{},it cost:{}", cacheKey, stopWatch.getTime());

		return value;

	}

	/**
	 * 拼接redis的key
	 * 
	 * @param pjp
	 * @param cache
	 * @return String
	 * @throws OgnlException 
	 */
	public String getCacheKey(JoinPoint pjp, RedisCache cache) throws OgnlException {
		StringBuilder buf = new StringBuilder();
		String className = pjp.getTarget().getClass().getCanonicalName();
		String methodName = pjp.getSignature().getName();

			buf.append(className).append(CONCAT_STRING).append(methodName);
			Object[] args = pjp.getArgs();
			for(Object arg : args){
				buf.append(CONCAT_STRING).append(Ognl.getValue("id", arg));
			}
			System.out.println(buf.toString());
			/*Annotation[][] pas = ((MethodSignature) pjp.getSignature())
					.getMethod().getParameterAnnotations();
			for (int i = 0; i < pas.length; i++) {
				for (Annotation an : pas[i]) {
					if (an instanceof RedisCacheKey) {
						buf.append(CONCAT_STRING).append(args[i].toString());
						break;
					}
				}
			}*/

		return buf.toString();
	}

	private static final String CONCAT_STRING = ":";
	
}
