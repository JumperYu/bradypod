package com.bradypod.shop.item.center.remote;

import com.bradypod.shop.item.center.service.impl.HomeServiceImpl;

/**
 * 启动ServiceProvider
 *
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015年10月22日 下午12:44:36
 */
public class Server {

	public static void main(String[] args) throws Exception {

		String host = "192.168.1.199";
		int port = 4181;

		ServiceProvider provider = new ServiceProvider();

		HomeServiceImpl homeServiceImpl = new HomeServiceImpl();
		provider.publish(homeServiceImpl, host, port);

		Thread.sleep(Long.MAX_VALUE);
	}
}
