package framework.config.schedule;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.bradypod.framework.config.job.CheckingFileJob;

public class TestSchedule {

	public static void main(String[] args) throws InterruptedException {
		final String path = "D://a.txt";
		ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.schedule(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				setTimeOut(true);
				return null;
			}
		}, 30, TimeUnit.SECONDS);
		scheduledExecutorService.shutdown();
		while (!isTimeOut && !isChanged) {
			isChanged = CheckingFileJob.beChanged(path);
		}
		System.out.println("isChanged: " + isChanged + ", isTimeOut:" + isTimeOut);
	}

	static boolean isTimeOut = false;
	static boolean isChanged = false;

	public static boolean isTimeOut() {
		return isTimeOut;
	}

	public static void setTimeOut(boolean isTimeOut) {
		TestSchedule.isTimeOut = isTimeOut;
	}

}
