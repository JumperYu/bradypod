package com.yu.nio;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TestBlockingQueue {

	public static void main(String[] args) throws InterruptedException {
		//testInternal();
		testExternal();
	}
	
	static void testExternal(){
		BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
		ExecutorService service = Executors.newCachedThreadPool();
		service.execute(new MyConsumer(queue));
		service.execute(new MyProducer(queue));
		service.shutdown();
	}
	
	static void testInternal() throws InterruptedException {
		BlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>();
		Consumer consumer = new Consumer(queue);
		Producer producer1 = new Producer(queue);
		Producer producer2 = new Producer(queue);
		Producer producer3 = new Producer(queue);

		// 借助Executors
		ExecutorService service = Executors.newCachedThreadPool();
		// 启动线程
		service.execute(producer1);
		service.execute(producer2);
		service.execute(producer3);
		service.execute(consumer);

		// 执行10s
		Thread.sleep(20 * 1000);
		producer1.stop();
		producer2.stop();
		producer3.stop();

		Thread.sleep(2000);
		consumer.stop();
		// 退出Executor
		service.shutdown();
	}

	static class Consumer implements Runnable {

		volatile boolean isRunning = true;
		BlockingQueue<Integer> queue;
		AtomicInteger atomic = new AtomicInteger(0);

		public Consumer(BlockingQueue<Integer> queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			try {
				while (isRunning) {
					int num = atomic.incrementAndGet();
					if (!queue.offer(num, 2, TimeUnit.SECONDS)) {
						System.out.println("生产者无法生产数据");
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			} finally {
				System.out.println("生产者退出");
			}
		}

		public void stop() {
			isRunning = false;
		}

	}

	static class Producer implements Runnable {

		private static final int DEFAULT_RANGE_FOR_SLEEP = 5;
		volatile boolean isRunning = true;
		BlockingQueue<Integer> queue;

		public Producer(BlockingQueue<Integer> queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			try {
				Random r = new Random();
				while (isRunning) {
					int num = queue.poll(2, TimeUnit.SECONDS);
					if (num > 0) {
						System.out.println(Thread.currentThread().getName() + ":" + num);
						TimeUnit.SECONDS.sleep(r.nextInt(DEFAULT_RANGE_FOR_SLEEP));
					} else {
						System.out.println("消费者无法获取数据");
						isRunning = false;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			} finally {
				System.out.println("消费者退出");
			}
		}

		public void stop() {
			isRunning = false;
		}
	}
}
