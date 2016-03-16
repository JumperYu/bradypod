package com.yu.test.concurrent;

import java.util.concurrent.Phaser;

/**
 * 调节机, 同步机
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年3月14日
 */
public class TestPhaser {

	public static void main(String[] args) {
		Phaser phaser = new Phaser(3) {
			@Override
			protected boolean onAdvance(int phase, int registeredParties) {
				System.out.println("on advance, phase: " + phase
						+ ", registeredParties: " + registeredParties);
				return registeredParties == 0;
			}
		};
		System.out.println("程序开始");
		char a = 'a';
		for (int i = 0; i < 3; i++) { // 创建并启动3个线程
			new Thread(new MyJob(a, phaser)).start();
		}
		while (!phaser.isTerminated()) {
			Thread.yield();
		}
		System.out.println("程序结束");
	}

}

class MyJob implements Runnable {

	private char c;
	private Phaser phaser;

	public MyJob(char c, Phaser phaser) {
		this.c = c;
		this.phaser = phaser;
	}

	@Override
	public void run() {
		while (!phaser.isTerminated()) {

			for (int i = 0; i < 3; i++) {
				System.out.print(c + " ");
			}

			c = (char) (c + 3);
			if (c > 'z') {
				// 如果超出了字母z，则在phaser中动态减少一个线程，并退出循环结束本线程
				phaser.arriveAndDeregister();
				break;
			} else {
				// 反之，等待其他线程到达阶段终点，再一起进入下一个阶段
				// 会回调onAdvance事件
				phaser.arriveAndAwaitAdvance();
			}
		}// end while
	}
}
