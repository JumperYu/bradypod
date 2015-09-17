package com.yu.nio;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class MyConsumer implements Runnable {

	private BlockingQueue<String> queue;

	public MyConsumer(BlockingQueue<String> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		while (true) {
			queue.offer(UUID.randomUUID().toString().replaceAll("-", ""));
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
