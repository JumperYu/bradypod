package com.yu.test.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * 通过并行计算得到结果，多线程递归计算
 *
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015年11月7日 上午9:36:20
 */
public class Calculator extends RecursiveTask<Integer> {

	private static final int THRESHOLD = 2; // 阀值
	private int start;
	private int end;

	public Calculator(int start, int end) {
		this.start = start;
		this.end = end;
	}

	@Override
	protected Integer compute() {
		int sum = 0;
		// 如果小于阀值则直接计算
		if ((end - start) <= THRESHOLD) {
			for (int i = start; i < end; i++) {
				sum += i;
			}
			// throw new RuntimeException("I did not complete");
		} else {
			int middle = (start + end) / 2;
			Calculator left = new Calculator(start, middle);
			Calculator right = new Calculator(middle + 1, end);
			// fork分叉成几个子任务继续计算
			left.fork();
			right.fork();
			// join加入结果
			sum = left.join() + right.join();
		}// --> end if-else
		return sum;
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		try {
			ForkJoinPool forkJoinPool = new ForkJoinPool();
			Future<Integer> result = forkJoinPool.submit(new Calculator(1, 4));
			if (result.isDone())
				System.out.println(result.get());
		} catch (Exception e) {
			System.out.println("pool error");
		}
	}

	private static final long serialVersionUID = 1L;
}
