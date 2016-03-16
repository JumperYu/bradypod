package com.yu.test.concurrent;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

/**
 * 重入锁， 能够让当前线程多次的进行对锁的获取操作， 这样的最大次数限制是Integer.MAX_VALUE，约21亿次左右。 有分公平和不不公平，
 * 公平是指先请求就先获取
 * 
 * 证明RreentranLock是互斥锁, 只允许一个线程执行
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年3月10日
 */
public class ReentranLockTest {

	private static Lock fairLock = new ReentrantLock2(true);
	private static Lock unfairLock = new ReentrantLock2();

	@Test
	public void fair() {
		System.out.println("fair version");
		for (int i = 0; i < 5; i++) {
			Thread thread = new Thread(new Job(fairLock));
			thread.setName("" + i);
			thread.start();
		}

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void unfair() {
		System.out.println("unfair version");
		for (int i = 0; i < 5; i++) {
			Thread thread = new Thread(new Job(unfairLock));
			thread.setName("" + i);
			thread.start();
		}

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSynJob() {
		System.out.println("sync version");
		for (int i = 0; i < 5; i++) {
			Thread thread = new Thread(new SynJob());
			thread.setName("" + i);
			thread.start();
		}

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static class Job implements Runnable {
		private Lock lock;

		public Job(Lock lock) {
			this.lock = lock;
		}

		@Override
		public void run() {
			for (int i = 0; i < 5; i++) {
				lock.lock();
				try {
					System.out.println("Lock by:"

					+ Thread.currentThread().getName() + " and "

					+ ((ReentrantLock2) lock).getQueuedThreads()

					+ " waits.");
					TimeUnit.MILLISECONDS.sleep((int) Math.random() * 100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
			}
		}
	}

	private static class ReentrantLock2 extends ReentrantLock {

		// Constructor Override

		private static final long serialVersionUID = 1773716895097002072L;

		public ReentrantLock2() {
			super();
		}

		public ReentrantLock2(boolean fair) {
			super(fair);
		}

		public Collection<Thread> getQueuedThreads() {

			return super.getQueuedThreads();

		}

	}

	private static class SynJob implements Runnable {

		@Override
		public synchronized void run() {
			for (int i = 0; i < 5; i++) {
				try {
					System.out.println("Lock by:"
							+ Thread.currentThread().getName());
					// sleep a while
					TimeUnit.MILLISECONDS.sleep((int) Math.random() * 100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
