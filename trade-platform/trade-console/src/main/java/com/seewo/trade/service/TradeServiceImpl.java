package com.seewo.trade.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.seewo.po.Item;
import com.seewo.trade.OrderVo;

@Service
public class TradeServiceImpl implements TradeService{
	Map<Long,Item> items=new HashMap<>();
	public TradeServiceImpl(){
		Item item1=new Item(1L, "商品1", 100L, 10);
		Item item2=new Item(2L, "商品2", 200L, 10);
		Item item3=new Item(3L, "商品3", 300L, 10);
		Item item4=new Item(4L, "商品4", 400L, 10);
		Item item5=new Item(5L, "商品5", 500L, 10);
		Item item6=new Item(6L, "商品6", 600L, 10);
		items.put(item1.getItemId(), item1);
		items.put(item2.getItemId(), item2);
		items.put(item3.getItemId(), item3);
		items.put(item4.getItemId(), item4);
		items.put(item5.getItemId(), item5);
		items.put(item6.getItemId(), item6);
	}
	
	@Override
	public Collection<Item> getItems() {
		return items.values();
	}

	@Override
	public Item getItemDetail(Long id) {
		return items.get(id);
	}

	@Override
	public OrderVo addOrder(Long itemId, int num) {
		OrderVo vo=new OrderVo();
		vo.setItemId(itemId);
		vo.setNum(num);
		vo.setPrice(num * items.get(itemId).getPrice().intValue());
		vo.setItemPrice(items.get(itemId).getPrice());
		vo.setMsg("平邮");
		vo.setTitle(items.get(itemId).getTitle());
		return vo;
	}

}
