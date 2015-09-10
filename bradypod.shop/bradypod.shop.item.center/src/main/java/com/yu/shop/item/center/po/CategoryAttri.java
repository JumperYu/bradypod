package com.yu.shop.item.center.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.*;
import java.util.*;

/**
 * 分类属性
 *
 * @author zengxm
 * @date Wed Aug 26 11:47:20 CST 2015
 *
 */
public class CategoryAttri implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id; //商品分类属性id
	private Long ctgId; //商品分类id
	private Long attriNameId; //属性id，对应dictionary表的id
	private boolean isForce; //是否预设值，0：不是；1：是。如果是则该分类下面所有商品都自动设置了该属性和属性值
	private boolean isNessary; //是否必填，0：否；1：是
	private boolean isMulti; //是否多选择，0：否；1：是
	private Long orderNum; //排序号；越小越前
	private Integer type; //
	private String attriValueList; //枚举属性值列表
	private Integer status; //状态；0：正常；1：删除
	private Date createTime; //创建时间
	private Date updateTime; //最后更新时间

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setCtgId(Long ctgId) {
		this.ctgId = ctgId;
	}

	public Long getCtgId() {
		return ctgId;
	}

	public void setAttriNameId(Long attriNameId) {
		this.attriNameId = attriNameId;
	}

	public Long getAttriNameId() {
		return attriNameId;
	}

	public void setIsForce(boolean isForce) {
		this.isForce = isForce;
	}

	public boolean getIsForce() {
		return isForce;
	}

	public void setIsNessary(boolean isNessary) {
		this.isNessary = isNessary;
	}

	public boolean getIsNessary() {
		return isNessary;
	}

	public void setIsMulti(boolean isMulti) {
		this.isMulti = isMulti;
	}

	public boolean getIsMulti() {
		return isMulti;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

	public Long getOrderNum() {
		return orderNum;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return type;
	}

	public void setAttriValueList(String attriValueList) {
		this.attriValueList = attriValueList;
	}

	public String getAttriValueList() {
		return attriValueList;
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