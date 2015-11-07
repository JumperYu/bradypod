package com.yu.test.redis;

import com.bradypod.util.redis.elector.RedisElector;

public class TestElector {
	
	public static void main(String[] args) {
		RedisElector redisElector = new RedisElector();
		redisElector.start();
	}
	
}
