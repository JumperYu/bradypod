package com.yu.shop.item.center.service.impl;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.bradypod.common.junit.BaseTest;
import com.bradypod.common.po.GenericQueryParam;
import com.bradypod.shop.item.center.mapper.BrandInfoMapper;
import com.bradypod.shop.item.center.po.BrandInfo;

public class BrandInfoServiceTest extends BaseTest {

	BrandInfoMapper mapper;

	@Before
	public void getBean() {
		mapper = applicationContext.getBean(BrandInfoMapper.class);
	}

	@Test
	public void testGet() {
		BrandInfo brandInfo = new BrandInfo();
		brandInfo.setId(1L);
		log.info(mapper.get(brandInfo).toString());
	}

	@Test
	public void testUpdate() {

	}

	@Test
	public void testDelete() {

	}

	@Test
	public void testSave() {
		BrandInfo brandInfo = new BrandInfo();
		brandInfo.setId(2L);
		brandInfo.setCreateTime(new Date());
		brandInfo.setName("豪斯特");
		brandInfo.setLogoPath("1.jpg");
		brandInfo.setOrderNum(1);
		brandInfo.setStatus(1);
		mapper.save(brandInfo);
	}
	
	@Test
	public void testGetAll(){
		GenericQueryParam genericQueryParam = new GenericQueryParam(0, 15);
		genericQueryParam.setSortKey("id desc");
		log.info(mapper.listData(genericQueryParam).toString());
	}
}
