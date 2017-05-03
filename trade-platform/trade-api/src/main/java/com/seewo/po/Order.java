package com.seewo.po;

public class Order {
	private Long orderId;
	
	private Long itemId;

	private Integer num;

	private Integer price;
	
	private Integer logisticsPrice=0;

	private String title;

	private Integer amount;
	
	private Integer height;

	
	
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getAmount() {
		return amount;
	}

	public Integer getLogisticsPrice() {
		return logisticsPrice;
	}

	public void setLogisticsPrice(Integer logisticsPrice) {
		this.logisticsPrice = logisticsPrice;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}
}
