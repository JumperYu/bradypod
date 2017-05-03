package com.seewo.po;

public class Item {

	private Long itemId;

	private String title;

	private Integer price;

	private Integer num;
	
	private String feature;
	
	private Integer height;

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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	public String getFeature() {
		return feature;
	}
	
	public void setFeature(String feature) {
		this.feature = feature;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Item(Long itemId, String title, Integer price, Integer num, String feature) {
		super();
		this.itemId = itemId;
		this.title = title;
		this.price = price;
		this.num = num;
		this.feature = feature;
	}

}
