package com.bradypod.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bradypod.common.po.GenericQueryParam;
import com.bradypod.common.po.Page;
import com.bradypod.common.po.PageData;

/**
 * 基础业务组成
 *
 * @author zengxm
 * @date 2015年9月5日
 *
 */
public interface BaseMybatiService<E, T> {

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

	/**
	 * 批量更新
	 * 
	 * @param
	 * 
	 * @return int - 行数
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public int batchUpdate(List<E> list);

	/**
	 * 获取分页数据
	 * 
	 * @param <E>
	 * 
	 * @param page
	 *            - 页大小&页码
	 * @param params
	 *            - Map<String, Object> 参数
	 * @return
	 */
	public PageData<List<E>> findPageData(Page page, Map<String, Object> params);

	/**
	 * 分页查找数据
	 * 
	 * @param <E>
	 * 
	 * @param params
	 *            - 参数
	 * @return - List<E>
	 */
	public List<E> listData(Map<String, Object> params);

	/**
	 * 分页依赖计数查询
	 * 
	 * @param params
	 *            - 参数
	 * @return - long
	 */
	public long countData(GenericQueryParam params);

}
