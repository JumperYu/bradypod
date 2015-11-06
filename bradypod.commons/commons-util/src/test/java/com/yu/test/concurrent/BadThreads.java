package com.yu.test.concurrent;

public class BadThreads {

	static String message;

	private static class CorrectorThread extends Thread {

		public void run() {
			try {
				sleep(1000);
			} catch (InterruptedException e) {
			}
			// Key statement 1:
			message = "Mares do eat oats.";
		}
	}

	public static void main(String args[]) throws InterruptedException {

		CorrectorThread thread = new CorrectorThread();
		thread.start();
		thread.join(2000);
		message = "Mares do not eat oats.";
		// Key statement 2:
		System.out.println(message);
	}
}