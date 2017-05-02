package com.seewo;

import com.seewo.modules.api.ItemQueryService;
import com.seewo.po.Item;

public class ItemQueryServiceImpl implements ItemQueryService{

	@Override
	public Item queryItem(Long itemId) {
		System.out.println("query item2");
		return null;
	}

}
