package com.yu.common.mapper;

import java.util.List;
import java.util.Map;

/**
 * 
 * 基础映射方法 - 还不完善 - CRUD
 *
 * @author zengxm
 * @date 2015年4月30日
 *
 */
public interface BaseMapper<E> {

	/**
	 * 保存实体
	 * 
	 * @param param
	 *            - Map类型 XML读取 Key的名称,Object的数据类型和值
	 * @param param
	 *            - 所有对象类型, XML读取Object的数据类型和值
	 * @return int - 影响的数据表的行数
	 */
	public int save(E e);

	/**
	 * 获取全部
	 * 
	 * @param E
	 *            - 实体
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
	 * 更新实体
	 * 
	 * @param E
	 *            - 实体对象
	 * @return int - 数据库影响行数
	 */
	public int update(E e);

	/**
	 * 批量更新
	 * 
	 * @param list
	 *            - List
	 * 
	 * @return int - 影响行数
	 */
	public int batchUpdate(List<E> list);
	
	/**
	 * 分页查询
	 * 
	 * @param pageSize - 叶大小
	 * @param pageNO - 页码
	 * @param params - Map参数
	 * @return - List<E>
	 */
	public List<E> listData(Map<String, Object> params);
	
	/**
	 * 统计条数
	 * 
	 * @param params - Map参数
	 * @return - 条数
	 */
	public int countData(Map<String, Object> params);
}
