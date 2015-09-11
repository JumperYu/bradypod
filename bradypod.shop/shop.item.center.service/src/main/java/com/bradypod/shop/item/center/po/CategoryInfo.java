package com.bradypod.shop.item.center.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.*;
import java.util.*;

/**
 * 分类信息
 *
 * @author zengxm
 * @date Wed Aug 26 11:47:20 CST 2015
 *
 */
public class CategoryInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id; //分类id
	private Integer depth; //层深
	private Long parentId; //父类的id号
	private Long orderNum; //数字越小排序越大
	private String name; //分类名称
	private String nickName; //别名
	private String description; //分类描述
	private Integer status; //状态，0：正常；1：删除
	private Date createTime; //创建的时间
	private Date updateTime; //最后更新时间

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public Integer getDepth() {
		return depth;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

	public Long getOrderNum() {
		return orderNum;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
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