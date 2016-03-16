package com.yu.test.artist;

/**
 * 互斥锁 - 双线程算法 - LockOne + LockTwo
 * 
 * PeterSon算法
 *  
 * @author	zengxm<http://github.com/JumperYu>
 *
 * @date	2016年3月7日
 */
public class Peterson implements Lock{

	private volatile boolean[] flag = new boolean[2];
	private volatile int victim;
	
	@Override
	public void lock() {
		int i = (int) Thread.currentThread().getId();
		int j = 1 - i;
		
		flag[i] = true;
		victim = i;
		
		while(flag[j] && victim == i) {
			// wait
		}
	}

	@Override
	public void unlock() {
		int i = (int) Thread.currentThread().getId();
		flag[i] = false;
	}

}
