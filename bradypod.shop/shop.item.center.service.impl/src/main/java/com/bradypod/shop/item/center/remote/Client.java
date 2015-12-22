package com.bradypod.shop.item.center.remote;

import com.bradypod.shop.item.center.service.HomeService;

public class Client {

	public static void main(String[] args) throws Exception {
		RmiServiceConsumer consumer = new RmiServiceConsumer();

		while (true) {
			HomeService homeService = consumer.lookup();
			String result = homeService.getCategory();
			System.out.println(result);
			Thread.sleep(3000);
		}
	}
}