package com.seewo.po;

public class Order {

	private Long itemId;

	private Integer num;

	private Integer price;
	
	private Integer logisticsPrice;

	private String title;

	private Integer amount;
	

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
}
