package com.yu.shop.item.center.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
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
		item.setId(1L);
		// mapper.get(item);
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
		item.setDescription("商品");
		item.setPrice(1000);
		item.setStatus(1);
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
		item.setId(1L);
		item.setUserId(1L);
		item.setItemType(1);
		item.setCtgId(1L);
		item.setTitle("衣服");
		item.setDescription("商品");
		item.setPrice(1000);
		item.setStatus(1);
		item.setCreateTime(new Date());
		mapper.save(item);
	}

	@Test
	public void testListData() {
		Map<String, Object> genericQueryParam = new HashMap<>();
		genericQueryParam.put("pageNO", 0);
		genericQueryParam.put("pageSize", 10);
		genericQueryParam.put("id", 11);
		// System.out.println(mapper.listData(genericQueryParam));
		System.out.println(JSON.toJSONString(itemInfoService.findPageData(11L,
				1, 10)));
	}

	@Test
	public void testCountData() {
		Map<String, Object> genericQueryParam = new HashMap<>();
		System.out.println(mapper.countData(genericQueryParam));
		System.out.println(itemInfoService.count());
	}

	protected Logger log = LoggerFactory.getLogger(getClass());
}
