package com.seewo.modules;

import com.seewo.po.Order;

public class ServerStarter {
	public static void main(String[] args) throws InterruptedException, InstantiationException, IllegalAccessException {
		Modules modules = new Modules();
		ItemQueryService itemQueryService = new ItemQueryService();
		
		OrderHandler orderHandler = new OrderHandler();
		orderHandler.setItemQueryService(itemQueryService);
		orderHandler.setModules(modules);
		
		Order order = orderHandler.createOrder(123L, 1);
		
		System.out.println(order);
	}
}