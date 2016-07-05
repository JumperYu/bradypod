package framework.config.schedule;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TestSchedule {

	public static void main(String[] args) throws InterruptedException {
		ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.schedule(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				setFlag(true);
				return null;
			}
		}, 5, TimeUnit.SECONDS);
		scheduledExecutorService.shutdown();
		while (!flag) {
			System.out.println("1");
			TimeUnit.MILLISECONDS.sleep(100);
		}
	}

	static boolean flag = false;

	public static void setFlag(boolean flag) {
		TestSchedule.flag = flag;
	}

	public static boolean getFlag() {
		return flag;
	}
}
