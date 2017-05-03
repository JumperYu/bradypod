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
		Item item1 = new Item(1L, "iphone-9-plus", 100, 10, "1001","https://store.storeimages.cdn-apple.com/8749/as-images.apple.com/is/image/AppleInc/aos/published/images/i/ph/iphone7plus/model/iphone7plus-model-select-201703_GEO_CN?wid=464&hei=662&fmt=png-alpha&qlt=95&.v=1488499908408");
		Item item2 = new Item(2L, "TECHDAY 1元秒杀", 200, 10, "1002","http://cms-bucket.nosdn.127.net/c71101528b094e47b0e3a9a021e9e40020160909210731.png?imageView&thumbnail=550x0");
		Item item3 = new Item(3L, "MAXHUB", 300, 10, "1003","http://www.maxhub.vip/static/images/items/productlist/img_product_c-26d0d104ba.png");
		Item item4 = new Item(4L, "帅气的衣服", 400, 10, "1004","https://g-search3.alicdn.com/img/bao/uploaded/i4/i4/751306283/TB2DJzhoFXXXXXsXpXXXXXXXXXX_!!751306283.jpg_460x460Q90.jpg_.webp");
		Item item5 = new Item(5L, "鲜花", 500, 10, "1005","https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2565682480,1049014799&fm=23&gp=0.jpg");
		Item item6 = new Item(6L, "EN5", 600, 10, "1006","http://www.seewo.com/Uploads/goods/1474543035.png");
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
}
