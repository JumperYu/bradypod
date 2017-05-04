package com.seewo.modules;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.seewo.po.Item;
import com.seewo.po.Order;
import com.seewo.po.Result;

public class ItemQueryService {

	Map<Long, Item> items = new HashMap<>();

	public ItemQueryService() {
		Item item1 = new Item(1L, "iphone-9-plus", 100, 10000000,10, "1001","https://store.storeimages.cdn-apple.com/8749/as-images.apple.com/is/image/AppleInc/aos/published/images/i/ph/iphone7plus/model/iphone7plus-model-select-201703_GEO_CN?wid=464&hei=662&fmt=png-alpha&qlt=95&.v=1488499908408","iphone-9-plus新品售卖，限购两件");
		Item item2 = new Item(2L, "TECHDAY 1元秒杀", 200, 10000000,10, "1002","https://img12.360buyimg.com/n1/jfs/t2656/197/1272925455/147190/f581755a/57396c70N94edc117.jpg","TECHDAY 1元秒杀活动，限购一件，逢秒数10的倍数开抢");
		Item item3 = new Item(3L, "MAXHUB", 300, 10000000,30, "1003","https://img10.360buyimg.com/n1/jfs/t4462/75/991935874/95960/24889009/58d72ed7Ne5cacf48.jpg","MAXHUB大促销，前10名下单5折优惠，剩余订单8折优惠");
		Item item4 = new Item(4L, "帅气的衣服", 400, 10000000,20, "1004","https://img13.360buyimg.com/n1/s350x449_jfs/t4396/17/2802107527/83793/cdab1d62/58f2e748N3c8ab09f.jpg!cc_350x449.jpg","帅气的衣服，买两件7折优惠");
		Item item5 = new Item(5L, "鲜花", 500, 10000000,20, "1005","https://img14.360buyimg.com/n1/jfs/t748/168/812685537/528802/b2afec9b/54fcf805N63fb9a40.jpg","鲜花，满两件包邮，免运费");
		Item item6 = new Item(6L, "EN5", 600, 10000000,30, "1006","http://www.seewo.com/Uploads/goods/1474543035.png","EN5，重量每超过50kg，加运费100");

		for (int i = 7; i <= 30; i++) {
			Item item = new Item(Long.valueOf(i), "商品-" + i, 1000, 10000000, 30, (1000 + i) + "",
					"https://g-search3.alicdn.com/img/bao/uploaded/i4/i4/751306283/TB2DJzhoFXXXXXsXpXXXXXXXXXX_!!751306283.jpg_460x460Q90.jpg_.webp");
			items.put(item.getItemId(), item);
		}
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
		Order order = new Order();
		order.setItemId(itemId);
		order.setNum(num);
		order.setAmount(num * items.get(itemId).getPrice().intValue());
		order.setPrice(items.get(itemId).getPrice());
		order.setTitle(items.get(itemId).getTitle());
		return order;
	}

	public Result<String> reduceInventory(Long itemId, int num) {

		Result<String> result = new Result<>();

		Item item = queryItemById(itemId);

		// 如果库存数小于需要的则返回失败
		if (item.getNum() < num) {
			result.setSuccess(false);
			result.setMessage("大于可购买库存数");
			return result;
		}

		item.setNum(item.getNum() - num);

		// update
		items.put(itemId, item);

		result.setSuccess(true);

		return result;
	}

	public void reloadItems() {
		for (Item item : items.values()) {
			item.setNum(100000000);
		}
	}
}
