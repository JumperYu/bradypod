package com.bradypod.search.lucene;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.bradypod.shop.item.center.po.ItemInfo;
import com.bradypod.shop.item.center.service.ItemInfoService;
import com.bradypod.util.bean.BeanCopyUtil;

public class ItemInfoServiceTest {

	public static void main(String[] args) throws IOException {
		testSearch();
//		testCreate();
	}

	static ItemInfoIndexService itemInfoIndexService = new ItemInfoIndexService();

	public static void testSearch() {
		ItemInfoIndex itemIndex = new ItemInfoIndex();
		itemIndex.setTitle("情侣");
		itemInfoIndexService.searchIndex(itemIndex);
	}

	public static void testCreate() throws IOException {
		ApplicationContext context = startSpring();
		ItemInfoService itemInfoService = context.getBean("itemInfoService", ItemInfoService.class);
		ItemInfo itemInfo = new ItemInfo();
		itemInfo.setId(2L);
		itemInfo = itemInfoService.get(itemInfo);

		ItemInfoIndex itemIndex = BeanCopyUtil.copyProperties(itemInfo, ItemInfoIndex.class);
		System.out.println(JSON.toJSONString(itemIndex));
		
		itemInfoIndexService.createIndex(itemIndex);
	}

	public static ApplicationContext startSpring() {
		return new ClassPathXmlApplicationContext("test-rmi.xml");
	}
}
