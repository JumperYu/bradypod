package com.bradypod.shop.item.center.mapper;

import java.util.List;

import com.bradypod.common.mapper.BaseMapper;
import com.bradypod.shop.item.center.po.CategoryInfo;

/**
 * 分类信息
 *
 * @author zengxm
 * @date Wed Aug 26 11:47:20 CST 2015
 *
 */
public interface CategoryInfoMapper extends BaseMapper<CategoryInfo> {

	/**
	 * 获取所有父节点列表
	 * 
	 * @return - List<Long>
	 */
	public List<Long> getAllParentId();

}