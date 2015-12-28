package com.bradypod.search.lucene;

/**
 * 商品索引
 *
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2015年12月28日 下午5:15:29
 */
public class ItemInfoIndex {

	private java.lang.Long id;
	private java.lang.Long userId;
	private java.lang.Integer itemType;
	private java.lang.Long ctgId;
	private java.lang.String title;
	private java.lang.String description;
	private java.lang.Integer price;
	private java.lang.Integer status;
	private java.util.Date createTime;
	private java.util.Date updateTime;

	public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Long getUserId() {
		return userId;
	}

	public void setUserId(java.lang.Long userId) {
		this.userId = userId;
	}

	public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public java.lang.Long getCtgId() {
		return ctgId;
	}

	public void setCtgId(java.lang.Long ctgId) {
		this.ctgId = ctgId;
	}

	public java.lang.String getTitle() {
		return title;
	}

	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	public java.lang.String getDescription() {
		return description;
	}

	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	public java.lang.Integer getPrice() {
		return price;
	}

	public void setPrice(java.lang.Integer price) {
		this.price = price;
	}

	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

}
