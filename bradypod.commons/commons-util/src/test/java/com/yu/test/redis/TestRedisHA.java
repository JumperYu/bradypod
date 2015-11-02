package com.yu.test.redis;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import com.bradypod.util.thread.ThreadPool;
import com.bradypod.util.thread.ThreadWorker;

/**
 * 测试redis高可用性
 *
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015年10月28日 上午9:02:45
 */
public class TestRedisHA {

	static final int THREADS = 100; // 并发线程数
	static final int TIMES = 10; // 次数
	static final int SECONDS = 1; // 时间间隔（秒）

	/**
	 * 测试哨兵模式
	 * 
	 * one master（one sentinel） - one slave
	 */
	@Test
	public void testSentinel() {
		// 哨兵初始化
		HostAndPort sentinelAddr = new HostAndPort("192.168.1.201", 26379);
		Set<String> sentinels = new HashSet<String>();
		sentinels.add(sentinelAddr.toString());
		JedisSentinelPool sentinelPool = new JedisSentinelPool("mymaster", sentinels,
				new GenericObjectPoolConfig());
		// 线程初始化
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		AtomicInteger calcCount = new AtomicInteger(0);
		AtomicInteger failCount = new AtomicInteger(0);
		for (int t = 0; t < TIMES; t++) {
			ThreadPool threadPool = new ThreadPool(THREADS);
			SentinelThread sentinelThread = new SentinelThread(sentinelPool, calcCount, failCount);
			threadPool.executeThread(sentinelThread);
			try {
				TimeUnit.SECONDS.sleep(SECONDS);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				System.err.println("error !!!");
			}
		}
		sentinelPool.close();
		stopWatch.stop();
		// 打印结果
		System.out.println(String.format(
				"redis sentinel work finish, times:%d(milliseconds), fails:%d",
				stopWatch.getTime(), failCount.get()));
	}

	@Test
	public void testBoolean() {
		AtomicBoolean atomicBoolean = new AtomicBoolean(true);
		System.out.println(atomicBoolean.getAndSet(false));
		System.out.println(atomicBoolean.getAndSet(true));
	}

	// <-- 哨兵工作线程
	class SentinelThread extends ThreadWorker {
		// TODO 线程池如果关闭
		JedisSentinelPool sentinelPool;
		AtomicInteger failCount;
		AtomicInteger calcCount;

		// 初始化
		public SentinelThread(JedisSentinelPool sentinelPool, AtomicInteger calcCount,
				AtomicInteger failCount) {
			this.sentinelPool = sentinelPool;
			this.calcCount = calcCount;
			this.failCount = failCount;
		}

		@Override
		public void execute() {
			try (Jedis jedis = sentinelPool.getResource()) {
				String ret = jedis.setex("" + failCount.incrementAndGet(), 10000, Thread
						.currentThread().getName());
				if (ret == null || !"OK".equals(ret))
					System.err.println("" + failCount.get() + " fail");
			} catch (Exception e) {
				// 记录错误的次数
				failCount.incrementAndGet();
				System.err.println(e.getMessage());
				Thread.currentThread().interrupt();
			}
		}// --> end function execute

	}// --> end class sentinel thread
}
