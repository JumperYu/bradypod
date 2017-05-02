package com.seewo.trade.service;

import java.util.Collection;

import com.seewo.po.Item;
import com.seewo.trade.OrderVo;

public interface TradeService {
	Collection<Item> getItems();

	Item getItemDetail(Long id);
	
	OrderVo addOrder(Long itemId,int num);
}
