package com.bradypod.common.service;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bradypod.common.mapper.BaseMapper;

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
			log.debug("插入数据失败, 期望插入1行, 结果为 {}", rows);
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

	protected static final Logger log = LoggerFactory.getLogger(BaseMybatisServiceImpl.class);

	private M mapper;

	@Autowired
	private void setMapper(M mapper) {
		this.mapper = mapper;
	}

	public M getMapper() {
		return mapper;
	}
}
