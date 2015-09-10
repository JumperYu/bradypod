package com.yu.test.spring;

import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import com.yu.task.AsyncTaskService;
import com.yu.task.MyTask;
import com.yu.task.TaskExecutorExample;
import com.yu.user.po.Account;
import com.yu.user.service.UserService;

/**
 * 
 * 非web环境启动spring工程
 *
 * @author zengxm
 * @date 2015年6月24日
 *
 */
public class LocalApplicationContextTest {

	private static Logger log = LoggerFactory
			.getLogger(LocalApplicationContextTest.class);

	private ApplicationContext applicationContext;

	// 1.启动spring工程需要找到对应的xml， 下面是示例
	@Before
	public void initApplicationContext() {
		applicationContext = new ClassPathXmlApplicationContext(
				"/config/spring/applicationContext**.xml");
		// applicationContext = new
		// FileSystemXmlApplicationContext("classpath:/config/spring/applicationContext**.xml");
	}

	@Test
	public void testGetBean() {
		log.info("目前的开发环境是:" + System.getenv("ENV"));
		UserService userService = applicationContext.getBean(UserService.class);
		Account account = new Account();
		account.setPassport("zxm");
		account.setPassword("123");
		boolean result = userService.validateAccount(account);
		log.info("验证结果是:" + result);
	}

	// 测试TaskExecutorPool
	@Test
	public void testTaskExecutor() {
		log.info("目前的开发环境是:" + System.getenv("ENV"));
		TaskExecutorExample taskExecutor = applicationContext
				.getBean(TaskExecutorExample.class);
		taskExecutor.printMessages();
	}

	// 测试TaskExecutorSchedule
	@Test
	public void testTaskExecutorSchedule() {
		log.info("目前的开发环境是:" + System.getenv("ENV"));
		ThreadPoolTaskScheduler scheduler = (ThreadPoolTaskScheduler) applicationContext
				.getBean("scheduler");
		// 秒 分 时 日 月 年 周
		scheduler.schedule(new MyTask(), new CronTrigger("* * * * * ?"));
		while (true) {
			// do nothing
		}
	}

	// 测试异步 不成功
	@Test
	public void testAnnotationTask() {
		while (true) {
			// do nothing
		}
	}

	// 测试quartz job
	@Test
	public void testJob() throws ExecutionException, InterruptedException {
		AsyncTaskService task = applicationContext
				.getBean(AsyncTaskService.class);
		// task.asyncServiceWithResult(10);
		task.asyncServiceWithNoResult(2);
		while (true) {
			// do nothing
		}
	}
}
