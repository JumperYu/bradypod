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
 * @date 2015-09-20
 */

public class CtgItem implements java.io.Serializable{
	
	//alias
	public static final String TABLE_ALIAS = "CtgItem";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_CTG_ID = "类目";
	public static final String ALIAS_ITEM_ID = "商品";
	
	//date formats
	
	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	
	private java.lang.Long id;
	@NotNull 
	private java.lang.Long ctgId;
	@NotNull 
	private java.lang.Long itemId;
	//columns END

	public CtgItem(){
	}

	public CtgItem(
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
	public void setCtgId(java.lang.Long value) {
		this.ctgId = value;
	}
	
	public java.lang.Long getCtgId() {
		return this.ctgId;
	}
	public void setItemId(java.lang.Long value) {
		this.itemId = value;
	}
	
	public java.lang.Long getItemId() {
		return this.itemId;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("CtgId",getCtgId())
			.append("ItemId",getItemId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof CtgItem == false) return false;
		if(this == obj) return true;
		CtgItem other = (CtgItem)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
	
	private static final long serialVersionUID = 5454155825314635342L;
}

