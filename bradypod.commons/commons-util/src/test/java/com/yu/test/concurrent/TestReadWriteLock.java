package com.yu.test.concurrent;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 
 * 读写锁适用于读多写少
 * 
 * @author	zengxm<http://github.com/JumperYu>
 *
 * @date	2016年3月12日
 */
public class TestReadWriteLock {

	private int numberA;

	private int numberB;

	private ReadWriteLock lock;

	public TestReadWriteLock() {
		lock = new ReentrantReadWriteLock();
	}

	public int getA() {
		lock.readLock().lock();
		int tmp = numberA;
		lock.readLock().unlock();
		return tmp;
	}

	public int getB() {
		lock.readLock().lock();
		int tmp = numberB;
		lock.readLock().unlock();
		return tmp;
	}

	public void write() {
		lock.writeLock().lock();
		this.numberA++;
		this.numberB++;
		lock.writeLock().unlock();
	}

	public static void main(String[] args) {
		final TestReadWriteLock readWriteLock = new TestReadWriteLock();

		for (int j = 0; j < 5; j++) {
			Thread threadA = new Thread() {
				@Override
				public void run() {
					for (int i = 0; i < 5; i++) {
						System.out.println(Thread.currentThread().getName()
								+ "-read: " + readWriteLock.getA());
						System.out.println(Thread.currentThread().getName()
								+ "-read: " + readWriteLock.getB());
					}
				}
			};
			threadA.start();
		}

		for (int j = 0; j < 5; j++) {
			Thread threadB = new Thread() {
				public void run() {
					for (int i = 0; i < 5; i++) {
						System.out.println(Thread.currentThread().getName()
								+ "-write");
						readWriteLock.write();
						try {
							Thread.sleep(300);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			};
			threadB.start();
		}

	}
}
