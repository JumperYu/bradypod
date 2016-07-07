package framework.config.schedule;

import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.bradypod.framework.config.job.CheckingFileJob;

public class TestSchedule {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		final CheckingFileJob checkingFileJob = new CheckingFileJob(Paths.get("D://a.txt"));

		ExecutorService executorService = Executors.newSingleThreadExecutor();
		Future<Boolean> result = executorService.submit(checkingFileJob);

		System.out.println("isChanged: " + result.get());

		executorService.shutdown();
		
	}

}
