package com.seewo.modules.api;

import com.seewo.po.Result;

public interface ReduceInventoryService {
	
	/**
	 * 扣库存前检查
	 */
	public Result<Boolean> witholdInventory(Long itemId, Integer num);
	
}
