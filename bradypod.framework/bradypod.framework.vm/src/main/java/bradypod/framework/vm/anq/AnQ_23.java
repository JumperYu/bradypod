package bradypod.framework.vm.anq;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 线程锁的理解
 * 
 * @author xiangmin.zxm
 *
 */
public class AnQ_23 {

	public static void main(String[] args) throws InterruptedException {
		final Worker worker = new Worker();
		worker.start();
		Timer t = new Timer(true); // Daemon thread
		t.schedule(new TimerTask() {
			public void run() {
				worker.keepWorking();
			}
		}, 500);
		Thread.sleep(400);
		worker.quit();
	}

}

class Worker extends Thread {

	private volatile boolean quittingTime = false;

	private final Object lock = new Object();

	public void run() {
		while (!quittingTime)
			pretendToWork();
		System.out.println("Beer is good");
	}

	private void pretendToWork() {
		try {
			System.out.println("pretendToWork");
			Thread.sleep(300); // Sleeping on the job?
		} catch (InterruptedException ex) {
		}
	}

	// It's quitting time, wait for worker - Called by good boss
	void quit() throws InterruptedException {
		synchronized (lock) {
			quittingTime = true;
			System.out.println("join");
			join(); // 调用它的线程会调用this.wait并且释放锁
		}
	}

	// Rescind quitting time - Called by evil boss
	synchronized void keepWorking() {
		synchronized (lock) {
			System.out.println("keepworking");
			quittingTime = false;
		}
	}

}
