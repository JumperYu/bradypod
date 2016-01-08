package com.yu.task;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

//@Configuration
//@EnableScheduling
public class ScheduleTask {

	private static final Logger log = LoggerFactory
			.getLogger(ScheduleTask.class);

	// delay N 毫秒后, 且方法结束后才能继续
	@Scheduled(fixedDelay = 2000)
	public void doSomethingDelay() throws InterruptedException {
		log.info("延迟2秒后开始工作");
//		TimeUnit.SECONDS.sleep(3);
	}

	// fixed N 毫秒, 就会立即执行
//	@Scheduled(fixedRate = 2000)
	public void doSomethingFixedRate() throws InterruptedException {
		log.info("固定2秒的频率工作");
		TimeUnit.SECONDS.sleep(3);
	}

}
