package com.yu.sys.job;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger.TriggerState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yu.sys.constant.TaskState;
import com.yu.sys.po.Task;
import com.yu.sys.service.SpringContext;
import com.yu.sys.service.TaskService;
import com.yu.util.quartz.QuartzUtil;

@DisallowConcurrentExecution
public class TaskCheckingJob implements Job {

	private static final Logger log 	= LoggerFactory
			.getLogger(TaskCheckingJob.class);

	public static final String PACKAGE_PATH = "com/yu/job";
	public static final String QUALIFIED_CLASS_PATH = "com.yu.job.";

	private static HashSet<Class<?>> jobs = new HashSet<Class<?>>();

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			// checkingClassesFromDir();
			Scheduler scheduler = QuartzUtil.getScheduler();// context.getScheduler();
			checkingTasksFromDB(scheduler);
		} catch (Exception e) {
			log.error("TaskCheckingJob定时执行出错", e);
		}
	}

	/**
	 * 从数据库读取配置的任务
	 * 
	 * @param scheduler
	 *            - org.quartz.Scheduler
	 * 
	 * @throws ClassNotFoundException
	 * @throws SchedulerException
	 */
	public void checkingTasksFromDB(Scheduler scheduler)
			throws ClassNotFoundException, SchedulerException {
		// 检查所有任务, 启动需要执行的任务, 或者恢复需要执行的任务
		TaskService taskService = SpringContext.getTaskService();
		List<Task> tasks = taskService.listAllTasks();
		// 循环迭代
		for (Task task : tasks) {

			if (task.getState() == TaskState.DEPRECATED)
				continue;
			// 检查是否存在, 不存在则视状态决定是否添加计划任务
			boolean isExists = scheduler.checkExists(QuartzUtil.getJobKey(
					task.getJobName(), task.getJobGroupName()));
			if (isExists) {
				TriggerState triggerState = scheduler
						.getTriggerState(QuartzUtil.getTriggerKey(
								task.getTriggerName(),
								task.getTriggerGroupName()));
				// 如果外部修改了任务状态
				if (task.getState() == TaskState.PAUSE
						&& TriggerState.valueOf(triggerState.name()) != TriggerState.PAUSED) {
					// 如果外部修改了任务状态, 则暂停任务
					log.info("暂停任务," + task);
					QuartzUtil.pauseJob(task.getJobName(),
							task.getJobGroupName());
				} else if (task.getState() == TaskState.WAITING
						&& TriggerState.valueOf(triggerState.name()) == TriggerState.NORMAL) {
					// 如果任务已经由等待进入运行状态, 將状态修改為运行中
					log.info("启动任务," + task);
					taskService.setTaskStateToRunning(task);
				} else if (task.getState() == TaskState.MODIFIED) {
					// 前端修改了配置, 对应调整触发器
					log.info("修改任务," + task);
					QuartzUtil.modifyJobTime(task.getTriggerName(),
							task.getTriggerGroupName(), task.getJobCronTime(),
							task.getStartTime(), task.getEndTime());
					taskService.setTaskStateToWaiting(task.getTaskId());
				}
				continue;
			}// --> end if

			// 不存在且处于运行状态或处于等待状态
			if (task.getState() != TaskState.PAUSE) {
				log.info(String.format("任务不在quartz中, 尝试添加任务, %s", task));
				JobDataMap jobDataMap = new JobDataMap();
				jobDataMap.put("lastModify", task.getLastModify());
				addJob(task.getJobName(), task.getJobGroupName(),
						task.getTriggerName(), task.getTriggerGroupName(),
						loadClass(task.getJobClassName()),
						task.getJobCronTime(), task.getStartTime(),
						task.getEndTime(), jobDataMap);
			}// --> end if

		}// --> end for
	}

	// 查找固定路径下的类, 实时加入到任务里面
	public void checkingClassesFromDir(Scheduler scheduler)
			throws URISyntaxException, ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		// 定义包路径、限定类路径
		URL dirURL = getClass().getClassLoader().getResource(PACKAGE_PATH);

		log.info("Checking classes from package path " + dirURL);

		// 浏览包路径下的类
		File dir = new File(dirURL.toURI());
		if (dir.exists() && dir.isDirectory() && dir.listFiles() != null) {
			for (File file : dir.listFiles()) {// ... begin for
				String className = file.getName();
				if (className.indexOf(".class") != -1) {// ... begin if
					Class<?> clazz = getClass().getClassLoader().loadClass(
							QUALIFIED_CLASS_PATH
									+ className.replace(".class", ""));
					// 添加到全局数组里面
					if (!jobs.contains(clazz) && jobs.add(clazz)) {
						log.info(clazz.getName() + "," + jobs.size());
						// clazz.isInstance(Job.class)
						if (clazz.getName().indexOf("TaskCheckingJob") == -1
								&& clazz.getName().indexOf("ExampleJob") == -1) {
							log.info("找到白菜");
							JobDataMap jobDataMap = new JobDataMap();
							addJob(clazz.getName(), clazz.getName(),
									clazz.getName(), clazz.getName(), clazz,
									"0/2 * * * * ?", new Date(), new Date(),
									jobDataMap);
						} else {
							log.info("找不到白菜," + clazz.getName() + ","
									+ clazz.isInstance(Job.class));
						}
					}
				}// ... 只处理.class文件
			}// ... end for
		} else {
			log.error("加载路径并不存在或者不是一个文件夹");
		}// ... end if-else
	}

	/**
	 * 加载类
	 * 
	 * @param className
	 *            - ex: com.yu.job.xxx
	 * @return - Class<?>
	 * @throws ClassNotFoundException
	 */
	public Class<?> loadClass(String className) throws ClassNotFoundException {
		Class<?> clazz = getClass().getClassLoader().loadClass(className);
		return clazz;
	}

	// 启动任务
	@SuppressWarnings("unchecked")
	public void addJob(String jobName, String jobGroup, String triggerName,
			String triggerGroup, Class<?> clazz, String cronExpression,
			Date startTime, Date endTime, JobDataMap jobDataMap) {
		QuartzUtil.addJob(jobName, jobGroup, triggerName, triggerGroup,
				(Class<? extends Job>) clazz, cronExpression, startTime,
				endTime, jobDataMap);
	}
}
