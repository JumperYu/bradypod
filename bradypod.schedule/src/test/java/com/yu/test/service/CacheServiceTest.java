package com.yu.test.service;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yu.sys.service.CacheService;
import com.yu.test.spring.LocalApplicationContextTest;

public class CacheServiceTest {

	static Logger log = LoggerFactory
			.getLogger(LocalApplicationContextTest.class);

	private ApplicationContext applicationContext;

	private CacheService cacheService;

	// 1.启动spring工程需要找到对应的xml， 下面是示例
	@Before
	public void initApplicationContext() {
		System.setProperty("ENV", "release");
		applicationContext = new ClassPathXmlApplicationContext(
				"/config/spring/applicationContext**.xml");
		cacheService = applicationContext.getBean(CacheService.class);
	}

	@Test
	public void testGetMap() {
		System.out.println(cacheService.getMap());
		System.out.println(cacheService.getList());
		System.out.println(cacheService.putMap());
		System.out.println(cacheService.getMap());
		System.out.println(cacheService.getList());
		cacheService.updateCache();
		System.out.println(cacheService.getMap());
		System.out.println(cacheService.getList());
		System.out.println(cacheService.putMap());
		System.out.println(cacheService.getMap());
		System.out.println(cacheService.getList());
	}
}
