package com.yu.shop.item.center.service.impl;

import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

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
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		int count = 1000;
		
		for (int i = 0; i < count; i++) {
			BrandInfo brandInfo = new BrandInfo();
			brandInfo.setId(new Random().nextLong() * 50000000L);
			brandInfo.setName(UUID.randomUUID().toString().replaceAll("-", ""));
			brandInfo.setLogoPath(UUID.randomUUID().toString().replaceAll("-", ""));
			mapper.update(brandInfo);
		}
		
		stopWatch.stop();
		System.out.println(stopWatch.getTime());
	}

	@Test
	public void testDelete() {

	}

	@Test
	public void testSave() throws InterruptedException {
		final int count = 1000000;
		int thread = 200;
		final int page = 5000;
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		final String studentId = UUID.randomUUID().toString().replaceAll("-", "");
		
		final String performanceTypeId = UUID.randomUUID().toString().replaceAll("-", "");
		
		final AtomicLong atomicResourceId = new AtomicLong(1L);
		final AtomicLong atomicClassId = new AtomicLong(1);
		
		ThreadPool threaPool = new ThreadPool(thread);
		threaPool.executeThread(new ThreadWorker() {
			
			@Override
			public void execute() {
				
//				final String classId = UUID.randomUUID().toString().replaceAll("-", ""); // classId
				final Long classId = atomicClassId.getAndIncrement(); // classId
				
				for (int i = 0; i < page; i++) {
					BrandInfo brandInfo = new BrandInfo();			
					
//					brandInfo.setIdUuid(UUID.randomUUID().toString().replaceAll("-", ""));
//					brandInfo.setTestcaseName(UUID.randomUUID().toString().replaceAll("-", ""));
//					brandInfo.setTestcasePoint(UUID.randomUUID().toString().replaceAll("-", ""));
					
//					final String resourceId = UUID.randomUUID().toString().replaceAll("-", "");
					
					brandInfo.setResourceid(atomicResourceId.getAndIncrement());
//					brandInfo.setResourceid(resourceId);
					
//					brandInfo.setClassId(classId);
					brandInfo.setClassId(classId);
					brandInfo.setStudentId(studentId);
					
					brandInfo.setCreateTime(new Date());
					
					brandInfo.setPerformanceType("1");
					brandInfo.setPerformanceTypeId(performanceTypeId);
					
					brandInfo.setValue(Long.valueOf(new Random().nextInt(10)));
					
					brandInfo.setVersion(Long.valueOf(new Random().nextInt(100)));
					
					brandInfo.setIsDeleted(-1);
					
					mapper.save(brandInfo);
				}
			}
		});
		
		stopWatch.stop();
		System.out.println(stopWatch.getTime()  + "  " + stopWatch.getTime() / count);
		
	}
	
	@Test
	public void testSaveSingleTPS() throws InterruptedException {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		int count = 1;
		
		
		
		final String resourceId = UUID.randomUUID().toString();
		final String classId = UUID.randomUUID().toString();
		final String studentId = UUID.randomUUID().toString();
		final String performanceTypeId = UUID.randomUUID().toString();
		
		for(int i = 0; i < count; i++) {
		
			BrandInfo brandInfo = new BrandInfo();
		
			
//			brandInfo.setId(5L);
//			brandInfo.setName(UUID.randomUUID().toString().replaceAll("-", ""));
//			brandInfo.setLogoPath(UUID.randomUUID().toString().replaceAll("-", ""));
//			brandInfo.setIdUuid(UUID.randomUUID().toString().replaceAll("-", ""));
//			brandInfo.setTestcaseName(UUID.randomUUID().toString().replaceAll("-", ""));
//			brandInfo.setTestcasePoint(UUID.randomUUID().toString().replaceAll("-", ""));
			
			
//			brandInfo.setResourceid(resourceId);
//			brandInfo.setClassId(classId);
			brandInfo.setCreateTime(new Date());
			brandInfo.setPerformanceType("1");
			brandInfo.setPerformanceTypeId(performanceTypeId);
			brandInfo.setStudentId(studentId);
			brandInfo.setValue(1L);
			brandInfo.setVersion(100L);
			brandInfo.setIsDeleted(-1);
			
			mapper.save(brandInfo);
		
		}
		
		stopWatch.stop();
		System.out.println(stopWatch.getTime());
	}

	@Test
	public void testGetAll() {
		GenericQueryParam genericQueryParam = new GenericQueryParam(0, 15);
		genericQueryParam.setSortKey("id desc");
		log.info(mapper.listData(genericQueryParam).toString());
	}
}
