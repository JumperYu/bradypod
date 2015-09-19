package com.yu.shop.item.center.service.impl;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bradypod.common.po.GenericQueryParam;
import com.bradypod.shop.item.center.constants.CategoryInfoConstants;
import com.bradypod.shop.item.center.mapper.CtgInfoMapper;
import com.bradypod.shop.item.center.po.CtgInfo;

public class CtgInfoServiceTest {

	private static Logger log = LoggerFactory
			.getLogger(CtgInfoServiceTest.class);

	private ApplicationContext applicationContext;

	public CtgInfoMapper ctgInfoMapper;

	// 1.启动spring工程需要找到对应的xml， 下面是示例
	@Before
	public void initApplicationContext() {
		System.setProperty("ENV", "release");
		applicationContext = new ClassPathXmlApplicationContext(
				"/config/spring/applicationContext**.xml");
		ctgInfoMapper = applicationContext.getBean(CtgInfoMapper.class);
	}

	@Test
	public void testSave() {
		log.info("目前的开发环境是:" + System.getProperty("ENV"));
		CtgInfo categroy = new CtgInfo();
		categroy.setId(17L);
		categroy.setParentId(CategoryInfoConstants.ROOT_ID);
		categroy.setDepth(1);
		categroy.setName("男装");
		categroy.setNickName("男装");
		categroy.setDescription("衣服");
		categroy.setStatus(CategoryInfoConstants.STATUS_NORMAL);
		categroy.setCreateTime(new Date());
		categroy.setOrderNum(1);
		ctgInfoMapper.save(categroy);
	}

	@Test
	public void testGet() {
		log.info("目前的开发环境是:" + System.getProperty("ENV"));
		CtgInfo categroy = new CtgInfo();
		categroy.setId(1L);
		log.info(ctgInfoMapper.get(categroy).toString());
	}

	@Test
	public void testGetAll() throws Exception {
		log.info("目前的开发环境是:" + System.getProperty("ENV"));
		GenericQueryParam genericQueryParam = new GenericQueryParam(0, 15);
		/*genericQueryParam.setSortKey("id desc");*/
		log.info(ctgInfoMapper.listData(genericQueryParam).toString());
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
	}
}
