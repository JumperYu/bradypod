package com.yu.cmd;

import org.quartz.JobDataMap;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yu.sys.job.TaskCheckingJob;
import com.yu.util.date.DateUtils;
import com.yu.util.quartz.QuartzUtil;

/**
 * 定时任务启动命令行
 *
 * @author zengxm
 * @date 2015年6月30日
 *
 */
public class QuartzCommand {

	private static final Logger log = LoggerFactory
			.getLogger(QuartzCommand.class);

	/**
	 * 定时调度系统启动主线程 <br/>
	 * 
	 * （1） 利用spring的quartz定时器设计 <br/>
	 * （2） 结合gearman的分布式任务分发 <br/>
	 * （3） 动态管理调度任务 <br/>
	 * 
	 * @param args
	 *            - interval 任务定时检测时间间隔, 不传则使用默认值
	 * @throws SchedulerException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws SchedulerException,
			InterruptedException {

		int interval = 10; // 任务检测默认间隔

		if (args == null || args.length >= 1)
			interval = Integer.parseInt(args[0]);

		JobDataMap jobDataMap = new JobDataMap();
		// 如果需要传递数据
		jobDataMap.put("lastModify", System.currentTimeMillis());

		// 创建 JobDetail 和 CronTrigger
		QuartzUtil.addJob("taskCheckingJob", "group", "taskCheckingTrigger", "group",
				TaskCheckingJob.class, "0/" + interval + " * * * * ?",
				DateUtils.nowadays(),
				DateUtils.strToDate("2999-12-30 23:59:59"), jobDataMap);
		// 启动quartz
		QuartzUtil.startJobs();

		// 主线程关闭, 触发回调
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				log.info(QuartzCommand.class.getSimpleName() + "系统正在退出");
				try {
					QuartzUtil.shutdownJobs();
				} catch (Exception e) {
					log.error("退出quartz出错", e);
				}
			}
		});
	}
}
