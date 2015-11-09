package com.bradypod.util.redis.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bradypod.util.redis.RedisTemplate;
import com.yu.util.validate.AssertUtil;

/**
 * redis lpush 实现消息入队
 * 
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015年11月9日 下午2:35:43
 */
public class JobProducer {

	private String jobKey; // job key

	private RedisTemplate redisTemplate; // redis connection

	/**
	 * 初始化参数
	 * 
	 * @param jobKey
	 *            - key
	 */
	public JobProducer(String jobKey) {
		this.jobKey = jobKey;
	}

	/**
	 * 初始化参数
	 * 
	 * @param jobKey
	 *            - key
	 * @param redisTemplate
	 *            - redis connection
	 */
	public JobProducer(String jobKey, RedisTemplate redisTemplate) {
		this.jobKey = jobKey;
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 往队伍里面添加元素
	 * 
	 * @param value
	 *            - String
	 */
	public void queue(String value) {
		// assert condition
		AssertUtil.assertNotNull(redisTemplate, "redis is required");
		AssertUtil.assertNotNull(value, "value is null");
		// push element to the left position
		redisTemplate.lpush(jobKey, value);
		// log
		logger.info("producer {} add element {}", jobKey, value);
	}

	/* set redis is important */
	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	private static Logger logger = LoggerFactory.getLogger(JobProducer.class);
}
