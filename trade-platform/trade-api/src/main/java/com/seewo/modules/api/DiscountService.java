package com.seewo.modules.api;

import com.seewo.po.Order;
import com.seewo.po.Result;

public interface DiscountService {
	
	/**
	 * 优惠
	 */
	public Result<Order> discount(Order order);
	
}