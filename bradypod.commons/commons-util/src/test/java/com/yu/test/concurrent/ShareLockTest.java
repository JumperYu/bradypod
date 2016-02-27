package com.yu.test.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * 共享锁的一些测试, wait 和yield 一样不会释放锁
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年2月26日
 */
public class ShareLockTest {

	private int count = 0; // 并发线程读写

	private int threadNum = 100;

	public int getAndIncreament() {
		return ++count;
	}

	public static void main(String[] args) throws InterruptedException {
		final ShareLockTest shareLockTest = new ShareLockTest();
		// 使用concurrent的包来实现
		CountDownLatch mainLatch = new CountDownLatch(1);
		CountDownLatch subLatch = new CountDownLatch(shareLockTest.threadNum);
		CountDownLatch allLatch = new CountDownLatch(shareLockTest.threadNum);
		for (int i = 0; i < shareLockTest.threadNum; i++) {
			Thread thread = new Thread(new Runnable() {
				public void run() {
					// 子线程递减
					subLatch.countDown();
					// 子线程输出
					System.out.println(Thread.currentThread().getName()
							+ " is waiting...");
					// 等待主线程通知
					try {
						mainLatch.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName()
							+ " is wake up, " + shareLockTest.getAndIncreament());
					// 所有子线程执行结束递减
					allLatch.countDown();
				}
			});
			// 启动子线程
			thread.start();
		}
		// 主线程等待子线程初始化完成
		subLatch.await();
		// 唤醒所有子线程
		mainLatch.countDown();
		// 让出CPU
		// Thread.yield();
		// 打印
		System.out.println("Main thread running for notify all threads");
		// 所有子线程工作结束
		allLatch.await();
		// 输出结果
		System.out.println("result: " + shareLockTest.count);
	}
}
