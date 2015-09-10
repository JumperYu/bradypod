package com.yu.nio;

import java.rmi.RemoteException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringRMI {

	public static void main(String[] args) throws RemoteException {
		ApplicationContext context = startSpring();
		System.out.println("rmi started!");
		Business business = context.getBean("remoteService", Business.class);
		System.out.println(business.hello());
	}

	public static ApplicationContext startSpring() {
		return new ClassPathXmlApplicationContext("test-rmi.xml");
	}
}
