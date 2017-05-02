package com.seewo.service.impl;

import java.util.List;

import com.seewo.po.Item;
import com.seewo.service.ItemQueryService;

public class ItemQueryServiceImpl implements ItemQueryService{

	@Override
	public Item queryItemById(Long itemId) {
		return new Item();
	}
	
	@Override
	public List<Item> listItems() {
		return null;
	}
}
