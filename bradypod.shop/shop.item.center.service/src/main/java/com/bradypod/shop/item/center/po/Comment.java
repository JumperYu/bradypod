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
 * @date 2015-09-19
 */

public class Comment implements java.io.Serializable{
	
	//alias
	public static final String TABLE_ALIAS = "Comment";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_USER_ID = "创建评论的用户id";
	public static final String ALIAS_ENTITY_USER_ID = "创建评论的用户id";
	public static final String ALIAS_ENTITY_ID = "被评论的实体id";
	public static final String ALIAS_ENTITY_TYPE = "被评论的实体类型";
	public static final String ALIAS_ENTITY_INFO = "被评论的对象信息";
	public static final String ALIAS_STAR_NUM = "评星数 1-5";
	public static final String ALIAS_TITLE = "评论标题";
	public static final String ALIAS_DESCRIPTION = "评论描述";
	public static final String ALIAS_PIC_URL = "买家上传图片地址";
	public static final String ALIAS_STATUS = "图片状态 1: 正常; 2: 删除";
	public static final String ALIAS_CREATE_TIME = "记录生成时间";
	public static final String ALIAS_UPDATE_TIME = "最后更新时间";
	
	//date formats
	public static final String FORMAT_CREATE_TIME = "yyyy-MM-dd HH:mm:ss";//DATE_FORMAT
	public static final String FORMAT_UPDATE_TIME = "yyyy-MM-dd HH:mm:ss";//DATE_FORMAT
	
	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	
	private java.lang.Long id;
	@NotNull 
	private java.lang.Long userId;
	@NotNull 
	private java.lang.Long entityUserId;
	@NotNull 
	private java.lang.Long entityId;
	@Max(127)
	private Integer entityType;
	@NotBlank @Length(max=300)
	private java.lang.String entityInfo;
	@NotNull 
	private java.lang.Integer starNum;
	@Length(max=50)
	private java.lang.String title;
	@Length(max=250)
	private java.lang.String description;
	@Length(max=100)
	private java.lang.String picUrl;
	@Max(127)
	private Integer status;
	@NotNull 
	private java.util.Date createTime;
	
	private java.util.Date updateTime;
	//columns END

	public Comment(){
	}

	public Comment(
		java.lang.Long id
	){
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
	public void setEntityUserId(java.lang.Long value) {
		this.entityUserId = value;
	}
	
	public java.lang.Long getEntityUserId() {
		return this.entityUserId;
	}
	public void setEntityId(java.lang.Long value) {
		this.entityId = value;
	}
	
	public java.lang.Long getEntityId() {
		return this.entityId;
	}
	public void setEntityType(Integer value) {
		this.entityType = value;
	}
	
	public Integer getEntityType() {
		return this.entityType;
	}
	public void setEntityInfo(java.lang.String value) {
		this.entityInfo = value;
	}
	
	public java.lang.String getEntityInfo() {
		return this.entityInfo;
	}
	public void setStarNum(java.lang.Integer value) {
		this.starNum = value;
	}
	
	public java.lang.Integer getStarNum() {
		return this.starNum;
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
	public void setPicUrl(java.lang.String value) {
		this.picUrl = value;
	}
	
	public java.lang.String getPicUrl() {
		return this.picUrl;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
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
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("UserId",getUserId())
			.append("EntityUserId",getEntityUserId())
			.append("EntityId",getEntityId())
			.append("EntityType",getEntityType())
			.append("EntityInfo",getEntityInfo())
			.append("StarNum",getStarNum())
			.append("Title",getTitle())
			.append("Description",getDescription())
			.append("PicUrl",getPicUrl())
			.append("Status",getStatus())
			.append("CreateTime",getCreateTime())
			.append("UpdateTime",getUpdateTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Comment == false) return false;
		if(this == obj) return true;
		Comment other = (Comment)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
	
	private static final long serialVersionUID = 5454155825314635342L;
}

