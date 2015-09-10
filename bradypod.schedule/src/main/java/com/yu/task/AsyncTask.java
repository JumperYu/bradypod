package com.yu.task;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Component
public class AsyncTask {

	private static final Logger log = LoggerFactory
			.getLogger(AsyncTask.class);

	// 异步调用，可以传参数进来，调用Callable获取返回值， 同时失去了外部异步执行的效果
	//@Async
	public Future<String> doSomethingAsync(final int seconds) {
		log.info("在 " + seconds + " 秒之后我才会开始工作!");
		Future<String> future = Executors.newSingleThreadExecutor().submit(
				new Callable<String>() {
					@Override
					public String call() throws Exception {
						TimeUnit.SECONDS.sleep(seconds);
						return "done";
					}
				});

		log.info("我可能在其他未结束线程结束，我就开始工作了");
		return future;
	}
	
	// 这里不能再开启线程， 会影响异步执行
	//@Async
	public void doSomethingAsyncWithNoReturn(int seconds)
			throws InterruptedException {
		log.info("在 " + seconds + " 秒之后我才会开始工作!");
		TimeUnit.SECONDS.sleep(seconds);
		log.info("我可能在其它线程未结束，我就开始工作了!");
	}
}
