package com.yu.test.concurrent;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 测试线性安全的随机类
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年3月14日
 */
public class TestLoclRandom {

	public static void main(String[] args) {

		Thread[] threads = new Thread[10];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread() {
				@Override
				public void run() {
					String name = Thread.currentThread().getName();
					for (int i = 0; i < 10; i++) {
						System.out.printf("%s: %d\n", name, ThreadLocalRandom
								.current().nextInt(10));
					}
				}
			};
			threads[i].start();
		}
	}
}
