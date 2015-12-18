package com.yu.test.redis;

import org.apache.commons.lang.time.StopWatch;

import com.bradypod.util.redis.RedisFactory;
import com.bradypod.util.redis.RedisTemplate;
import com.bradypod.util.redis.lock.RedisLock;
import com.bradypod.util.thread.ThreadPool;
import com.bradypod.util.thread.ThreadWorker;

public class TestLock {

	String key = "test:lock";
	long expireTime = 3;
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
		int threads = 10;
		int counts = 10;
		while (counts > 0) {
			ThreadPool pool = new ThreadPool(threads);
			pool.executeThread(new ThreadWorker() {

				@Override
				public void execute() {
					testLock.execute();
				}
			});
			counts--;
		}
		stopWatch.stop();
		System.out.println("finished cost: " + stopWatch.getTime() + ", num:"
				+ testLock.getCount());
	}

}
