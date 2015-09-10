package com.yu.common.po;

/**
 * 分页数据模型
 *
 * @author zengxm<github.com/JumperYu>
 *
 *         2015年8月28日 下午6:11:58
 */
public class Page {

	private static final int DEFAULT_PAGE_SIZE = 15; // 默认15页

	private int pageSize = DEFAULT_PAGE_SIZE; // 页大小

	private int pageNO; // 页码

	public Page() {
	}
	
	/**
	 * 指定页码默认页大小
	 * 
	 * @param pageNO
	 */
	public Page(int pageNO) {
		this.pageNO = pageNO;
	}

	/**
	 * 构造
	 * 
	 * @param pageSize
	 * @param pageNO
	 */
	public Page(int pageSize, int pageNO) {
		this.pageSize = pageSize;
		this.pageNO = pageNO;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNO() {
		return pageNO;
	}

	public void setPageNO(int pageNO) {
		this.pageNO = pageNO;
	}

}
