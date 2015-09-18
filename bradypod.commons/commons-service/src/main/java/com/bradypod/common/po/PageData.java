package com.bradypod.common.po;

import java.io.Serializable;

public class PageData<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 每页记录数
	 */
	private int pageSize;

	/**
	 * 当前页
	 */
	private int currentPage;

	/**
	 * 总页数
	 */
	private long totalPage;

	/**
	 * 总记录数
	 */
	private long count;

	private T data;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
