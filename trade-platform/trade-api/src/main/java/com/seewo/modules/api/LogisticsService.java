package com.seewo.modules.api;

import com.seewo.po.Order;

public interface LogisticsService {
	
	/**
	 * 物流费用
	 */
	public Order logisticsCost(Order order);
	
}
