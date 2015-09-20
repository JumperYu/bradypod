package com.bradypod.shop.item.center.cmd;

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

	public static void main(String[] args) {
		// 启动spring
		SpringContext.newInstance().buildContextByClassPathXml(
				"config/spring/applicationContext.xml",
				"config/spring/applicationContext-service.xml");
	}

}
