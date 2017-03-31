package com.yu.shop.item.center.service.impl;

import java.util.UUID;

import org.apache.commons.lang.time.StopWatch;
import org.junit.Before;
import org.junit.Test;

import com.bradypod.common.junit.BaseTest;
import com.bradypod.common.po.GenericQueryParam;
import com.bradypod.shop.item.center.mapper.BrandInfoMapper;
import com.bradypod.shop.item.center.po.BrandInfo;
import com.bradypod.util.thread.ThreadPool;
import com.bradypod.util.thread.ThreadWorker;

public class BrandInfoServiceTest extends BaseTest {

	BrandInfoMapper mapper;

	@Before
	public void getBean() {
		initApplicationContext();
		mapper = applicationContext.getBean(BrandInfoMapper.class);
	}

	@Test
	public void testGet() {
		BrandInfo brandInfo = new BrandInfo();
		brandInfo.setId(1L);
		System.out.println(mapper.get(brandInfo));
	}

	@Test
	public void testUpdate() {

	}

	@Test
	public void testDelete() {

	}

	@Test
	public void testSave() throws InterruptedException {
		int count = 1000000;
		int thread = 100;
		int page = 10000;
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		ThreadPool threaPool = new ThreadPool(thread);
		threaPool.executeThread(new ThreadWorker() {
			
			@Override
			public void execute() {
				for (int i = 0; i < page; i++) {
					BrandInfo brandInfo = new BrandInfo();
					brandInfo.setName(UUID.randomUUID().toString().replaceAll("-", ""));
					brandInfo.setLogoPath(UUID.randomUUID().toString().replaceAll("-", ""));
					mapper.save(brandInfo);
				}
			}
		});
		
		
		stopWatch.stop();
		System.out.println(stopWatch.getTime()  + "  " + count / stopWatch.getTime());
		
		while(true) {
			
		}
	}

	@Test
	public void testGetAll() {
		GenericQueryParam genericQueryParam = new GenericQueryParam(0, 15);
		genericQueryParam.setSortKey("id desc");
		log.info(mapper.listData(genericQueryParam).toString());
	}
}
