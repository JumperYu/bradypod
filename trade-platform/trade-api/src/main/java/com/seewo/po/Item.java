package com.seewo.po;

public class Item {

	private Long itemId; // 标识

	private String title; // 标题

	private String desc; // 描述

	private Integer price; // 单价

	private Integer num; // 库存

	private String feature; // 特征

	private Integer weight; // 重量

	private String url;

	private String witholding; // 预留库存
	
	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
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

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setWitholding(String witholding) {
		this.witholding = witholding;
	}

	public String getWitholding() {
		return witholding;
	}

	public Item(Long itemId, String title, Integer price, Integer num,Integer weight, String feature,String url) {
		super();
		this.itemId = itemId;
		this.title = title;
		this.price = price;
		this.num = num;
		this.feature = feature;
		this.url=url;
		this.weight=weight;
	}
	public Item(Long itemId, String title, Integer price, Integer num,Integer weight, String feature,String url,String descript) {
		super();
		this.itemId = itemId;
		this.title = title;
		this.price = price;
		this.num = num;
		this.feature = feature;
		this.url=url;
		this.weight=weight;
		this.desc=descript;
	}
}
