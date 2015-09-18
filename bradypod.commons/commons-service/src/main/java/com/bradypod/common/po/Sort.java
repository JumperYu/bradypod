package com.bradypod.common.po;

/**
 * 排序
 *
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015年9月18日 下午4:43:03
 */
public class Sort {

	/**
	 * 排序类型
	 */
	private String column;

	/**
	 * 排序类型
	 */
	private Order order;

	public Sort(String column) {
		this(column, Order.ASC);
	}

	public Sort(String column, Order order) {
		this.column = column;
		this.order = order;
	}

	public String getColumn() {
		return column;
	}

	public Order getOrder() {
		return order;
	}

	/**
	 * 排序类型枚举
	 */
	public enum Order {
		ASC, DESC
	}

}
