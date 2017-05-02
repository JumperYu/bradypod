package com.seewo.modules;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.seewo.po.Item;
import com.seewo.po.Order;

public class ItemQueryService {
	
	Map<Long,Item> items=new HashMap<>();
	public ItemQueryService(){
		Item item1=new Item(1L, "商品1", 100, 10,"1001");
		Item item2=new Item(2L, "商品2", 200, 10,"1002");
		Item item3=new Item(3L, "商品3", 300, 10,"1003");
		Item item4=new Item(4L, "商品4", 400, 10,"1004");
		Item item5=new Item(5L, "商品5", 500, 10,"1005");
		Item item6=new Item(6L, "商品6", 600, 10,"1006");
		items.put(item1.getItemId(), item1);
		items.put(item2.getItemId(), item2);
		items.put(item3.getItemId(), item3);
		items.put(item4.getItemId(), item4);
		items.put(item5.getItemId(), item5);
		items.put(item6.getItemId(), item6);
	}
	
	public Collection<Item> getItems() {
		return items.values();
	}

	public Item queryItemById(Long id) {
		return items.get(id);
	}

	public Order addOrder(Long itemId, int num) {
		Order order=new Order();
		order.setItemId(itemId);
		order.setNum(num);
		order.setAmount(num * items.get(itemId).getPrice().intValue());
		order.setPrice(items.get(itemId).getPrice());
		order.setTitle(items.get(itemId).getTitle());
		return order;
	}
	
}
