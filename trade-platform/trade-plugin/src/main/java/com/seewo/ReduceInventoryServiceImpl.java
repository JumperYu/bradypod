package com.seewo;

import com.seewo.modules.api.ReduceInventoryService;

public class ReduceInventoryServiceImpl implements ReduceInventoryService{


	@Override
	public void reduceInventory(Long itemId, Integer num) {
		System.out.println("reduce");
	}

}
