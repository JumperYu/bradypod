package com.yu.test.concurrent;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * non-blocking thread-safe lists
 * 
 * 双端链表
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年3月14日
 */
public class TestDeque {

	public static void main(String[] args) {
		ConcurrentLinkedDeque<String> list = new ConcurrentLinkedDeque<String>();

		Thread[] threads = new Thread[100];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(new AddTask(list));
			threads[i].start();
		}
		for (int i = 0; i < threads.length; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Add tasks have done, deque size: " + list.size());
		
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(new PollTask(list));
			threads[i].start();
		}
		
		for (int i = 0; i < threads.length; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("poll tasks have done, deque size: " + list.size());
	}

}

class AddTask implements Runnable {

	private ConcurrentLinkedDeque<String> list;

	public AddTask(ConcurrentLinkedDeque<String> list) {
		this.list = list;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			list.add("i=" + i);
		}
	}
}

class PollTask implements Runnable {

	private ConcurrentLinkedDeque<String> list;

	public PollTask(ConcurrentLinkedDeque<String> list) {
		this.list = list;
	}

	@Override
	public void run() {
		for (int i = 0; i < 5; i++) {
			list.pollFirst();
			list.pollLast();
		}
	}
}