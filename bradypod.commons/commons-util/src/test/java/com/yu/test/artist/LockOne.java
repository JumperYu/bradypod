package com.yu.test.artist;

/**
 * 互斥锁算法 - 双线程解决方案  1, 有点简单； 2, 并发中将无法满足
 * 
 * @author	zengxm<http://github.com/JumperYu>
 *
 * @date	2016年3月7日
 */
public class LockOne implements Lock {

	boolean[] flag = new boolean[2];

	@Override
	public void lock() {
		// 双线程要么为0或1
		int i = (int) Thread.currentThread().getId();
		int j = 1 - i;
		flag[i] = true;
		while (flag[j]) {
		} // wait
	}

	@Override
	public void unlock() {
		int i = (int) Thread.currentThread().getId();
		flag[i] = false;
	}

}
