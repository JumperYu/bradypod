package com.bradypod.util.thread;

public class ThreadID {

	private static volatile int nextId = 0;

	private static class ThreadLocalID extends ThreadLocal<Integer> {
		@Override
		protected Integer initialValue() {
			return nextId++;
		}
	}

	private static ThreadLocalID threadID = new ThreadLocalID();

	public static int get() {
		return threadID.get();
	}

	public static void set(int index) {
		threadID.set(index);
	}
	
	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getId());
		System.out.println(ThreadID.get());
	}
}
