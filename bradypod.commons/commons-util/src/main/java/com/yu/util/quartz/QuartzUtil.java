package com.yu.util.quartz;

import java.util.Date;
import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Quartz常用操作工具
 *
 * @author zengxm
 * @date 2015年7月1日
 * @version 1.0
 */
public class QuartzUtil {

	private static Logger log = LoggerFactory.getLogger(QuartzUtil.class);

	// 定义一下
	public static final String DEFAULT_GROUP = "default_group";

	/**
	 * 显示全部正在工作的作业任务, 经测试不能正确打印所有
	 * 
	 */
	public static void listAllWorkingJobs(Scheduler scheduler) {
		try {
			List<JobExecutionContext> executingJobs = scheduler
					.getCurrentlyExecutingJobs();
			log.info("executingJobs'size:" + executingJobs.size());
			for (JobExecutionContext executingJob : executingJobs) {
				JobDetail jobDetail = executingJob.getJobDetail();
				JobKey jobKey = jobDetail.getKey();
				Trigger trigger = executingJob.getTrigger();
				log.info(jobKey.toString());
				log.info(trigger.toString());
			}
		} catch (SchedulerException e) {
			log.error("显示全部正在工作的作业任务出错", e);
		}
	}

	/**
	 * 添加一个定时任务
	 * 
	 * @param jobName
	 *            - 任务名
	 * @param jobGroupName
	 *            - 任务组名
	 * @param triggerName
	 *            - 触发器名
	 * @param triggerGroupName
	 *            - 触发器组名
	 * @param jobClass
	 *            - 任务执行类
	 * @param cronExpression
	 *            - cron时间表达式
	 * @throws RuntimeException
	 *             - 出现错误则抛出运行时错误
	 */
	public static void addJob(String jobName, String jobGroup,
			String triggerName, String triggerGroup,
			Class<? extends Job> jobClass, String cronExpression,
			Date startTime, Date endTime, JobDataMap jobDataMap) {
		try {
			// 任务名
			JobKey jobKey = getJobKey(jobName, jobGroup);
			// 触发器
			TriggerKey triggerKey = TriggerKey.triggerKey(triggerName,
					triggerGroup);
			if (getScheduler().checkExists(jobKey)
					|| getScheduler().checkExists(triggerKey)) {
				// 如果已经存在, 说明整个调度流程是存在问题的
				throw new RuntimeException(
						String.format(
								"添加任务失败, 因为已经存在, [jobName:%s; jobGroup:%s;triggerName:%s;triggerGroup:%s]",
								jobName, jobGroup, triggerName, triggerGroup));
			} else {
				JobDetail jobDetail = JobBuilder.newJob(jobClass)
						.withIdentity(jobKey).build();
				CronTrigger trigger = TriggerBuilder
						.newTrigger()
						.withIdentity(triggerKey)
						.withSchedule(
								CronScheduleBuilder
										.cronSchedule(cronExpression))
						.startAt(startTime).endAt(endTime)
						.usingJobData(jobDataMap).build();
				getScheduler().scheduleJob(jobDetail, trigger);
			}
		} catch (Exception e) {
			log.error("添加作业异常", e);
		}
	}

	/**
	 * 删除某个任务
	 * 
	 * @param jobName
	 *            - 任务名
	 * @param jobGroup
	 *            - 任务组
	 * @param triggerName
	 *            - 触发器名
	 * @param triggerGroup
	 *            - 触发器组
	 * @param jobClass
	 *            - 任务执行类
	 * @param cronExpression
	 *            - cron时间表达式
	 * @throws RuntimeException
	 */
	public static void deleteJob(String jobName, String jobGroup,
			String triggerName, String triggerGroup,
			Class<? extends Job> jobClass, String cronExpression) {
		try {
			Scheduler scheduler = getScheduler();
			JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
			scheduler.deleteJob(jobKey);
			// 删除失败抛出异常
		} catch (Exception e) {
			log.error("删除作业异常", e);
		}
	}

	/**
	 * 启动任务
	 * 
	 * @param jobName
	 *            - 任务名
	 * @param jobGroup
	 *            - 任务组
	 */
	public static void startJob(String jobName, String jobGroup) {
		try {
			Scheduler scheduler = getScheduler();
			JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
			if (scheduler.checkExists(jobKey))
				scheduler.triggerJob(jobKey);
			else
				throw new RuntimeException(String.format(
						"启动任务失败, 因为job不存在, [jobName:%s; jobGroup:%s]", jobName,
						jobGroup));
		} catch (SchedulerException e) {
			log.error("启动作业异常", e);
		}
	}

