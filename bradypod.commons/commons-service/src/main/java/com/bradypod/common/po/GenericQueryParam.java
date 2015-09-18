package com.bradypod.common.po;

import java.util.LinkedHashMap;

/**
 * Mybatis基本查询参数
 *
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015年9月18日 下午4:46:20
 */
public class GenericQueryParam extends LinkedHashMap<String, Object> {

	/**
	 * 默认大小
	 */
	public final static int PAGE_SIZE = 15;

	/**
	 * 默认起始页
	 */
	public final static int PAGE_NO = 1;

	/**
	 * 最大单页记录数
	 */
	public final static int MAX_PAGE_SIZE = 100;

	/**
	 * 页码key
	 */
	private final static String PAGENO_KEY = "pageNO";

	/**
	 * 单页大小key
	 */
	private final static String PAGESIZE_KEY = "pageSize";

	/**
	 * 排序key
	 */
	private final static String SORT_KEY = "sort";

	public GenericQueryParam() {
	}

	/* 初始化 */
	public GenericQueryParam(Integer pageNO, Integer pageSize) {
		setPageSize(pageSize);
		setPageNO(pageNO);
	}

	/* get/set */
	public Integer getPage() {
		return (Integer) get(PAGENO_KEY);
	}

	public Integer getPageSize() {
		return (Integer) get(PAGESIZE_KEY);
	}

	public String getSortKey() {
		return (String) get(SORT_KEY);
	}

	public void setPageNO(Integer pageNO) {
		put(PAGENO_KEY, pageNO);
	}

	public void setPageSize(Integer pageSize) {
		put(PAGESIZE_KEY, pageSize);
	}

	public void setSortKey(String sortKey) {
		put(SORT_KEY, sortKey);
	}

	private static final long serialVersionUID = 1L;
}
