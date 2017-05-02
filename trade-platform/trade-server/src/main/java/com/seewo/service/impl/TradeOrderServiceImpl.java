package com.seewo.service.impl;

import com.google.inject.Inject;
import com.seewo.service.ItemQueryService;
import com.seewo.service.TradeOrderService;

public class TradeOrderServiceImpl implements TradeOrderService{
	
	@Inject
	private ItemQueryService itemQueryService;
	
	@Override
	public void order(Long itemId, Integer num) {
		// query item
		
		// Item item = ItemModule.getModule(item);
		
		// discount
		// Order order = Discount.getModule(item);
		
		// output order
		// Order order = OrderModule.getModule(order);
	}

}
