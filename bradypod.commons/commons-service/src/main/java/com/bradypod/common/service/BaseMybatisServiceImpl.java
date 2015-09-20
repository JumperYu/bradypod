package com.bradypod.common.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bradypod.common.mapper.BaseMapper;
import com.bradypod.common.po.GenericQueryParam;
import com.bradypod.common.po.Page;
import com.bradypod.common.po.PageData;
import com.yu.util.validate.AssertUtil;

/**
 * 基础业务接口
 *
 * @author zengxm
 * @date 2015年7月8日
 *
 */
public abstract class BaseMybatisServiceImpl<M extends BaseMapper<E>, E extends Serializable>
		implements BaseMybatiService<E> {

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int save(E e) {
		int rows = getMapper().save(e);
		if (rows != 1) {
			log.debug("插入数据失败, 期望插入1行, 结果为" + rows + "," + e.toString());
		}
		return rows;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int update(E e) {
		int rows = getMapper().update(e);
		if (rows != 1) {
			log.debug("插入数据失败, 期望插入1行, 结果为" + rows + "," + e.toString());
		}
		return rows;
	}

	/**
	 * 获取全部
	 * 
	 * @return List - 返回实体数组
	 */
	@Override
	public List<E> getAll(E e) {
		return getMapper().getAll(e);
	}

	/**
	 * 删除实体
	 * 
	 * @param E
	 *            - 实体对戏
	 * 
	 * @return int - 影响的数据表的行数
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int delete(E e) {
		return getMapper().delete(e);
	}

	/**
	 * 获取实体
	 * 
	 * @return E - 返回实体
	 */
	@Override
	public E get(E e) {
		return getMapper().get(e);
	}

	/**
	 * 批量更新
	 * 
	 * @return int - 行数
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int batchUpdate(List<E> list) {
		return getMapper().batchUpdate(list);
	}

	/**
	 * 获取分页数据
	 * 
	 * @param page
	 *            - 页大小&页码
	 * @param params
	 *            - Map<String, Object> 参数
	 * @return
	 */
	@Override
	public PageData<List<E>> findPageData(Page page, Map<String, Object> params) {

		AssertUtil.assertNotNull(page, "page对象为比传参数");
		AssertUtil.assertGreaterThanZero(page.getPageSize(), "pageSize需要大于0");
		AssertUtil.assertGreaterThanZero(page.getPageNO(), "pageNO需要大于0");

		int pageSize = page.getPageSize();
		int pageNO = page.getPageNO() - 1; // 使用mysql limit实现需要减去1

		if (params == null) {
			params = new HashMap<String, Object>();
		}
		params.put("pageSize", pageSize);
		params.put("pageNO", pageNO);

		// 1.找到mapper的list位置 和 count位置
		long count = 0;// countData(params);
		List<E> result = listData(params);

		// 2.指定Page对象
		PageData<List<E>> pageResult = new PageData<List<E>>();
		pageResult.setCurrentPage(page.getPageNO());
		pageResult.setPageSize(pageSize);
		pageResult.setCount(count);
		pageResult.setTotalPage((count % pageSize > 0) ? (count / pageSize + 1)
				: (count / pageSize));
		pageResult.setData(result);
		// 3.返回List<T>
		return pageResult;
	}

	/**
	 * 分页查找数据
	 * 
	 * @param params
	 *            - 参数
	 * @return - List<E>
	 */
	public List<E> listData(Map<String, Object> params) {
		// return getMapper().listData(params);
		return null;
	}

	/**
	 * 分页依赖计数查询
	 * 
	 * @param params
	 *            - 参数
	 * @return - int
	 */
	@Override
	public long countData(GenericQueryParam params) {
		return getMapper().countData(params);
	}

	protected static final Logger log = LoggerFactory
			.getLogger(BaseMybatisServiceImpl.class);

	private M mapper;

	@Autowired
	private void setMapper(M mapper) {
		this.mapper = mapper;
	}

	public M getMapper() {
		return mapper;
	}
}
