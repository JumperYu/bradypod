package com.yu.nio;

import java.rmi.RemoteException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bradypod.shop.item.center.po.ItemInfo;
import com.bradypod.shop.item.center.service.ItemInfoService;

public class SpringRMI {

	public static void main(String[] args) throws RemoteException {
		ApplicationContext context = startSpring();
		ItemInfoService itemInfoService = context.getBean("itemInfoService",
				ItemInfoService.class);
		ItemInfo itemInfo = new ItemInfo();
		itemInfo.setId(2L);
		System.out.println(itemInfoService.get(itemInfo));
	}

	public static ApplicationContext startSpring() {
		return new ClassPathXmlApplicationContext("test-rmi.xml");
	}
}
