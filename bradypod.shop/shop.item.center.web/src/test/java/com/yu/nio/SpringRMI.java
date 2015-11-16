package com.yu.nio;

import java.rmi.RemoteException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;

public class SpringRMI {

	public static void main(String[] args) throws RemoteException, JsonProcessingException {
		ApplicationContext context = startSpring();
		HessianService hessianService = context.getBean("remoteHessianService", HessianService.class);
		MyEntity entity = new MyEntity();
		entity.setId(2);
		entity.setName("wyl");
		hessianService.insert(entity);
		System.out.println(hessianService.get(2));
		/*ItemInfoService itemInfoService = context.getBean("itemInfoService",
				ItemInfoService.class);
		ItemInfo itemInfo = new ItemInfo();
		itemInfo.setId(2L);
		itemInfo = itemInfoService.get(itemInfo);
		ObjectMapper objectMapper = new ObjectMapper();
		System.out.println(objectMapper.writeValueAsString(itemInfo));*/
	}

	public static ApplicationContext startSpring() {
		return new ClassPathXmlApplicationContext("test-rmi.xml");
	}
}
