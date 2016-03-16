package com.yu.test.concurrent;

import java.util.concurrent.TimeUnit;

public class ThreadGroupTest {

	public static void main(String[] args) {
		ThreadGroup threadGroup = new ThreadGroup("group-test");
		for (int i = 0; i < 5; i++) {
			Thread thread = new Thread(threadGroup, new Runnable() {

				@Override
				public void run() {
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
			thread.start();
		}
		System.out.println(threadGroup.activeCount());
		threadGroup.list();
		Thread[] threads = new Thread[threadGroup.activeCount()];
		threadGroup.enumerate(threads);
		for (int i = 0; i < threadGroup.activeCount(); i++) {
			System.out.printf("Thread %s: %s\n", threads[i].getName(),
					threads[i].getState());
		}
	}
}
