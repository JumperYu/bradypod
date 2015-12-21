package com.yu.test.redis;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.time.StopWatch;

import com.bradypod.util.redis.RedisFactory;
import com.bradypod.util.redis.RedisTemplate;
import com.bradypod.util.redis.lock.RedisLock;

public class TestLock {

	String key = "test:lock";
	int expireTime = 2;
	RedisFactory redisFactory = new RedisFactory("localhost", 6379);
	RedisTemplate redisTemplate = new RedisTemplate();

	private int number = 0;

	public TestLock() {
		redisTemplate.setRedisFactory(redisFactory);
	}

	public void execute() {
		try (RedisLock redisLock = new RedisLock(key, expireTime, redisTemplate)) {
			redisLock.lock();
			// redisTemplate.incr("test:count");
			int num = getNumber();
			setNumber(++num);
		}
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Long getCount() {
		return redisTemplate.getLongValue("test:count");
	}

	public static void main(String[] args) {
		final TestLock testLock = new TestLock();

		int times = 5;

		for (int t = 0; t < times; t++) {
			StopWatch stopWatch = new StopWatch();
			stopWatch.start();
			int threads = 1000;
			final CountDownLatch threadCount = new CountDownLatch(threads);
			final CountDownLatch mainCount = new CountDownLatch(1);
			for (int i = 0; i < threads; i++) {
				Thread thread = new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							mainCount.await();
							testLock.execute();
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							threadCount.countDown();
						}
					}
				});
				thread.start();
			}
			mainCount.countDown();
			try {
				threadCount.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			stopWatch.stop();
			System.out.println("finished cost: " + stopWatch.getTime() + ", num:"
					+ testLock.getNumber());
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
