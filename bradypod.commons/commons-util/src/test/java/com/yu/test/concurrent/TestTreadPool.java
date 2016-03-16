package com.yu.test.concurrent;

import com.bradypod.util.thread.ThreadPool;
import com.bradypod.util.thread.ThreadWorker;

/**
 * 测试多线程框架
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年3月7日
 */
public class TestTreadPool {

	private int value;

	public int getAndIncreament() {
		int temp = value;
		value = temp + 1;
		return temp;
	}

	public static void main(String[] args) {

		TestTreadPool test = new TestTreadPool();

		ThreadPool pool = new ThreadPool(10);
		pool.executeThread(new ThreadWorker() {

			@Override
			public void execute() {
				test.getAndIncreament();
			}
		});
		
		System.out.println(test.value);

	}

}
