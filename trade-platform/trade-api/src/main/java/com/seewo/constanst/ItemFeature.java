package com.seewo.constanst;

public enum ItemFeature {
	
	SEEWO_BAIBAN("seewo白板"), IPHONE_9_PLUS("iphone 9 plus");
	
	private String desc;
	
	private ItemFeature(String desc) {
		this.desc = desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public String getDesc() {
		return desc;
	}
}
