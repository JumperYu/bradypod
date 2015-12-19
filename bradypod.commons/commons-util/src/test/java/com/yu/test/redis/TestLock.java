package com.yu.test.redis;

import org.apache.commons.lang.time.StopWatch;

import com.bradypod.util.redis.RedisFactory;
import com.bradypod.util.redis.RedisTemplate;
import com.bradypod.util.redis.lock.RedisLock;
import com.bradypod.util.thread.ScheduledThreadPool;
import com.bradypod.util.thread.ThreadWorker;

public class TestLock {

	String key = "test:lock";
	int expireTime = 2;
	RedisFactory redisFactory = new RedisFactory("localhost", 6379);
	RedisTemplate redisTemplate = new RedisTemplate();

	public TestLock() {
		redisTemplate.setRedisFactory(redisFactory);
	}

	public void execute() {
		try (RedisLock redisLock = new RedisLock(key, expireTime, redisTemplate)) {
			redisLock.lock();
			redisTemplate.incr("test:count");
		}
	}

	public Long getCount() {
		return redisTemplate.getLongValue("test:count");
	}

	public static void main(String[] args) {
		final TestLock testLock = new TestLock();

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		int threads = 100;
		int delay = 0;
		int period = 1;
		ScheduledThreadPool pool = new ScheduledThreadPool(threads);
		pool.executeAtFixedRate(new ThreadWorker() {

			@Override
			public void execute() {
				testLock.execute();
			}
		}, delay, period);
		stopWatch.stop();
		System.out
				.println("finished cost: " + stopWatch.getTime() + ", num:" + testLock.getCount());
	}

}
