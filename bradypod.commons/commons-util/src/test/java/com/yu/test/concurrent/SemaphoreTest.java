package com.yu.test.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 信号量测试
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年3月4日
 */
public class SemaphoreTest {

	public static void main(String[] args) {

		// 定义100个线程
		int threads = 20;
		ExecutorService pool = Executors.newFixedThreadPool(threads);
		Semaphore semaphore = new Semaphore(2);
		for (int i = 0; i < threads; i++) {
			final int NO = i;
			pool.execute(new Runnable() {

				@Override
				public void run() {
					try {
						semaphore.acquire();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(System.currentTimeMillis() + " "+ NO + " is working");
					semaphore.release();
				}
			});
		}
		pool.shutdown();
	}

}
