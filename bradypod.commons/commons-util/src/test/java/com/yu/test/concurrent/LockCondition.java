package com.yu.test.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 示例java.lock.condition
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年3月11日
 */
public class LockCondition {

	static class NumberWrapper {
		public int number;
	}

	public static void main(String[] args) {
		// 重入锁
		final Lock lock = new ReentrantLock();

		final Condition reachThree = lock.newCondition();
		final Condition reachSix = lock.newCondition();

		final NumberWrapper numberWrapper = new NumberWrapper();

		Thread threadA = new Thread() {
			@Override
			public void run() {
				lock.lock();
				try {
					System.out.println("thread A start write");
					while (numberWrapper.number <= 3) {
						System.out.println(numberWrapper.number);
						numberWrapper.number++;
					}
					// 输出条件到达标识
					reachThree.signal();
				} finally {
					lock.unlock();
				}
				lock.lock();
				try {
					while (numberWrapper.number <= 6) {
						reachSix.await();
					}
					while (numberWrapper.number <= 9) {
						System.out.println(numberWrapper.number);
						numberWrapper.number++;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					lock.lock();
				}
			}
		};

		Thread threadB = new Thread() {
			@Override
			public void run() {
				lock.lock();
				try {
					while (numberWrapper.number <= 3) {
						reachThree.await();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
				lock.lock();
				try {
					System.out.println("thread B start write");
					while (numberWrapper.number <= 6) {
						System.out.println(numberWrapper.number);
						numberWrapper.number++;
					}
					// 标识到达6了
					reachSix.signal();
				} finally {
					lock.unlock();
				}
			}
		};

		// 启动
		threadA.start();
		threadB.start();
	}
}
