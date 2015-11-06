package com.bradypod.util.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 并发启动线程执行
 *
 * @author zengxm
 * @date 2015年8月6日
 *
 */
public class ThreadPool {

	private int count;
	private ExecutorService pool;

	public ThreadPool(int count) {
		this.count = count;
	}

	/**
	 * 执行线程
	 * 
	 * @param threadWorker
	 *            -- 需要实现的具体执行类
	 */
	public void executeThread(final ThreadWorker threadWorker) {
		// 手动添加对象
		final CountDownLatch latch = new CountDownLatch(count);
		pool = Executors.newFixedThreadPool(count);
		for (int i = 0; i < count; i++) {
			pool.execute(new Runnable() {
				@Override
				public void run() {
					try {
						threadWorker.execute();
					} finally {
						latch.countDown();
					}
				}
			});
		}
		try {
			latch.await();
			pool.shutdown();
			// Wait a while for existing tasks to terminate
			if (!pool.awaitTermination(MAX_AWAIT_TIME, TimeUnit.SECONDS)) {
				pool.shutdownNow(); // Cancel currently executing tasks
				// Wait a while for tasks to respond to being cancelled
				if (!pool.awaitTermination(MAX_AWAIT_TIME, TimeUnit.SECONDS)) {
					System.err
							.println("---------------- Pool did not terminate --------------------");
				}
			}
		} catch (InterruptedException e) {
			// ignore message
			// (Re-)Cancel if current thread also interrupted
			pool.shutdownNow();
			// Preserve interrupt status
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * 强制终止线程池里的线程, 会出现InterruptedException
	 * 
	 */
	public void closeInDanger() {
		pool.shutdownNow();
	}

	private static final int MAX_AWAIT_TIME = 60;
}
