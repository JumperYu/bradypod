package com.bradypod.shop.item.center.cmd;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * RMI服务启动
 * 
 *
 * @author zengxm
 * @date 2015年9月20日
 *
 */
public class ShopServiceCommand {

	public static void main(String[] args) throws RemoteException {
	/*	System.setProperty("java.rmi.server.hostname", "192.168.1.2");
		System.setProperty("ENV", "dev");
		// 启动spring
		SpringContext.newInstance().buildContextByClassPathXml(
				"config/spring/applicationContext.xml",
				"config/spring/applicationContext-service.xml");*/
		Registry reg = LocateRegistry.getRegistry("192.168.1.2", 1991);
		reg.list();
	}

}
