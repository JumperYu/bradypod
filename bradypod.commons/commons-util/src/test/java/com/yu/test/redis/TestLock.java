package com.yu.test.redis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.time.StopWatch;

import com.bradypod.util.redis.RedisFactory;
import com.bradypod.util.redis.RedisTemplate;
import com.bradypod.util.redis.lock.RedisLock;

public class TestLock {

	public static void execute() {
		String key = "test:lock";
		long expireTime = 10;
		RedisFactory redisFactory = new RedisFactory("192.168.1.201", 7005);
		RedisTemplate redisTemplate = new RedisTemplate();
		redisTemplate.setRedisFactory(redisFactory);
		try (RedisLock redisLock = new RedisLock(key, expireTime, redisTemplate)) {
			// redisLock.lock();
			System.out.println(Thread.currentThread().getName() + " do my job");
		}
	}

	public static void main(String[] args) {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		int threads = 10;
		final int counts = 200;
		ExecutorService pool = Executors.newFixedThreadPool(threads);
		while (threads > 0) {
			pool.execute(new Runnable() {

				@Override
				public void run() {
					try {
						TimeUnit.MILLISECONDS.sleep(5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					for (int i = 0; i < counts; i++) {
						TestLock.execute();
					}
				}
			});
			threads--;
		}
		pool.shutdown();
		stopWatch.stop();
		System.out.println("finished cost: " + stopWatch.getTime());
	}

}
