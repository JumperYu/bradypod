package com.bradypod.shop.item.center.cmd;

import java.rmi.RemoteException;

import com.bradypod.util.spring.SpringContext;

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
		// -Djava.rmi.server.hostname=192.168.1.116;
		// java -jar -server shop.item.center.service.impl.jar -Djava.rmi.server.hostname=112.124.126.31 -Denv=release
		System.setProperty("ENV", "release");
		// 启动spring
		SpringContext.newInstance().buildContextByClassPathXml(
				"config/spring/applicationContext.xml",
				"config/spring/applicationContext-service.xml");
		
		System.err.println("Server startup");
	}

}
