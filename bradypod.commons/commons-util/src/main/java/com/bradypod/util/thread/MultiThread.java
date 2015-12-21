package com.bradypod.util.thread;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.time.StopWatch;

/**
 * 多线程, 一般用于测试
 *
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2015年12月21日 上午11:28:26
 */
public class MultiThread {

	private int threads;

	private AtomicInteger errCount = new AtomicInteger(0); // 错误计数器
	
	private CopyOnWriteArrayList<Long> list = new CopyOnWriteArrayList<>();

	public MultiThread(int threads) {
		this.threads = threads;
	}

	/**
	 * 并发执行方法, 需实现回调
	 * 
	 * @param threadWorker
	 *            - 回调
	 */
	public void executeThreads(final ThreadWorker threadWorker) {
		// 主线程唤醒和子线程唤醒初始化
		final CountDownLatch threadCount = new CountDownLatch(threads);
		final CountDownLatch mainCount = new CountDownLatch(1);
		// 构建N个线程
		for (int i = 0; i < threads; i++) {
			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						mainCount.await();
						StopWatch stopWatch = new StopWatch();
						stopWatch.start();
						threadWorker.execute();
						stopWatch.stop();
						list.add(stopWatch.getTime());
					} catch (Exception e) {
						e.printStackTrace();
						errCount.incrementAndGet();
					} finally {
						threadCount.countDown();
					}
				}
			});
			// --> 线程开启
			thread.start();
		}// --> end for
		try {
			// 主线程结束
			mainCount.countDown();
			// 等待所有子线程结束
			threadCount.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
