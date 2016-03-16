package com.yu.test.artist;

/**
 * 互斥锁算法 - 双线程解决方案  1, 有点简单； 2, 满足并发，但是不满足前后执行
 * 
 * @author	zengxm<http://github.com/JumperYu>
 *
 * @date	2016年3月7日
 */
public class LockTwo implements Lock {

	private volatile int victim;

	@Override
	public void lock() {
		int i = (int) Thread.currentThread().getId();
		victim = i;
		while (victim == i) {

		}
	}

	@Override
	public void unlock() {
		// TODO Auto-generated method stub

	}

}
