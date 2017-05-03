package com.seewo.modules;

import java.util.HashMap;
import java.util.Map;

import com.seewo.modules.api.DiscountService;
import com.seewo.modules.api.LogisticsService;
import com.seewo.modules.api.ReduceInventoryService;
import com.seewo.po.Item;
import com.seewo.po.Order;
import com.seewo.po.Result;

public class OrderHandler {

	private Map<Long, Order> orderMap = new HashMap<>();

	private ItemQueryService itemQueryService;

	private Modules modules;

	public Result<Order> createOrder(Long itemId, Integer num) {

		// 查询商品
		Item item = itemQueryService.queryItemById(itemId);

		String feature = item.getFeature();

		// 预扣库存
		ReduceInventoryService reduceInventoryService = modules.getItemManagerModule().getInstance(feature,
				ReduceInventoryService.class);
		Result<Boolean> reduceInvResult = reduceInventoryService.witholdInventory(itemId, num);

		Result<Order> orderResult = new Result<>();

		Order order = new Order();

		if (reduceInvResult.isSuccess()) {
			// 创建订单
			order.setOrderId(System.currentTimeMillis());
			order.setItemId(itemId);
			order.setNum(num);
			order.setAmount(item.getPrice() * num);
			order.setPrice(item.getPrice());
			order.setTitle(item.getTitle());
			order.setUrl(item.getUrl());

			// 计算优惠
			DiscountService discountService = modules.getItemManagerModule().getInstance(feature,
					DiscountService.class);
			
			Result<Order> discountResult = discountService.discount(order);
			if (discountResult.isSuccess()) {
				order = discountResult.getData();
			}

			// 计算物流
			LogisticsService logisticsService = modules.getItemManagerModule().getInstance(feature,
					LogisticsService.class);
			order = logisticsService.logisticsCost(order);

			// 合计
			// ...
			orderResult.setSuccess(true);
			orderResult.setData(order);

			orderMap.put(order.getOrderId(), order);
		} else {
			orderResult.setMessage(reduceInvResult.getMessage());
			orderResult.setSuccess(false);
		}

		return orderResult;
	}

	public Result<String> confirmOrder(Long orderId) {
		Order order = orderMap.get(orderId);
		// 扣库存
		return itemQueryService.reduceInventory(order.getItemId(), order.getNum());
	}

	public void setModules(Modules modules) {
		this.modules = modules;
	}

	public void setItemQueryService(ItemQueryService itemQueryService) {
		this.itemQueryService = itemQueryService;
	}

	public Order queryById(Long id) {
		return orderMap.get(id);
	}
}
