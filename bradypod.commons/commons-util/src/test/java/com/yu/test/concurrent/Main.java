package com.yu.test.concurrent;

public class Main {

	public static void main(String[] args) {
		Add t1 = new Add();
		Mul t2 = new Mul();

		t1.start(); // 3
		t2.start(); // 2

		try {
			t1.join(1000);
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		double n = ((double) t2.value / t1.value);

		System.out.println(n);
	}
}

class Add extends Thread {
	int value;

	public void run() {
		value = 1 + 2;
		System.out.println(Thread.currentThread().getName() + ":" + value);
	}
}

class Mul extends Thread {
	int value;

	public void run() {
		value = 1 * 2;
		System.out.println(Thread.currentThread().getName() + ":" + value);
	}
}