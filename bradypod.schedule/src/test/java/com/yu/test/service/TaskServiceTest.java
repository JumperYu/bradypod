package com.yu.test.service;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yu.sys.constant.TaskState;
import com.yu.sys.constant.TaskType;
import com.yu.sys.po.Task;
import com.yu.sys.service.TaskService;
import com.yu.test.spring.LocalApplicationContextTest;
import com.yu.util.date.DateUtils;

public class TaskServiceTest {

	private static Logger log = LoggerFactory
			.getLogger(LocalApplicationContextTest.class);

	private ApplicationContext applicationContext;

	public TaskService taskService;

	// 1.启动spring工程需要找到对应的xml， 下面是示例
	@Before
	public void initApplicationContext() {
		System.setProperty("ENV", "release");
		applicationContext = new ClassPathXmlApplicationContext(
				"/config/spring/applicationContext**.xml");
		taskService = applicationContext.getBean(TaskService.class);
	}

	@Test
	public void testSave() {
		log.info("目前的开发环境是:" + System.getProperty("ENV"));

		long taskId = DateUtils.getCurrentSeconds();

		Task task = new Task();
		task.setTaskId(taskId);
		task.setLastOperator("zengxm");
		task.setCreateOperator("zengxm");
		task.setState(TaskState.WAITING);
		task.setTaskDesc("网络监控任务");
		task.setTaskType(TaskType.CLASS_FILE);
		task.setStartTime(DateUtils.strToDate("2015-07-09 18:10:10"));
		task.setEndTime(DateUtils.strToDate("2025-07-09 18:10:10"));
		task.setJobClassName("com.yu.po.Task");
		task.setJobCronTime("0/5 * * * * ?");
		task.setJobClassPath("src/main/java");
		task.setJobGroupName("group");
		task.setJobName("job_" + taskId);
		task.setTriggerName("trigger_" + taskId);
		task.setTriggerGroupName("trigger");
		taskService.addTask(task);

		log.info("" + task.getId());
	}

	@Test
	public void testGet() {
		log.info("目前的开发环境是:" + System.getProperty("ENV"));

		Task task = new Task();
		task.setTaskId(1436435579);
		log.info("" + task);
	}

	@Test
	public void testGetAll() {
		log.info("目前的开发环境是:" + System.getProperty("ENV"));
		log.info(taskService.getAll(new Task()).toString());
		System.out.println(new Date(System.currentTimeMillis()));
	}

	@Test
	public void testUpdate() {
		log.info("目前的开发环境是:" + System.getProperty("ENV"));
		List<Task> tasks = taskService.getAll(new Task());
		Task[] arrays = new Task[tasks.size()];
		for (int i = 0; i < tasks.size(); i++) {
			arrays[i] = tasks.get(i);
			// taskService.update(tasks.get(i));
		}
		taskService.batchSetTaskStateToRunning(arrays);
	}

	@Test
	public void testDelete() {
		log.info("目前的开发环境是:" + System.getProperty("ENV"));
		Task task = new Task();
		task.setTaskId(1436435579);
		int effectedRow = taskService.delete(task);
		log.info("删除成功行数:" + effectedRow);
	}

	@Test
	public void testGetTasks() {
		log.info("目前的开发环境是:" + System.getProperty("NV"));
		Task task = new Task();
		task.setState(TaskState.PAUSE);
		List<Task> tasks = taskService.listTasks(task);
		log.info("删除成功行数:" + tasks);
	}
}
