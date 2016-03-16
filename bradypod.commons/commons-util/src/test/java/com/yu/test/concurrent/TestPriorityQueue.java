package com.yu.test.concurrent;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * 有序队列
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年3月14日
 */
public class TestPriorityQueue {

	public static void main(String[] args) throws InterruptedException {

		final PriorityBlockingQueue<EventOrder> queue = new PriorityBlockingQueue<EventOrder>();

		Thread[] threads = new Thread[3];
		for (int i = 0; i < threads.length; i++) {
			final int thread = i;
			threads[i] = new Thread() {
				public void run() {
					for (int j = 0; j < 1000; j++) {
						queue.add(new EventOrder(thread, j));
					}
				};
			};
			threads[i].start();
		}

		for (int i = 0; i < threads.length; i++) {
			threads[i].join();
		}

		System.out.printf("Mian: Queue Size: %d\n", queue.size());

		for (int i = 0; i < threads.length * 1000; i++) {
			EventOrder event = queue.poll();
			System.out.printf("Thread: %s Priority: %d\n", event.getThread(),
					event.getPriority());
		}
	}
}

class EventOrder implements Comparable<EventOrder> {

	private int thread;

	private int priority;

	public EventOrder(int thread, int priority) {
		this.thread = thread;
		this.priority = priority;
	}

	@Override
	public int compareTo(EventOrder other) {
		if (this.priority > other.priority) {
			return -1;
		} else if (this.priority < other.priority) {
			return 1;
		} else {
			return 0;
		}
	}

	public int getPriority() {
		return priority;
	}

	public int getThread() {
		return thread;
	}
}