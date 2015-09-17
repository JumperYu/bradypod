package com.yu.nio;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class MyProducer implements Runnable {

	private BlockingQueue<String> queue;

	public MyProducer(BlockingQueue<String> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		while (true) {
			String value = queue.poll();
			if(value != null)
				System.out.println("pool value:" + value);
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
