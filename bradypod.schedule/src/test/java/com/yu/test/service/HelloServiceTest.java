package com.yu.test.service;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yu.dubbo.HelloService;

/**
 * 测试dubbo服务
 * 
 * @author zengxm
 *
 */
public class HelloServiceTest {

	private static Logger log = LoggerFactory.getLogger(HelloServiceTest.class);

	private ApplicationContext applicationContext;

	public HelloService helloService;

	// 1.启动spring工程需要找到对应的xml， 下面是示例
	@Before
	public void initApplicationContext() {
		System.setProperty("ENV", "release");
		applicationContext = new ClassPathXmlApplicationContext(
				"/config/spring/applicationContext**.xml");
		helloService = applicationContext.getBean(HelloService.class);
	}

	@Test
	public void testHello() {
		log.info(helloService.hello("zxm"));
	}
}
