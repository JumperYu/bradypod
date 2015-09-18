package com.yu.shop.item.center.service.impl;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.bradypod.common.junit.BaseTest;
import com.bradypod.common.po.GenericQueryParam;
import com.bradypod.shop.item.center.mapper.ItemInfoMapper;
import com.bradypod.shop.item.center.po.ItemInfo;

public class ItemInfoServiceImplTest extends BaseTest {

	ItemInfoMapper mapper;

	@Before
	public void getBean() {
		mapper = applicationContext.getBean(ItemInfoMapper.class);
	}

	@Test
	public void testGet() {
		ItemInfo item = new ItemInfo();
		item.setId(1L);
		mapper.get(item);
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
		item.setId(3L);
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
//		genericQueryParam.put("id", "2");
		System.out.println(mapper.countData(genericQueryParam));
	}
}
