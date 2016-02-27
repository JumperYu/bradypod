package com.yu.test.concurrent;

import java.util.Map;

/**
 * 共享锁， 又称读锁（S），允许并发读， 但是不允许修改
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年2月25日
 */
public class ShareLock {

	// 懒加载
	private static ShareLock instance = null;

	private ShareLock() {

	}

	private Map<String, byte[]> lockpool = new java.util.concurrent.ConcurrentHashMap<String, byte[]>(
			1024);

	/**
	 * 获取实例
	 */
	public static ShareLock getInstance() {
		// 懒加载
		if (instance == null) {
			// 减少粒度
			synchronized (ShareLock.class) {
				instance = new ShareLock();
			}
		}
		return instance;
	}

	/**
	 * 获取共享锁
	 */
	public byte[] getShareLock(String key) {
		byte[] lock = null;
		if (lockpool.containsKey(key)) {
			lock = lockpool.get(key);
		} else {
			lock = new byte[0];
			lockpool.put(key, lock);
		}
		return lock;
	}

	/**
	 * 释放锁
	 */
	public void removeShareLock(String key) {
		lockpool.remove(key);
	}

	public static void main(String[] args) {
		synchronized (ShareLock.getInstance().getShareLock("test")) {
			try {

			} finally {
				ShareLock.getInstance().removeShareLock("test");
			}
		}
	}
}
