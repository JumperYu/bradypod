package com.seewo.modules.api;

import com.seewo.po.Order;

public interface OrderService {
	
	public Order queryOrderById(Long orderId);
	
}
