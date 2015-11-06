package com.yu.test.concurrent;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.bradypod.util.thread.ThreadPool;
import com.bradypod.util.thread.ThreadWorker;

public class TestTreadPool {

	public static void main(String[] args) {

		ThreadPool pool = new ThreadPool(20);
		pool.executeThread(new ThreadWorker() {

			@Override
			public void execute() {
				try {
					TimeUnit.SECONDS.sleep(6l);
					System.out.println(new Date());
				} catch (InterruptedException e) {
					// ignore
				}
			}
		});

	}

}
