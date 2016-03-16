package com.yu.test.concurrent;

import java.util.Date;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * 线性安全的阻塞队列
 * 
 * @author	zengxm<http://github.com/JumperYu>
 *
 * @date	2016年3月14日
 */
public class TestBlockingQueue {

	public static void main(String[] args) throws InterruptedException {

		LinkedBlockingDeque<String> list = new LinkedBlockingDeque<>();

		new Thread(new Client(list)).start();

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				String request = list.take();
				System.out.printf("Main: Request: %s at %s. Size: %d\n",
						request, new Date(), list.size());
			}
			TimeUnit.MILLISECONDS.sleep(300);
		}
	}

}

class Client implements Runnable {

	LinkedBlockingDeque<String> list;

	public Client(LinkedBlockingDeque<String> list) {
		this.list = list;
	}

	@Override
	public void run() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 10; j++) {
				StringBuilder req = new StringBuilder();
				req.append(i).append(":").append(j);
				try {
					list.put(req.toString());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.printf("Client: %s at %s.\n", req.toString(),
						new Date());
			}
			try {
				TimeUnit.MILLISECONDS.sleep(2000);
			} catch (InterruptedException e) {

			}
		} // end for
		System.out.println("Client: End");
	}
}
