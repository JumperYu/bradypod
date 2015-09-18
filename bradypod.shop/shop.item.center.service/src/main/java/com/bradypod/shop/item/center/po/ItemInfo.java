/*
 * Powered By [generator-framework]
 * Web Site: http://blog.bradypod.com
 * Github: https://github.com/JumperYu
 * Since 2015 - 2015
 */

package com.bradypod.shop.item.center.po;

import javax.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import org.apache.commons.lang.builder.*;

import com.bradypod.util.date.DateUtils;

/**
 *
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015-09-18
 */

public class ItemInfo implements java.io.Serializable {

	// alias
	public static final String TABLE_ALIAS = "ItemInfo";
	public static final String ALIAS_ID = "商品id";
	public static final String ALIAS_USER_ID = "用户id";
	public static final String ALIAS_ITEM_TYPE = "商品类型：1 自营商品";
	public static final String ALIAS_CTG_ID = "商品分类id";
	public static final String ALIAS_TITLE = "商品标题";
	public static final String ALIAS_PIC_URL_LIST = "商品多图片地址";
	public static final String ALIAS_DESCRIPTION = "商品描述";
	public static final String ALIAS_ATTRI_JSON = "商品普通属性组合串 例如 {1:10001,2:10002,3:[10003,10004,10005]}";
	public static final String ALIAS_PRICE = "金额单位（分）";
	public static final String ALIAS_STATUS = "商品状态  1 正常";
	public static final String ALIAS_APPROVAL_TIME = "最后一次审核通过时间";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "最后更新时间";

	// date formats
	public static final String FORMAT_APPROVAL_TIME = "yyyy-MM-dd HH:mm:ss";// DATE_FORMAT
	public static final String FORMAT_CREATE_TIME = "yyyy-MM-dd HH:mm:ss";// DATE_FORMAT
	public static final String FORMAT_UPDATE_TIME = "yyyy-MM-dd HH:mm:ss";// DATE_FORMAT

	// 可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	// columns START

	private java.lang.Long id;
	@NotNull
	private java.lang.Long userId;
	@Max(127)
	private Integer itemType;
	@NotNull
	private java.lang.Long ctgId;
	@NotBlank
	@Length(max = 500)
	private java.lang.String title;
	@NotBlank
	@Length(max = 500)
	private java.lang.String picUrlList;
	@NotBlank
	@Length(max = 500)
	private java.lang.String description;
	@NotBlank
	@Length(max = 1000)
	private java.lang.String attriJson;
	@NotNull
	private java.lang.Long price;
	@NotNull
	@Max(127)
	private Integer status;
	@NotNull
	private java.util.Date approvalTime;
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

	public void setPicUrlList(java.lang.String value) {
		this.picUrlList = value;
	}

	public java.lang.String getPicUrlList() {
		return this.picUrlList;
	}

	public void setDescription(java.lang.String value) {
		this.description = value;
	}

	public java.lang.String getDescription() {
		return this.description;
	}

	public void setAttriJson(java.lang.String value) {
		this.attriJson = value;
	}

	public java.lang.String getAttriJson() {
		return this.attriJson;
	}

	public void setPrice(java.lang.Long value) {
		this.price = value;
	}

	public java.lang.Long getPrice() {
		return this.price;
	}

	public void setStatus(Integer value) {
		this.status = value;
	}

	public Integer getStatus() {
		return this.status;
	}

	public String getApprovalTimeString() {
		return DateUtils.timeToString(getApprovalTime(), FORMAT_APPROVAL_TIME);
	}

	public void setApprovalTimeString(String value) {
		setApprovalTime(DateUtils.strToDate(value, FORMAT_APPROVAL_TIME));
	}

	public void setApprovalTime(java.util.Date value) {
		this.approvalTime = value;
	}

	public java.util.Date getApprovalTime() {
		return this.approvalTime;
	}

	public String getCreateTimeString() {
		return DateUtils.timeToString(getCreateTime(), FORMAT_CREATE_TIME);
	}

	public void setCreateTimeString(String value) {
		setCreateTime(DateUtils.strToDate(value, FORMAT_CREATE_TIME));
	}

	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}

	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public String getUpdateTimeString() {
		return DateUtils.timeToString(getUpdateTime(), FORMAT_UPDATE_TIME);
	}

	public void setUpdateTimeString(String value) {
		setUpdateTime(DateUtils.strToDate(value, FORMAT_UPDATE_TIME));
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
				.append("PicUrlList", getPicUrlList()).append("Description", getDescription())
				.append("AttriJson", getAttriJson()).append("Price", getPrice())
				.append("Status", getStatus()).append("ApprovalTime", getApprovalTime())
				.append("CreateTime", getCreateTime()).append("UpdateTime", getUpdateTime())
				.toString();
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
