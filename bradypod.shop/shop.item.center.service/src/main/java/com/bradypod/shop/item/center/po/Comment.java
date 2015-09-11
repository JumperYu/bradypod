package com.bradypod.shop.item.center.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.*;
import java.util.*;

/**
 * 商品评论
 *
 * @author zengxm<https://github.com/JumperYu/bradypod>
 * @date 2015-09-11 12:02:22
 *
 */
public class Comment implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id; //主键
	private Long userId; //创建评论的用户id
	private Long entityUserId; //创建评论的用户id
	private Long entityId; //被评论的实体id
	private Integer entityType; //被评论的实体类型
	private String entityInfo; //被评论的对象信息
	private Long starNum; //评星
	private String title; //评论标题
	private String description; //评论描述
	private String picUrl; //买家上传图片地址
	private Integer status; //图片状
	private Date createTime; //记录生成时间
	private Date updateTime; //最后更新时间

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setEntityUserId(Long entityUserId) {
		this.entityUserId = entityUserId;
	}

	public Long getEntityUserId() {
		return entityUserId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityType(Integer entityType) {
		this.entityType = entityType;
	}

	public Integer getEntityType() {
		return entityType;
	}

	public void setEntityInfo(String entityInfo) {
		this.entityInfo = entityInfo;
	}

	public String getEntityInfo() {
		return entityInfo;
	}

	public void setStarNum(Long starNum) {
		this.starNum = starNum;
	}

	public Long getStarNum() {
		return starNum;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}