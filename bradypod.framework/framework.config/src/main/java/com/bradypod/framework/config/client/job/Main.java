package com.bradypod.framework.config.client.job;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) {

		final CheckWorker clientWorker = new CheckWorker("D://b.txt");

		ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleWithFixedDelay(clientWorker, 0, 1, TimeUnit.SECONDS);
	}

}
