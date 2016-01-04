package com.bradypod.common.service;

import java.io.Serializable;
import java.util.List;

/**
 * 基础业务组成
 *
 * @author zengxm
 * @date 2015年9月5日
 *
 */
public interface BaseMybatiService<E extends Serializable> {

	/**
	 * 保存
	 * 
	 * @param <E>
	 * 
	 * @param e
	 *            - 实体对象
	 * @return - 影响行数
	 */
	public int save(E e);

	/**
	 * 更新
	 * 
	 * @param
	 * 
	 * @param e
	 *            - 实体对象
	 * @return - 影响行数
	 */
	public int update(E e);

	/**
	 * 获取全部
	 * 
	 * @param
	 * 
	 * @return List - 返回实体数组
	 */
	public List<E> getAll(E e);

	/**
	 * 删除实体
	 * 
	 * @param
	 * 
	 * @param E
	 *            - 实体对戏
	 * 
	 * @return int - 影响的数据表的行数
	 */
	public int delete(E e);

	/**
	 * 获取实体
	 * 
	 * @param
	 * 
	 * @return E - 返回实体
	 */
	public E get(E e);

}
