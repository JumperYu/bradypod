package com.seewo.modules.api;

import com.seewo.po.Result;

public interface ReduceInventoryService {
	
	public Result<Boolean> reduceInventory(Long itemId, Integer num);
	
}
