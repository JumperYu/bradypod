package com.yu.identity.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.*;
import java.util.*;

/**
 * 业务标识
 *
 * @author zengxm
 * @date Mon Aug 24 10:41:54 CST 2015
 *
 */
public class Identity implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id; // 自增id
	private String system; // 系统
	private String subSys; // 子系统
	private String module; // 模块
	private String tableName; // 表明
	private Long identity; // 标识
	private Date createTime; // 创建时间
	private Date updateTime; // 更新时间

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getSystem() {
		return system;
	}

	public void setSubSys(String subSys) {
		this.subSys = subSys;
	}

	public String getSubSys() {
		return subSys;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getModule() {
		return module;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setIdentity(Long identity) {
		this.identity = identity;
	}

	public Long getIdentity() {
		return identity;
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