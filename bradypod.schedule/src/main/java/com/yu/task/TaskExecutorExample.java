package com.yu.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;

/**
 * 
 * Spring基于TimerTask的任务调度
 *
 * @author zengxm
 * @date 2015年6月24日
 *
 */
//@Component
public class TaskExecutorExample {

	private static Logger log = LoggerFactory
			.getLogger(TaskExecutorExample.class);

	private static final int TEST_COUNT = 25;

	private class MessagePrinterTask implements Runnable {
		private String message;

		public MessagePrinterTask(String message) {
			this.message = message;
		}

		public void run() {
			log.info(message);
		}
	}

	private TaskExecutor taskExecutor;

	public TaskExecutorExample(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public void printMessages() {
		for (int i = 0; i < TEST_COUNT; i++) {
			taskExecutor.execute(new MessagePrinterTask("Message-" + i));
		}
	}
}
