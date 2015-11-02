package com.bradypod.util.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 并发启动线程执行
 *
 * @author zengxm
 * @date 2015年8月6日
 *
 */
public class ThreadPool {

	private int count;
	private ExecutorService service;

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
		service = Executors.newFixedThreadPool(count);
		for (int i = 0; i < count; i++) {
			service.execute(new Runnable() {
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
			service.shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 强制终止线程池里的线程, 会出现InterruptedException
	 * 
	 */
	public void closeInDanger() {
		service.shutdownNow();
	}

}
