package com.seewo.modules.impl;

import com.seewo.modules.api.LogisticsService;
import com.seewo.po.Order;

public class DefaultLogisticsServiceImpl implements LogisticsService{


	@Override
	public Order logisticsCost(Order order) {
		System.out.println("default logistics");
		return order;
	}

}
