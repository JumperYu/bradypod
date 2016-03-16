package com.yu.test.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 线程同步写作辅助类， 比倒数阀门好用
 * 
 * @author	zengxm<http://github.com/JumperYu>
 *
 * @date	2016年3月1日
 */
public class CyclicBarrierTest {

	public static void main(String[] args) {
		int threads = 2;
		CyclicBarrier barrier = new CyclicBarrier(threads, new MainThread());

		// 子任务执行
		new Thread(new SubThread("广州", barrier)).start();
		new Thread(new SubThread("深圳", barrier)).start();
	}

}

class MainThread implements Runnable {
	@Override
	public void run() {
		System.out.println("正在统计主线程");
	}
}

class SubThread extends Thread {

	private String name;
	private CyclicBarrier barrier;

	public SubThread(String name, CyclicBarrier barrier) {
		this.name = name;
		this.barrier = barrier;
	}

	@Override
	public void run() {
		try {
			System.out.println("子线程正在统计: " + name);
		} finally {
			try {
				barrier.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		}

	}
}
