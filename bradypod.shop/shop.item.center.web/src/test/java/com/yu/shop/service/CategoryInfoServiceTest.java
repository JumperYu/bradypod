package com.yu.shop.service;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bradypod.shop.item.center.constants.CategoryInfoConstants;
import com.bradypod.shop.item.center.po.CategoryInfo;
import com.bradypod.shop.item.center.service.CategoryInfoService;
import com.yu.util.json.JacksonUtil;

public class CategoryInfoServiceTest {

	private static Logger log = LoggerFactory
			.getLogger(CategoryInfoServiceTest.class);

	private ApplicationContext applicationContext;

	public CategoryInfoService categoryInfoService;

	// 1.启动spring工程需要找到对应的xml， 下面是示例
	@Before
	public void initApplicationContext() {
		System.setProperty("ENV", "release");
		applicationContext = new ClassPathXmlApplicationContext(
				"/config/spring/applicationContext**.xml");
		categoryInfoService = applicationContext
				.getBean(CategoryInfoService.class);
	}

	@Test
	public void testSave() {
		log.info("目前的开发环境是:" + System.getProperty("ENV"));
		CategoryInfo categroy = new CategoryInfo();
		categroy.setParentId(CategoryInfoConstants.ROOT_ID);
		categroy.setDepth(1);
		categroy.setOrderNum(1L);
		categroy.setName("男装");
		categroy.setNickName("男装");
		categroy.setDescription("衣服");
		categroy.setStatus(CategoryInfoConstants.STATUS_NORMAL);
		categroy.setCreateTime(new Date());
		categoryInfoService.save(categroy);

	}

	@Test
	public void testGet() {
		log.info("目前的开发环境是:" + System.getProperty("ENV"));
		CategoryInfo categroy = new CategoryInfo();
		categroy.setId(1L);
		log.info(categoryInfoService.get(categroy).toString());
	}

	@Test
	public void testGetAll() throws Exception {
		log.info("目前的开发环境是:" + System.getProperty("ENV"));
		// log.info(categoryInfoService.getAllAvailableInfo().toString());
		categoryInfoService.getCategoryInfoBO();
	}

	@Test
	public void testUpdate() {
		log.info("目前的开发环境是:" + System.getProperty("ENV"));
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
	public void testPageData() throws Exception {
		log.info("目前的开发环境是:" + System.getProperty("ENV"));
		log.info(JacksonUtil.beanToJson(categoryInfoService.findCategories(10,
				1, 1L, 2)));
	}
}
