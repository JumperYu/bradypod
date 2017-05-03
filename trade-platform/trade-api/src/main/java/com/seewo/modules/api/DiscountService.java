package com.seewo.modules.api;

import com.seewo.po.Order;
import com.seewo.po.Result;

public interface DiscountService {
		
	public Result<Order> discount(Order order);
	
}