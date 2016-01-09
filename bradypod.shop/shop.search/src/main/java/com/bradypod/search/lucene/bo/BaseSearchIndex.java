package com.bradypod.search.lucene.bo;

import java.io.Serializable;

/**
 * 基础查询对象
 *
 * @author zengxm
 * @date 2016年1月9日
 *
 */
public class BaseSearchIndex implements Serializable {

	private Integer pageNO; // 页码

	private Integer pageSize; // 页长

	private String sortField; // 排序字段

	private boolean isDescending; // 默认为false升序

	private static final int DEFAULT_PAGE_NUM = 1;
	private static final int DEFAULT_PAGE_SIZE = 10;

	public Integer getPageNO() {
		// hack if not be set
		if (pageNO == null) {
			this.pageNO = DEFAULT_PAGE_NUM;
		}
		return pageNO;
	}

	public void setPageNO(Integer pageNO) {
		this.pageNO = pageNO;
	}

	public Integer getPageSize() {
		// hack if not be set
		if (pageSize == null) {
			this.pageSize = DEFAULT_PAGE_SIZE;
		}
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public boolean isDescending() {
		return isDescending;
	}

	public void setDescending(boolean isDescending) {
		this.isDescending = isDescending;
	}

	private static final long serialVersionUID = 1L;

}
