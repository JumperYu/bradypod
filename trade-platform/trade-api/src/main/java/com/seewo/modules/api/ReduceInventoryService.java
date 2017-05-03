package com.seewo.modules.api;

import com.seewo.po.Result;

public interface ReduceInventoryService {
	
	public Result<Boolean> witholdInventory(Long itemId, Integer num);
	
}
