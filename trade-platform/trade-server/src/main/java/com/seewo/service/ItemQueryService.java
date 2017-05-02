package com.seewo.service;

import java.util.List;

import com.seewo.po.Item;

public interface ItemQueryService {

	public Item queryItemById(Long itemId);
	
	
	public List<Item> listItems();
}
