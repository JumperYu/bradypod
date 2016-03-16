package com.yu.test.concurrent;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

/**
 * 交换器
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年3月14日
 */
public class TestExchanger {

	public static void main(String[] args) throws InterruptedException {

		Exchanger<Integer> exchanger = new Exchanger<Integer>();

		Thread producer = new Thread(new MyProducer(exchanger));
		Thread consumer = new Thread(new MyConsumer(exchanger));

		producer.start();
		consumer.start();

	}

}

class MyProducer implements Runnable {

	private Exchanger<Integer> exchanger;
	private Integer number;

	public MyProducer(Exchanger<Integer> exchanger) {
		this.exchanger = exchanger;
		this.number = new Integer(0);
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			try {
				++number;
				System.out.println("producer exchange data: " + number);
				exchanger.exchange(number);
				TimeUnit.MILLISECONDS.sleep((long) (Math.random() * 100));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class MyConsumer implements Runnable {

	private Exchanger<Integer> exchanger;
	private Integer number;

	public MyConsumer(Exchanger<Integer> exchanger) {
		this.exchanger = exchanger;
		this.number = new Integer(0);
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			try {
				number = exchanger.exchange(number);
				System.out.println("consumer get data: " + number);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
