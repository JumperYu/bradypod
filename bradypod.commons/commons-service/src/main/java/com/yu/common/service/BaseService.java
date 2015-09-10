package com.yu.common.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yu.common.mapper.BaseMapper;
import com.yu.common.po.Page;
import com.yu.common.po.PageData;

/**
 * 基础业务组成
 *
 * @author zengxm
 * @date 2015年9月5日
 *
 */
public interface BaseService<E extends Serializable, T extends BaseMapper<E>> {
	
	/**
	 * 获取SQL映射类
	 * 
	 * @return
	 */
	public T getMapper();

	/**
	 * 保存
	 * 
	 * @param e
	 *            - 实体对象
	 * @return - 影响行数
	 */
	public int save(E e);

	/**
	 * 更新
	 * 
	 * @param e
	 *            - 实体对象
	 * @return - 影响行数
	 */
	public int update(E e);

	/**
	 * 获取全部
	 * 
	 * @return List - 返回实体数组
	 */
	public List<E> getAll(E e);

	/**
	 * 删除实体
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
	 * @return E - 返回实体
	 */
	public E get(E e);

	/**
	 * 批量更新
	 * 
	 * @return int - 行数
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public int batchUpdate(List<E> list);

	/**
	 * 获取分页数据
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
	 * @return - int
	 */
	public int countData(Map<String, Object> params);

}
