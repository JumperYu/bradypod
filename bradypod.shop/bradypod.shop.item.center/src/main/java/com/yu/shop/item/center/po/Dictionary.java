package com.yu.shop.item.center.po;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * ϵͳ�ֵ����
 *
 * @author zengxm
 * @date Mon Sep 07 10:02:07 CST 2015
 *
 */
public class Dictionary implements Serializable {

	private static final long serialVersionUID = 1L;
	@NotNull(message = "can't be null")
	private Long id; // 10000000
	@NotNull
	private Long userId; //
	private String name; //
	private String description; //
	private Integer type;//
	private Integer status; //
	private Date createTime; //
	private Date updateTime; //

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

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return type;
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
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}