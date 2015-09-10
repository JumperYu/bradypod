package com.yu.util.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring的应用上下文获取工具
 *
 * @author zengxm
 * @date 2015年6月30日
 *
 */
public class SpringContextUtil {

	private static final String APPLICATION_CONTEXT_XML_PATH = "/config/spring/applicationContext**.xml";

	private static ApplicationContext context;

	/**
	 * 获取应用上下文
	 * 
	 * @return 如果为空在加载类路径中的xml文件, 不为空则共用同一个上下文
	 */
	public static ApplicationContext getContext() {
		if (context == null)
			context = new ClassPathXmlApplicationContext(
					APPLICATION_CONTEXT_XML_PATH);
		return context;
	}

	public static void setContext(ApplicationContext context) {
		SpringContextUtil.context = context;
	}

	/**
	 * 通过类名从应用上下文加载bean
	 * 
	 * @param clazz
	 * 
	 * @return 对应的类实例
	 */
	public static <T> T getBean(Class<T> clazz) {
		return getContext().getBean(clazz);
	}

	/**
	 * 通过beanName从应用上下文加载bean
	 * 
	 * @param beanName
	 * 
	 * @return Object基本对象, 需要强制转换使用
	 */
	public static Object getBean(String beanName) {
		return getContext().getBean(beanName);
	}
}
