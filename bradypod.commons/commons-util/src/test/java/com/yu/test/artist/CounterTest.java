package com.yu.test.artist;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Assert;

public class CounterTest {

	public static void main(String[] args) throws Exception {

		// final SimpleCounter counter = new SimpleCounter();
		final ThreadSafeSimpleCounter counter = new ThreadSafeSimpleCounter();
		counter.setCount(0);

		final int threadNum = 12;
		final CyclicBarrier cyclicBarrier = new CyclicBarrier(threadNum);
		final List<Thread> threads = new ArrayList<>(cyclicBarrier.getParties());

		final AtomicReference<Exception> exception = new AtomicReference<>();

		for (int i = 0; i < threadNum; i++) {
			final Thread thread = new Thread() {
				@Override
				public void run() {
					try {
						cyclicBarrier.await();
						counter.getAndIncreament();
					} catch (InterruptedException | BrokenBarrierException e) {
						Thread.currentThread().interrupt();
						exception.compareAndSet(null, e);
					}
				}
			};
			thread.start();
			threads.add(thread);
		}

		threads.forEach(thread -> {
			try {
				thread.join();
			} catch (Exception e) {
			}
		});

		if (exception.get() != null) {
			throw exception.get();
		}

		Assert.assertEquals(cyclicBarrier.getParties(), counter.getCount());
	}

}
