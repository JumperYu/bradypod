package com.yu.shop.item.center.service.impl;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bradypod.common.po.GenericQueryParam;
import com.bradypod.shop.item.center.mapper.ItemInfoMapper;
import com.bradypod.shop.item.center.po.ItemInfo;
import com.bradypod.shop.item.center.service.ItemInfoService;

public class ItemInfoServiceImplTest {

	ItemInfoMapper mapper;
	ItemInfoService itemInfoService;
	
	protected ApplicationContext applicationContext;

	// 1.启动spring工程需要找到对应的xml， 下面是示例
	@Before
	public void initApplicationContext() {
		System.setProperty("ENV", "release");
		applicationContext = new ClassPathXmlApplicationContext(
				"/config/spring/applicationContext-test.xml");
		mapper = applicationContext.getBean(ItemInfoMapper.class);
		itemInfoService = applicationContext.getBean(ItemInfoService.class);
	}

	@Test
	public void testGet() {
		ItemInfo item = new ItemInfo();
		item.setId(2L);
//		mapper.get(item);
		log.info(itemInfoService.get(item).toString());
	}

	@Test
	public void testUpdate() {
		ItemInfo item = new ItemInfo();
		item.setId(2L);
		item.setUserId(1L);
		item.setItemType(1);
		item.setCtgId(1L);
		item.setTitle("衣服");
		item.setPicUrlList("1.jpg;2.jpg");
		item.setDescription("商品");
		item.setAttriJson("{1:3,3:4}");
		item.setPrice(1000L);
		item.setStatus(1);
		item.setApprovalTime(new Date());
		item.setCreateTime(new Date());
		mapper.update(item);
	}

	@Test
	public void testDelete() {
		ItemInfo item = new ItemInfo();
		item.setId(1L);
		mapper.delete(item);
	}

	@Test
	public void testSave() {
		ItemInfo item = new ItemInfo();
		item.setId(4L);
		item.setUserId(1L);
		item.setItemType(1);
		item.setCtgId(1L);
		item.setTitle("衣服");
		item.setPicUrlList("1.jpg;2.jpg");
		item.setDescription("商品");
		item.setAttriJson("{1:2,3:4}");
		item.setPrice(1000L);
		item.setStatus(1);
		item.setApprovalTime(new Date());
		item.setCreateTime(new Date());
		mapper.save(item);
	}

	@Test
	public void testListData() {
		GenericQueryParam genericQueryParam = new GenericQueryParam(0, 15);
		genericQueryParam.setSortKey("id desc");
		System.out.println(mapper.listData(genericQueryParam));
	}

	@Test
	public void testCountData() {
		GenericQueryParam genericQueryParam = new GenericQueryParam();
		// genericQueryParam.put("id", "2");
		System.out.println(mapper.countData(genericQueryParam));
	}
	
	protected Logger log = LoggerFactory.getLogger(getClass());
}
