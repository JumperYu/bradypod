package com.yu.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyTask implements Runnable{
	
	private static Logger log = LoggerFactory
			.getLogger(MyTask.class);

	@Override
	public void run() {
		log.info("线程:" + Thread.currentThread().getName() + ", 正在工作.");
	}

}
