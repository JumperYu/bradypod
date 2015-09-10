package thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestVolatile {

	static class MyCounter implements Runnable {

		private CountDownLatch latch;
		private volatile static int count = 0;

		public MyCounter(CountDownLatch latch) {
			this.latch = latch;
		}

		@Override
		public void run() {
			count++;
			try {
				TimeUnit.MICROSECONDS.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			latch.countDown();
		}
	}

	public static void main(String[] args) throws InterruptedException {

		int count = 10000;
		CountDownLatch latch = new CountDownLatch(count);
		ExecutorService executor = Executors.newFixedThreadPool(count);
		for (int i = 0; i < count; i++) {
			executor.execute(new MyCounter(latch));
		}
		latch.await();
		System.out.println("finished:" + MyCounter.count);
		executor.shutdown();

	}
}
