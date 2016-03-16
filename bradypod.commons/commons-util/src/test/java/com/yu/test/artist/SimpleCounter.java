package com.yu.test.artist;

import java.util.concurrent.TimeUnit;

/**
 * 演示计数器的线性不安全
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年3月7日
 */
public class SimpleCounter {

	private int count;

	public int getAndIncreament() {
		int temp = count;

		try {
			TimeUnit.MILLISECONDS.sleep(1);
		} catch (InterruptedException e) {
		}

		count = ++temp;

		return temp;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getCount() {
		return count;
	}

}
