package com.seewo.modules.impl;

import com.seewo.modules.api.DiscountService;
import com.seewo.po.Order;
import com.seewo.po.Result;

public class DefaultDiscountServiceImpl implements DiscountService {

	@Override
	public Result<Order> discount(Order order) {
		System.out.println("default discount");
		
		Result<Order> result = new Result<Order>();
		result.setData(order);
		result.setSuccess(true);
		
		return result;
	}

}
