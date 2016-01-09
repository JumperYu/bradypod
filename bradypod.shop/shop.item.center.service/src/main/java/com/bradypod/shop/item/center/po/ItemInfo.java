/*
 * Powered By [generator-framework]
 * Web Site: http://blog.bradypod.com
 * Github: https://github.com/JumperYu
 * Since 2015 - 2015
 */

package com.bradypod.shop.item.center.po;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015-12-28
 */

public class ItemInfo implements java.io.Serializable {

	// alias
	public static final String TABLE_ALIAS = "ItemInfo";
	public static final String ALIAS_ID = "商品id";
	public static final String ALIAS_USER_ID = "用户id";
	public static final String ALIAS_ITEM_TYPE = "商品类型：1 自营商品";
	public static final String ALIAS_CTG_ID = "商品分类id";
	public static final String ALIAS_TITLE = "商品标题";
	public static final String ALIAS_DESCRIPTION = "商品描述";
	public static final String ALIAS_PRICE = "金额单位（分）";
	public static final String ALIAS_STATUS = "商品状态  1 正常";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "最后更新时间";

	// date formats
	public static final String FORMAT_CREATE_TIME = "yyyy-MM-dd HH:mm:ss";// DATE_FORMAT
	public static final String FORMAT_UPDATE_TIME = "yyyy-MM-dd HH:mm:ss";// DATE_FORMAT

	// 可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	// columns START

	private java.lang.Long id;
	@NotNull
	private java.lang.Long userId;
	@NotNull
	@Max(127)
	private Integer itemType;
	@NotNull
	private java.lang.Long ctgId;
	@NotBlank
	@Length(max = 500)
	private java.lang.String title;
	@NotBlank
	@Length(max = 500)
	private java.lang.String description;
	@NotNull
	private java.lang.Integer price;
	@NotNull
	private java.lang.Integer status;
	@NotNull
	private java.util.Date createTime;

	private java.util.Date updateTime;

	// columns END

	public ItemInfo() {
	}

	public ItemInfo(java.lang.Long id) {
		this.id = id;
	}

	public void setId(java.lang.Long value) {
		this.id = value;
	}

	public java.lang.Long getId() {
		return this.id;
	}

	public void setUserId(java.lang.Long value) {
		this.userId = value;
	}

	public java.lang.Long getUserId() {
		return this.userId;
	}

	public void setItemType(Integer value) {
		this.itemType = value;
	}

	public Integer getItemType() {
		return this.itemType;
	}

	public void setCtgId(java.lang.Long value) {
		this.ctgId = value;
	}

	public java.lang.Long getCtgId() {
		return this.ctgId;
	}

	public void setTitle(java.lang.String value) {
		this.title = value;
	}

	public java.lang.String getTitle() {
		return this.title;
	}

	public void setDescription(java.lang.String value) {
		this.description = value;
	}

	public java.lang.String getDescription() {
		return this.description;
	}

	public void setPrice(java.lang.Integer value) {
		this.price = value;
	}

	public java.lang.Integer getPrice() {
		return this.price;
	}

	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}

	public java.lang.Integer getStatus() {
		return this.status;
	}

	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}

	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}

	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("Id", getId())
				.append("UserId", getUserId()).append("ItemType", getItemType())
				.append("CtgId", getCtgId()).append("Title", getTitle())
				.append("Description", getDescription()).append("Price", getPrice())
				.append("Status", getStatus()).append("CreateTime", getCreateTime())
				.append("UpdateTime", getUpdateTime()).toString();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public boolean equals(Object obj) {
		if (obj instanceof ItemInfo == false)
			return false;
		if (this == obj)
			return true;
		ItemInfo other = (ItemInfo) obj;
		return new EqualsBuilder().append(getId(), other.getId()).isEquals();
	}

	private static final long serialVersionUID = 5454155825314635342L;
}
