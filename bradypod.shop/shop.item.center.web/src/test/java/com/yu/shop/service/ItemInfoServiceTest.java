package com.yu.shop.service;

import java.rmi.RemoteException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bradypod.shop.item.center.po.ItemInfo;
import com.bradypod.shop.item.center.service.ItemInfoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ItemInfoServiceTest {

	public static void main(String[] args) throws RemoteException, JsonProcessingException {
		ApplicationContext context = startSpring();
		ItemInfoService itemInfoService = context.getBean("itemInfoService", ItemInfoService.class);
		ItemInfo itemInfo = new ItemInfo();
		itemInfo.setId(1L);
		itemInfo = itemInfoService.get(itemInfo);
		ObjectMapper objectMapper = new ObjectMapper();
		System.out.println(objectMapper.writeValueAsString(itemInfo));
	}

	public static ApplicationContext startSpring() {
		return new ClassPathXmlApplicationContext("test-rmi.xml");
	}
}
