package com.bradypod.util.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 多线程定时任务
 *
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015年11月7日 下午5:38:34
 */
public class ScheduledThreadPool {

	private int threads; // 线程数
	private ScheduledExecutorService pool; // 线程池

	public ScheduledThreadPool(int threads) {
		this.threads = threads;
	}

	// ScheduleAtFixedRate 是基于固定时间间隔进行任务调度
	public void executeAtFixedRate(final ThreadWorker threadWorker, int delay, int period) {
		pool = Executors.newScheduledThreadPool(threads);
		pool.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				threadWorker.execute();
			}
		}, delay, period, TimeUnit.SECONDS);
	}

	// ScheduleWithFixedDelay 取决于每次任务执行的时间长短，是基于不固定时间间隔进行任务调度
	public void executeWithFixedRate(final ThreadWorker threadWorker, int delay, int period) {
		pool = Executors.newScheduledThreadPool(threads);
		pool.scheduleWithFixedDelay(new Runnable() {

			@Override
			public void run() {
				threadWorker.execute();
			}
		}, delay, period, TimeUnit.SECONDS);
	}

	public static void main(String[] args) {
		ScheduledThreadPool pool = new ScheduledThreadPool(2);
		pool.executeAtFixedRate(new ThreadWorker() {

			@Override
			public void execute() {
				System.out.println(String.format("%s:%d", Thread.currentThread().getName(),
						System.currentTimeMillis()));
			}
		}, 0, 1);
	}

}
