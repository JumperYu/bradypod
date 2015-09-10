package com.yu.shop.service;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yu.identity.constants.IdentityConstants;
import com.yu.identity.po.Identity;
import com.yu.identity.service.IdentityService;

public class IdentityServiceTest {

	private static Logger log = LoggerFactory
			.getLogger(IdentityServiceTest.class);

	private ApplicationContext applicationContext;

	public IdentityService identityService;

	// 1.启动spring工程需要找到对应的xml， 下面是示例
	@Before
	public void initApplicationContext() {
		System.setProperty("ENV", "release");
		applicationContext = new ClassPathXmlApplicationContext(
				"/config/spring/applicationContext**.xml");
		identityService = applicationContext.getBean(IdentityService.class);
	}

	@Test
	public void testSave() {
		log.info("目前的开发环境是:" + System.getProperty("ENV"));
		Identity identity = new Identity();
		identity.setCreateTime(new Date());
		identity.setModule(IdentityConstants.MODULE_BRADYPOD_SHOP_ITEM_CENTER_ATTRI);
		identity.setSubSys(IdentityConstants.SUB_SYS_BRADYPOD_SHOP);
		identity.setSystem(IdentityConstants.SYS_BRADYPOD);
		identity.setTableName("cms.t_identity");
		identity.setIdentity(1L);
		identityService.save(identity);
	}

	@Test
	public void testGet() {
		log.info("目前的开发环境是:" + System.getProperty("ENV"));
		Long id = identityService.generateId(IdentityConstants.SYS_BRADYPOD,
				IdentityConstants.SUB_SYS_BRADYPOD_SHOP,
				IdentityConstants.MODULE_BRADYPOD_SHOP_ITEM_CENTER_ATTRI,
				"cms.t_identity");
		log.info("id: " + id);
	}

	@Test
	public void testGetAll() {
		log.info("目前的开发环境是:" + System.getProperty("ENV"));
		log.info(identityService.getAll(new Identity()).toString());
		System.out.println(new Date(System.currentTimeMillis()));
	}

	@Test
	public void testUpdate() {
		log.info("目前的开发环境是:" + System.getProperty("ENV"));
		Identity entity = new Identity();
		entity.setId(17L);
		entity.setModule(IdentityConstants.MODULE_BRADYPOD_SHOP_ITEM_CENTER_ATTRI);
		entity.setSubSys(IdentityConstants.SUB_SYS_BRADYPOD_SHOP);
		entity.setSystem(IdentityConstants.SYS_BRADYPOD);
		entity.setTableName("cms.t_identity");
		entity.setIdentity(1L);
		identityService.update(entity);
	}

	@Test
	public void testDelete() {
		log.info("目前的开发环境是:" + System.getProperty("ENV"));
	}

	@Test
	public void testGetTasks() {
		log.info("目前的开发环境是:" + System.getProperty("ENV"));
	}

	@Test
	public void testGenerated() {

		log.info("目前的开发环境是:" + System.getProperty("ENV"));

		Long id = identityService.generateId(IdentityConstants.SYS_BRADYPOD,
				IdentityConstants.SUB_SYS_BRADYPOD_SHOP,
				IdentityConstants.MODULE_BRADYPOD_SHOP_ITEM_CENTER_ATTRI,
				"cms.t_identity");
		log.info("id: " + id);
	}
}
