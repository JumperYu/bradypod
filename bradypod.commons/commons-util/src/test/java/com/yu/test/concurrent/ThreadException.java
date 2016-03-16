package com.yu.test.concurrent;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * 线程异常
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年3月10日
 */
public class ThreadException {

	public static void main(String[] args) {

		Thread thread = new Thread() {
			@Override
			public void run() {
				Integer num = Integer.parseInt("wrong");
				// do not show
				System.out.println(num);
			}
		};
		thread.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.out.printf("Thread: %s\n", t.getId());
				System.out.printf("Exception: %s: %s\n",
						t.getClass().getName(), e.getMessage());
			}
		});
		thread.start();
	}

}
