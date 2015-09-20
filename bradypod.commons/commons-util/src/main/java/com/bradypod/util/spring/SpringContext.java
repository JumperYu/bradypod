package com.bradypod.util.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yu.util.validate.AssertUtil;

/**
 * 简单的上下文创建器
 *
 * @author zengxm
 * @date 2015年9月20日
 *
 */
public class SpringContext {

	private static SpringContext instance = new SpringContext();

	public static SpringContext newInstance() {
		return SpringContext.instance;
	}

	// 获取上下文对象
	public ApplicationContext getDefaultApplicationContext() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"/config/spring/applicationContext**.xml");
		// 如果未能初始化
		AssertUtil.notNull(applicationContext, "spring应用上下文未能初始化");
		return applicationContext;
	}

	// 根据配置创建上下文
	public ApplicationContext buildContextByClassPathXml(String... config) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				config);
		// 如果未能初始化
		AssertUtil.notNull(applicationContext, "spring应用上下文未能初始化");
		return applicationContext;
	}

	/* not generate */
	private SpringContext() {

	}
}
