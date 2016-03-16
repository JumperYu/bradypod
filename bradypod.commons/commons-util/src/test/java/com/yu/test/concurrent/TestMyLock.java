package com.yu.test.concurrent;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestMyLock {

	public static void main(String[] args) throws InterruptedException {
		MyLock lock = new MyLock();

		Thread[] threads = new Thread[5];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(new MyTask(lock));
			threads[i].start();
		}

		for (int i = 0; i < 15; i++) {
			System.out.printf("Main: Logging the Lock\n");
			System.out.printf("**********************\n");
			System.out.printf("Lock: Owner: %s\n", lock.getOwnerName());
			if (lock.hasQueuedThreads()) {
				System.out.printf("Lock: Queued Length %d\n",
						lock.getQueueLength());
				System.out.printf("Lock: Queued Threads: ");
				for (Thread thread : lock.getThreads()) {
					System.out.printf("%s ", thread.getName());
				}
				System.out.println();
			}
			System.out.printf("Lock: Fairness: %s\n", lock.isFair());
			System.out.printf("Lock: Locked: %s\n", lock.isLocked());
			System.out.printf("**********************\n");
			TimeUnit.MILLISECONDS.sleep(1);
		}

	}

}

class MyLock extends ReentrantLock {

	private static final long serialVersionUID = 1L;

	public String getOwnerName() {
		if (this.getOwner() == null) {
			return "None";
		}
		return this.getOwner().getName();
	}

	public Collection<Thread> getThreads() {
		return this.getQueuedThreads();
	}

}

class MyTask implements Runnable {

	private Lock lock;

	public MyTask(Lock lock) {
		this.lock = lock;
	}

	@Override
	public void run() {
		for (int i = 0; i < 5; i++) {
			lock.lock();
			try {
				System.out.printf("%s get the lock\n", Thread.currentThread()
						.getName());
				TimeUnit.MILLISECONDS.sleep(500);
				System.out.printf("%s free the lock\n", Thread.currentThread()
						.getName());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
	}

}
