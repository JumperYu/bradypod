package com.yu.test.artist;

import java.util.concurrent.RecursiveTask;

/**
 * 质数测试
 * 
 * 1, 注意算法;
 * 
 * 2, 使用多线程;
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年3月3日
 */
public class PrimeTest {

	// 一亿数里面使用筛选法 耗时 1.4S左右  5761455
	
	public static void main(String[] args) {

		long start = System.currentTimeMillis();

		// ForkJoinPool pool = new ForkJoinPool();
		// int count = pool.invoke(new PrimeTask(1, 10000000, 3000));

		int count = coutnPrimeNumber(1000000000);

		long end = System.currentTimeMillis();
		System.out.println("耗时: " + (end - start) + " 结果为： " + count);

	}

	/**
	 * 终极计算方法
	 * 
	 * @param number
	 * @return
	 */
	public static int coutnPrimeNumber(int number) {
		// n + 1
		boolean[] prime = new boolean[number + 1];

		// --> 1. 设置奇数为true
		for (int i = 1; i <= number; i++) {
			if (i % 2 != 0)
				prime[i] = true;
		}
		
		// 2 单独设置为true
		prime[2] = true;
		
		int count = 0; // 计算个数

		// 设置奇数的倍数为false
		for (int i = 3; i <= Math.sqrt(number); i += 2) {
			// 如果是奇数
			if (prime[i]) {
				for (int j = i + i; j <= number; j += i) {
					prime[j] = false;
				}
			}
		}

		for (int i = 2; i < prime.length; i++) {
			if (prime[i]) {
				count++;
			}
		}

		return count;
	}

}

/**
 * 比较拙略的写法
 * 
 * @author	zengxm<http://github.com/JumperYu>
 *
 * @date	2016年3月3日
 */
class PrimeTask extends RecursiveTask<Integer> {

	private int begin; // 1

	private int end; // 100

	private int count; // 计算质数的个数

	private int threshold; // 阀值

	private final int DEFAULT_SIZE = 1000; // 默认阀值

	public PrimeTask(int begin, int end) {
		this.begin = begin;
		this.end = end;
		this.threshold = DEFAULT_SIZE;
	}

	public PrimeTask(int begin, int end, int threshold) {
		this.begin = begin;
		this.end = end;
		this.threshold = threshold;
	}

	@Override
	protected Integer compute() {

		// 如果大于阀值拆分子任务
		if ((end - begin + 1) % threshold > 0) {

			PrimeTask left = new PrimeTask(begin, begin + threshold, threshold);
			PrimeTask right = new PrimeTask(begin + threshold, end, threshold);

			left.fork();
			right.fork();

			count += (left.join() + right.join());

		} else {
			for (int i = begin; i < end; i++) {
				if (isPrimeByFactor(i)) {
					// System.out.println(i);
					count++;
				}
			}// --> end for

		}// --> end if-else

		return count;
	}

	/**
	 * 循环判断是否是质数
	 * 
	 * @param number
	 * @return
	 */
	public static boolean isPrimeByLoop(int number) {

		for (int i = 2; i < number - 1; i++) {
			if (number % i == 0) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 根据合数定理反推是否为质数
	 * 
	 * @param number
	 * @return
	 */
	public static boolean isPrimeByFactor(int number) {

		int sqrt = (int) Math.sqrt(number);

		for (int i = 2; i <= sqrt; i++) {
			if (number % i == 0) {
				return true;
			}
		}

		return false;
	}

	private static final long serialVersionUID = 1L;
}