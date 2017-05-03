package com.seewo.modules;

import com.seewo.po.Order;
import com.seewo.po.Result;

public class ServerStarter {
	public static void main(String[] args) throws InterruptedException, InstantiationException, IllegalAccessException {
		Modules modules = new Modules();
		ItemQueryService itemQueryService = new ItemQueryService();
		
		OrderHandler orderHandler = new OrderHandler();
		orderHandler.setItemQueryService(itemQueryService);
		orderHandler.setModules(modules);
		
		Result<Order> result = orderHandler.createOrder(123L, 1);
		
		System.out.println(result);
	}
}