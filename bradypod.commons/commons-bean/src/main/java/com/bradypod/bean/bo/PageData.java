package com.bradypod.bean.bo;

import java.io.Serializable;
import java.util.List;

public class PageData<T> implements Serializable{

	private int pageNO;

	private int pageSize;

	private int totalPage; // 自动计算

	private int totalNum;

	private List<T> list; // 数据列表

	public PageData() {
	}

	public PageData(int pageNO, int pageSize) {
		this.pageNO = pageNO;
		this.pageSize = pageSize;
	}

	public int getPageNO() {
		return pageNO;
	}

	public void setPageNO(int pageNO) {
		this.pageNO = pageNO;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		/**
		 * 自动计算
		 */
		totalPage = (totalNum % pageSize > 0) ? (totalNum / pageSize + 1)
				: (totalNum / pageSize);
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
	
	private static final long serialVersionUID = 1L;
}
