package thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.bradypod.util.thread.ThreadPool;
import com.bradypod.util.thread.ThreadWorker;
import com.yu.util.redis.RedisLock;

/**
 * 1, 使用ReentrantLock 2, 使用RedisLock
 *
 * @author zengxm
 * @date 2015年8月6日
 *
 */
public class TestLock {

	int count = 0;
	Lock lock = new ReentrantLock();

	public void increase() {
		// lock.lock();
		try (RedisLock redisLock = new RedisLock("sign")) {
			if (redisLock.lock())
				count++;
			else
				System.out.println("未能成功获取锁");
			// throw new Exception("未能成功获取锁");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getCount() {
		return count;
	}

	public static void main(String[] args) {
		int count = 10;
		ThreadPool pool = new ThreadPool(count);
		final TestLock testLock = new TestLock();
		pool.executeThread(new ThreadWorker() {
			@Override
			public void execute() {
				testLock.increase();
			}
		});
		System.out.println(testLock.getCount());
	}

	// 原版
	public static void main1(String[] args) {
		final TestLock testLock = new TestLock();
		for (int i = 0; i < 1; i++) {
			new Thread() {
				public void run() {
					for (int j = 0; j < 10; j++)
						testLock.increase();
				};
			}.start();
		}

		while (Thread.activeCount() > 1)
			// 保证前面的线程都执行完
			Thread.yield();
		System.out.println(testLock.count);
	}
}
