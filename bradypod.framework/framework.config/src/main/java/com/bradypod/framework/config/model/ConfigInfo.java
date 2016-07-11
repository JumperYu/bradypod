package com.bradypod.framework.config.model;

import java.io.Serializable;

public class ConfigInfo implements Serializable{
	
	private long id;

	private String appId;

	private String dataId;

	private String desc;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	private static final long serialVersionUID = 1L;
}
