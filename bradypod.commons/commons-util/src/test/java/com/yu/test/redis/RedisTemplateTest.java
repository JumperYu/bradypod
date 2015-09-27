package com.yu.test.redis;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisTemplateTest {
	
	public static void main(String[] args) {
		
		// 
		JedisConnectionFactory connectionFactory =new JedisConnectionFactory();
		RedisTemplate template = new RedisTemplate();
		template.setConnectionFactory(connectionFactory);
	}
	
}
