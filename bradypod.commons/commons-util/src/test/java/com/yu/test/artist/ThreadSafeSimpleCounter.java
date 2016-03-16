package com.yu.test.artist;

import java.util.concurrent.TimeUnit;

/**
 * 演示计数器的线性安全
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年3月7日
 */
public class ThreadSafeSimpleCounter {

	private int count;

	public synchronized int getAndIncreament() {
		int temp = count;

		try {
			TimeUnit.MILLISECONDS.sleep(1);
		} catch (InterruptedException e) {
		}

		count = ++temp;

		return temp;
	}

	public synchronized void setCount(int count) {
		this.count = count;
	}

	public synchronized int getCount() {
		return count;
	}

}
