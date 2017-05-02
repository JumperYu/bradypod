package com.seewo.modules;

import com.seewo.modules.api.ReduceInventoryService;
import com.seewo.po.Item;
import com.seewo.po.Order;

public class OrderHandler {
	
	private ItemQueryService itemQueryService;
	
	private Modules modules;
	
	public Order createOrder(Long itemId, Integer num) {
		
		Item item = itemQueryService.queryItemById(itemId);
		
		String feature = item.getFeature();
		
		ReduceInventoryService reduceInventoryService = modules.getItemManagerModule().getInstance(feature, ReduceInventoryService.class);
		reduceInventoryService.reduceInventory(itemId, num);
		
		Order order = new Order();
		order.setItemId(itemId);
		order.setNum(num);
		order.setPrice(item.getPrice() * num);
		order.setTitle(item.getTitle());
		
		return order;
	}
	
	public void setModules(Modules modules) {
		this.modules = modules;
	}
	
}
