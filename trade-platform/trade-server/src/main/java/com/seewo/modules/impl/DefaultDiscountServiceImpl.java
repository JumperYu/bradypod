package com.seewo.modules.impl;

import com.seewo.modules.api.DiscountService;
import com.seewo.po.Order;

public class DefaultDiscountServiceImpl implements DiscountService {

	@Override
	public Order discount(Order order) {
		System.out.println("default discount");
		return order;
	}

}
