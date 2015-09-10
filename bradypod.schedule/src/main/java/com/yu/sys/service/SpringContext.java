package com.yu.sys.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yu.util.validate.AssertUtil;

public class SpringContext {

	private static ApplicationContext applicationContext = null;

	// 获取上下文对象
	public synchronized static ApplicationContext getApplicationContext() {
		// 加上运行时判断异常
		if (applicationContext == null) {
			// 初始化后
			applicationContext = new ClassPathXmlApplicationContext(
					"/config/spring/applicationContext**.xml");
			// 如果未能初始化
			AssertUtil.notNull(applicationContext, "spring应用上下文未能初始化");
		}
		return applicationContext;
	}

	// 获取Service
	public static TaskService getTaskService() {
		return getApplicationContext().getBean(TaskService.class);
	}

	// 也可以这样获取
	public static <T> T getBean(Class<T> clazz) {
		return getApplicationContext().getBean(clazz);
	}
}
