package com.yu.test.concurrent;

import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class TestDelayQueue {

	public static void main(String[] args) throws InterruptedException {
		final DelayQueue<EventDeplay> queue = new DelayQueue<EventDeplay>();

		Thread[] threads = new Thread[3];
		for (int i = 0; i < threads.length; i++) {
			final int thread = i;
			threads[i] = new Thread() {
				public void run() {
					Date now = new Date();
					Date delay = new Date();
					delay.setTime(now.getTime() + (thread * 1000));
					System.out.printf("Thread %s: %s\n", thread, delay);
					for (int i = 0; i < 100; i++) {
						EventDeplay event = new EventDeplay(delay);
						queue.add(event);
					}
				};
			};
			threads[i].start();
		}

		for (int i = 0; i < threads.length; i++) {
			threads[i].join();
		}

		System.out.printf("Mian: Queue Size: %d\n", queue.size());

		do {
			int counter = 0;
			EventDeplay event;
			do {
				event = queue.poll();
				if (event != null)
					counter++;
			} while (event != null);
			System.out.printf("At %s you have read %d evenets \n", new Date(),
					counter);
			TimeUnit.MILLISECONDS.sleep(500);
		} while (queue.size() != 0);
	}
}

class EventDeplay implements Delayed {

	private Date date;

	public EventDeplay(Date date) {
		this.date = date;
	}

	@Override
	public int compareTo(Delayed o) {
		long result = this.getDelay(TimeUnit.NANOSECONDS)
				- o.getDelay(TimeUnit.NANOSECONDS);
		if (result < 0) {
			return -1;
		} else if (result > 0) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public long getDelay(TimeUnit unit) {
		Date now = new Date();
		long diff = date.getTime() - now.getTime();
		return unit.convert(diff, TimeUnit.MILLISECONDS);
	}
}
