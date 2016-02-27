package com.yu.test.concurrent;

/**
 * 测试锁
 * 
 * @author	zengxm<http://github.com/JumperYu>
 *
 * @date	2016年2月25日
 */
public class SyncTest implements Runnable {

	public static void main(String[] args) {
		SyncTest syncTest = new SyncTest();
		// 同一个对象则可以同时进入sync模块
		new Thread(syncTest).start();
		new Thread(syncTest).start();
	}

	@Override
	public void run() {
		// 对象锁
		synchronized (this) {
			System.out.println(Thread.currentThread().getName());
			try {
				this.wait(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}

}
