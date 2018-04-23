package com.seewo;

import com.seewo.modules.api.ReduceInventoryService;
import com.seewo.po.Result;

public class ReduceInventoryServiceImpl implements ReduceInventoryService{


	@Override
	public Result<Boolean> witholdInventory(Long itemId, Integer num) {
		System.out.println("reduce");
		return null;
	}

}