	/**
	 * 暂停任务
	 * 
	 * @param jobName
	 *            - 任务名
	 * @param jobGroup
	 *            - 任务组
	 */
	public static void pauseJob(String jobName, String jobGroup) {
		try {
			Scheduler scheduler = getScheduler();
			JobKey jobKey = getJobKey(jobName, jobGroup);
			if (scheduler.checkExists(jobKey))
				scheduler.pauseJob(jobKey);
			else
				throw new RuntimeException(String.format(
						"暂停任务失败, 因为job不存在, [jobName:%s; jobGroup:%s]", jobName,
						jobGroup));
		} catch (SchedulerException e) {
			log.error("暂停作业异常", e);
		}
	}

	/**
	 * 修改触发器
	 * 
	 * @param triggerName
	 *            - 触发器名字
	 * @param triggerGroup
	 *            - 触发器组名
	 * @param cronExpression
	 *            - cron时间表达式
	 * @param startTime
	 *            - 开始时间
	 * @param endTime
	 *            - 结束时间
	 */
	public static void modifyJobTime(String triggerName, String triggerGroup,
			String cronExpression, Date startTime, Date endTime) {
		try {
			TriggerKey triggerKey = getTriggerKey(triggerName, triggerGroup);
			assertTriggerExists(triggerKey);

			Scheduler scheduler = getScheduler();
			// 获取旧的触发器
			CronTrigger trigger = (CronTrigger) scheduler
					.getTrigger(triggerKey);
			// 重新设定触发器
			trigger = trigger
					.getTriggerBuilder()
					.withSchedule(
							CronScheduleBuilder.cronSchedule(cronExpression))
					.startAt(startTime).endAt(endTime).build();
			scheduler.rescheduleJob(triggerKey, trigger);
		} catch (Exception e) {
			log.error("修改作业异常", e);
		}
	}

	/**
	 * 暂停所有定时任务
	 * 
	 * @throws SchedulerException
	 */
	public static void pauseJobs() {
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();
			if (!scheduler.isShutdown()) {
				scheduler.pauseAll();
			}
		} catch (Exception e) {
			log.error("暂停全部作业异常", e);
		}
	}

	/**
	 * 恢复全部任务
	 * 
	 * @throws SchedulerException
	 */
	public static void resumeJobs() {
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();
			scheduler.resumeAll();
		} catch (Exception e) {
			log.error("恢复全部作业异常", e);
		}
	}

	/**
	 * 启动所有定时任务
	 * 
	 * @throws SchedulerException
	 */
	public static void startJobs() {
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();
			scheduler.start();
		} catch (Exception e) {
			log.error("启动全部作业异常", e);
		}
	}

	/**
	 * 关闭所有定时任务
	 * 
	 * @throws SchedulerException
	 */
	public static void shutdownJobs() {
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();
			if (!scheduler.isShutdown()) {
				scheduler.shutdown();
			}
		} catch (Exception e) {
			log.error("停止全部作业异常", e);
		}
	}

	/**
	 * 从SchedulerFactory获取Scheduler
	 * 
	 * @return Scheduler
	 * @throws SchedulerException
	 */
	public static Scheduler getScheduler() throws SchedulerException {
		return schedulerFactory.getScheduler();
	}

	/**
	 * 获取作业标识
	 * 
	 * @param jobName
	 * @param jobGroup
	 * @return
	 */
	public static JobKey getJobKey(String name, String group) {
		JobKey jobKey = JobKey.jobKey(name, group);
		return jobKey;
	}

	/**
	 * 获取触发器标识
	 * 
	 * @param name
	 * @param group
	 * @return
	 */
	public static TriggerKey getTriggerKey(String name, String group) {
		TriggerKey triggerKey = TriggerKey.triggerKey(name, group);
		return triggerKey;
	}

	// 判断jobDetail是否存在
	public static void assertJobDetailExists(JobKey jobKey)
			throws SchedulerException {
		Scheduler scheduler = getScheduler();
		if (!scheduler.checkExists(jobKey)) {
			throw new RuntimeException(String.format(
					"[jobName:%s, jobGroupName:%s]不存在", jobKey.getName(),
					jobKey.getGroup()));
		}
	}

	// 判断jobDetail是否存在
	public static void assertJobDetailExists(String jobName, String jobGroupName)
			throws SchedulerException {
		assertJobDetailExists(getJobKey(jobName, jobGroupName));
	}

	// 判断trigger是否存在
	public static void assertTriggerExists(TriggerKey triggerKey)
			throws SchedulerException {
		Scheduler scheduler = getScheduler();
		if (!scheduler.checkExists(triggerKey)) {
			throw new RuntimeException(String.format(
					"[triggerName:%s, triggerGroupName:%s]不存在",
					triggerKey.getName(), triggerKey.getGroup()));
		}
	}

	// 判断trigger是否存在
	public static void assertTriggerExists(String triggerName,
			String triggerGroupName) throws SchedulerException {
		assertTriggerExists(getTriggerKey(triggerName, triggerGroupName));
	}

	private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();
}
