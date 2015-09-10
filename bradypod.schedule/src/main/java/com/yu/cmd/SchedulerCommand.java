package com.yu.cmd;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 定时任务启动命令行
 *
 * @author zengxm
 * @date 2015年6月30日
 *
 */
public class SchedulerCommand {

	private static final Logger log = LoggerFactory
			.getLogger(SchedulerCommand.class);

	/**
	 * 定时调度系统启动主线程
	 * 
	 * 1, 利用spring的scheduler定时器设计 <br/>
	 * 2, 结合gearman的分布式任务分发 <br/>
	 * 3, 动态管理调度任务 <br/>
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {

		// --> 1, TaskCheck定期去检查任务和新任务的加入

		// bala bala ..
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				log.info("System Existing ... "
						+ SchedulerCommand.class.getSimpleName());
			}
		});

		TimeUnit.SECONDS.sleep(5);

	}

}
