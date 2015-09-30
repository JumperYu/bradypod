package com.bradypod.common.junit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * junit可以继承这里
 *
 * @author zengxm
 * @date 2015年8月30日
 *
 */
public class BaseTest {

	protected Logger log = LoggerFactory.getLogger(getClass());

	protected ApplicationContext applicationContext;

	// 1.启动spring工程需要找到对应的xml， 下面是示例
//	@Before
	public void initApplicationContext() {
		System.setProperty("ENV", "release");
		applicationContext = new ClassPathXmlApplicationContext(
				"/config/spring/applicationContext**.xml");
	}
}
