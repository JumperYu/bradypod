package com.yu.test.concurrent;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 使用AtomicReference实现自旋锁
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年3月11日
 */
public class TestAtomicReference {

	private AtomicReference<Thread> owner = new AtomicReference<>();
	private int count = 0;

	/**
	 * 如果同一个线程多次进入的话会产生死锁
	 * 
	 * 所以使用计数器记录
	 */
	public void lock() {

		Thread current = Thread.currentThread();

		if (current == owner.get()) {
			count++;
			return;
		}

		while (!owner.compareAndSet(null, current)) {

		}

	}

	/**
	 * 如果同一个线程多次进入的话锁
	 */
	public void unlock() {

		Thread current = Thread.currentThread();

		if (current == owner.get()) {
			if (count != 0) {
				count--;
			} else {
				owner.compareAndSet(current, null);
			}

		}

	}

}
