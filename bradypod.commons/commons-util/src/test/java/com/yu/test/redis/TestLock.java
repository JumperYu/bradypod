package com.yu.test.redis;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.time.StopWatch;

import com.bradypod.util.redis.RedisFactory;
import com.bradypod.util.redis.RedisTemplate;
import com.bradypod.util.redis.lock.RedisLock;
import com.bradypod.util.thread.ThreadPool;
import com.bradypod.util.thread.ThreadWorker;

/**
 * 测试锁的程序
 *
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2015年12月12日 上午10:53:59
 */
public class TestLock {

	private int count = 0;

	private String key = "test:lock";

	private RedisFactory redisFactory;

	private RedisTemplate redisTemplate;

	private RedisLock redisLock;

	public TestLock() {
		redisFactory = new RedisFactory("192.168.1.201", 7005);
		redisTemplate = new RedisTemplate();
		redisTemplate.setRedisFactory(redisFactory);
		redisLock = new RedisLock(redisTemplate, key, 5);
	}

	/**
	 * 测试多线程并发修改
	 * 
	 * @throws InterruptedException
	 */
	public void increament() {

		int waitTime = 100; // 等待100毫秒
		while (true) {
			try {
				if (redisLock.lock()) {

					TimeUnit.MILLISECONDS.sleep(1); // 等待一会
					
					System.out.println("do it");
					
					// 每次取当前线程的副本变量值
					int currentCount = getCount();
					// +1
					setCount(currentCount + 1);
					// unlock
					redisLock.unlock();
					// break
					break;
				}
				TimeUnit.MILLISECONDS.sleep(RandomUtils.nextInt(waitTime));
			} catch (Exception e) {
				// ignore
				e.printStackTrace();
			}
		}
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public static void main(String[] args) {

		// 记时
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		final TestLock testLock = new TestLock();

		int times = 2;
		while (times > 0) {
			int threads = 5;
			ThreadPool pool = new ThreadPool(threads);
			pool.executeThread(new ThreadWorker() {

				@Override
				public void execute() {
					try {
						testLock.increament();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			// System.out.println("count:" + testLock.getCount());
			times--;
		}

		stopWatch.stop();

		System.out.println("finish time:" + stopWatch.getTime() + ", count:" + testLock.getCount());
	}
}
