package com.seewo.modules.impl;

import com.seewo.modules.api.ReduceInventoryService;
import com.seewo.po.Result;

public class DefaultReduceInventoryServiceImpl implements ReduceInventoryService{

	@Override
	public Result<Boolean> reduceInventory(Long itemId, Integer num) {
		System.out.println("default reduce");
		Result<Boolean> result = new Result<Boolean>();
		result.setData(true);
		return result;
	}

}
