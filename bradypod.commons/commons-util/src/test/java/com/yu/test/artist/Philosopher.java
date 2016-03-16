package com.yu.test.artist;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 哲学家就餐问题
 * 
 * 1, waiter模式，最多只有2个家伙在吃
 * 
 * 2, TODO  Chandy/Misra 的解法
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年3月4日
 */
public class Philosopher extends Thread {

	public static void main(String[] args) {
		int philosophers = 10;
		Fork fork = new Fork(philosophers);
		for (int i = 0; i < philosophers; i++) {
			Philosopher philosopher_1 = new Philosopher("哲学家-" + i, i, fork);
			philosopher_1.start();
		}
	}

	private String name;

	private int index;

	private Fork fork;

	public static final int DEFAULT_THINKING_TIME = 3000;

	public static final int DEFAULT_EATING_TIME = 3000;

	public Philosopher(String name, int index, Fork fork) {
		this.name = name;
		this.index = index;
		this.fork = fork;
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			// 申请
			try {
				fork.takeForks(index);
			} catch (InterruptedException e) {
				System.out.println("---" + name + " 已经饿死了");
				Thread.interrupted();
			}
			// 吃东西
			eating();
			// 吃饱就思考
			thinking();
		}
	}

	public void thinking() {
		System.out.println(name + " 正在思考人生");
		try {
			sleep(ThreadLocalRandom.current().nextInt(3) * 1000);
		} catch (Exception e) {
			System.out.println("--- " + name + " 刚才脑路短路");
		}

	}

	public void eating() {
		System.out.println(name + " 正在吃东西");
		try {
			sleep(ThreadLocalRandom.current().nextInt(3) * 1000);
		} catch (Exception e) {
			System.out.println("--- " + name + " 刚才吃东西噎到了");
		}
		// 吃饱把叉子放回去
		fork.returnForks(index);
	}

}

class Fork {

	private final boolean[] forks;

	public Fork(int forksNum) {
		forks = new boolean[forksNum];
	}

	public synchronized void takeForks(int index) throws InterruptedException {
		if (!forks[(index + 1) % 5] && !forks[index]) {
			// 拿起筷子
			forks[(index + 1) % 5] = true;
			forks[index] = true;
		} else {
			wait();
		}
	}

	public synchronized void returnForks(int index) {
		// 交还筷子
		forks[(index + 1) % 5] = false;
		forks[index] = false;
		notifyAll();
	}
}
