package com.yu.task;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Service
public class AsyncTaskService {

	private static final Logger log = LoggerFactory
			.getLogger(AsyncTaskService.class);

	//@Autowired
	private AsyncTask annotationTask;

	public void asyncServiceWithResult(int seconds) throws InterruptedException, ExecutionException {
		log.info("异步执行任务开始");
		log.info(annotationTask.doSomethingAsync(seconds).get());
		log.info("异步执行任务结束");
	}
	
	public void asyncServiceWithNoResult(int seconds) throws InterruptedException, ExecutionException {
		log.info("异步执行任务开始");
		annotationTask.doSomethingAsyncWithNoReturn(seconds);
		log.info("异步执行任务结束");
	}
}
