package bradypod.framework.lock;

import java.lang.management.ManagementFactory;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.time.StopWatch;

/**
 * 验证锁的开销在不同jvm版本的效果是不同的
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年5月8日
 */
public class JvmLockTestCase {

	public static void main(String[] args) {
		System.out.println(ManagementFactory.getRuntimeMXBean().getName());
		// 1, 做4次单线程运算取平均值
//		countTimeBySingleThread();
//		countTimeBySingleThread();
//		countTimeBySingleThread();
//		countTimeBySingleThread();
		
		// 2, 做4次多线程运算取平均值
		countTimeByMultiThread();
		countTimeByMultiThread();
		countTimeByMultiThread();
		countTimeByMultiThread();
	}

	static int count = 256;
	static int size = 10000;

	static StopWatch watch = new StopWatch();

	static ReentrantLock lock = new ReentrantLock();

	/**
	 * 单线程计算时间
	 */
	static void countTimeBySingleThread() {
		watch.reset();
		watch.start();
		// 计算
		int total = 0;
		for (int i = 0; i < count; i++) {
			total += testSync();
			// total += testLock();
		}
		watch.stop();
		System.out.format("single thread sync test time: %s\n", watch);
		System.out.format("single thread sync total: %d\n", total);
	}

	/**
	 * 单线程计算时间
	 */
	static void countTimeByMultiThread() {
		watch.reset();
		watch.start();
		// 计算
		Thread[] threads = new Thread[count];
		for (int i = 0; i < count; i++) {
			threads[i] = new Thread("thread-" + i){
				@Override
				public void run() {
//					testSync();
					testLock();
				}
			};
			threads[i].start();
		}
		for (int i = 0; i < count; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				Thread.interrupted();
			}
		}
		watch.stop();
		System.out.format("multi thread sync test time: %s\n", watch);
	}

	static synchronized int testSync() {

		int tmp = 0;

		for (int i = 0; i < size; i++) {
			tmp++;
		}

		return tmp;
	}

	static int testLock() {
		int tmp = 0;

		lock.lock();
		try {
			for (int i = 0; i < size; i++) {
				tmp++;
			}
		} finally {
			lock.unlock();
		}

		return tmp;
	}

}
