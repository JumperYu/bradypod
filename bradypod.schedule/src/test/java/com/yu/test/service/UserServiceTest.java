package com.yu.test.service;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yu.sys.po.User;
import com.yu.sys.service.UserService;

public class UserServiceTest {

	private ApplicationContext applicationContext;

	public UserService userService;

	// 1.启动spring工程需要找到对应的xml， 下面是示例
	@Before
	public void initApplicationContext() {
		System.setProperty("ENV", "release");
		applicationContext = new ClassPathXmlApplicationContext("/config/spring/applicationContext**.xml");
		userService = applicationContext.getBean(UserService.class);
	}

	@Test
	public void testSave() {
		
		User user = new User();
		user.setId(1L);
		user.setUid(UUID.randomUUID().toString().replaceAll("-", ""));
		user.setUid2(UUID.randomUUID().toString().replaceAll("-", ""));
		
		userService.save(user);
	}
}
