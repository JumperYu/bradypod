package com.yu.shop.item.center.service.impl;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.bradypod.common.junit.BaseTest;
import com.bradypod.common.po.GenericQueryParam;
import com.bradypod.shop.item.center.mapper.CtgItemMapper;
import com.bradypod.shop.item.center.po.CtgItem;
import com.bradypod.shop.item.center.po.ItemInfo;
import com.bradypod.shop.item.center.service.CtgItemService;

public class CtgItemServiceImplTest extends BaseTest {

	CtgItemMapper mapper;
	CtgItemService ctgItemService;

	@Before
	public void getBean() {
		mapper = applicationContext.getBean(CtgItemMapper.class);
		ctgItemService = applicationContext.getBean(CtgItemService.class);
	}

	@Test
	public void testGet() {
		log.info(ctgItemService.get(new CtgItem()).toString());
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
		// mapper.update(item);
	}

	@Test
	public void testDelete() {
		// mapper.delete(item);
	}

	@Test
	public void testSave() {
		CtgItem ctgItem = new CtgItem();
		ctgItem.setCtgId(1L);
		ctgItem.setItemId(1L);
		ctgItemService.save(ctgItem);
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

	@Test
	public void testGetAll() {
		CtgItem ctgItem = new CtgItem();
		ctgItem.setCtgId(1L);
		log.info(mapper.getAll(ctgItem).toString());
	}
}
