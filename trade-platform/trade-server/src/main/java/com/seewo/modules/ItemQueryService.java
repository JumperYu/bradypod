package com.seewo.modules;

import com.seewo.po.Item;

public class ItemQueryService {
	
	
	public Item queryItemById(Long itemId) {
		return new Item(123L, "12313", 1231, 12, "1001");
	}
	
}
