package com.yu.test.artist;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class MyLock extends ReentrantLock {

	private static final long serialVersionUID = 1L;

	// 2. 实现 getOwnerName() 方法。此方法使用Lock类的保护方法 getOwner()， 返回控制锁的线程（如果存在）的名字。
	public String getOwnerName() {
		if (this.getOwner() == null) {
			return "None";
		}
		return this.getOwner().getName();
	}

	// 3. 实现 getThreads() 方法。此方法使用Lock类的保护方法 getQueuedThreads()，返回在锁里的线程的 queued
	// list。
	public Collection<Thread> getThreads() {
		return this.getQueuedThreads();
	}

	// 4. 创建一个类，名为 Task，实现 Runnable 接口.
	public class Task implements Runnable {

		// 5. 声明一个私有 Lock 属性，名为 lock。
		private java.util.concurrent.locks.Lock lock;

		// 6. 实现类的构造函数，初始化它的属性值。
		public Task(java.util.concurrent.locks.Lock lock) {
			this.lock = lock;
		}

		// 7. 实现 run() 方法。创建迭代5次的for循环。
		@Override
		public void run() {
			for (int i = 0; i < 5; i++) {

				// 8. 使用lock()方法获取锁，并打印一条信息。
				lock.lock();
				System.out.printf("%s: Get the Lock.\n", Thread.currentThread()
						.getName());

				// 9. 让线程休眠 500 毫秒。使用 unlock() 释放锁并打印一条信息。
				try {
					TimeUnit.MILLISECONDS.sleep(500);
					System.out.printf("%s: Free the Lock.\n", Thread
							.currentThread().getName());
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
			}
		}
	}

	// 10. 创建例子的主类通过创建一个类，名为 Main 并添加 main()方法。
	public static void main(String[] args) throws Exception {

		// 11. 创建 MyLock 对象，名为 lock。
		MyLock lock = new MyLock();

		// 12. 创建有5个Thread对象的 array。
		Thread threads[] = new Thread[5];

		// 13. 创建并开始5个线程来执行5个Task对象。
		for (int i = 0; i < 5; i++) {
			Task task = lock.new Task(lock);
			threads[i] = new Thread(task);
			threads[i].start();
		}

		// 14. 创建迭代15次的for循环。
		for (int i = 0; i < 15; i++) {

			// 15. 把锁的拥有者的名字写入操控台。
			System.out.printf("Main: Logging the Lock\n");
			System.out.printf("************************\n");
			System.out.printf("Lock: Owner : %s\n", lock.getOwnerName());

			// 16. 显示锁queued的线程的号码和名字。
			System.out.printf("Lock: Queued Threads: %s\n",
					lock.hasQueuedThreads()); // 译者注：加上 System
			if (lock.hasQueuedThreads()) {
				System.out.printf("Lock: Queue Length: %d\n",
						lock.getQueueLength());
				System.out.printf("Lock: Queued Threads: ");
				Collection<Thread> lockedThreads = lock.getThreads();
				for (Thread lockedThread : lockedThreads) {
					System.out.printf("%s ", lockedThread.getName());
				}
				System.out.printf("\n");
			}

			// 17. 显示关于Lock对象的公平性和状态的信息。
			System.out.printf("Lock: Fairness: %s\n", lock.isFair());
			System.out.printf("Lock: Locked: %s\n", lock.isLocked());
			System.out.printf("************************\n");

			// 18. 让线程休眠1秒，并合上类的循环。
			TimeUnit.SECONDS.sleep(1);
		}
	}
}
