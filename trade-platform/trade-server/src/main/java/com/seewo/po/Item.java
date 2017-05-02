package com.seewo.po;

public class Item {

	private Long itemId;

	private String title;

	private Long price;

	private Integer num;

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Item(Long itemId, String title, Long price, Integer num) {
		super();
		this.itemId = itemId;
		this.title = title;
		this.price = price;
		this.num = num;
	}

}
