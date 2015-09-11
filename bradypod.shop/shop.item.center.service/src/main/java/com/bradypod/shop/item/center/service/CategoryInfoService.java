package com.bradypod.shop.item.center.service;

import com.bradypod.shop.item.center.po.CategoryInfo;

/**
 * 分类信息
 *
 * @author zengxm
 * @date Wed Aug 26 11:47:20 CST 2015
 *
 */
public interface CategoryInfoService {

	void save(CategoryInfo categroy);

	Object get(CategoryInfo categroy);

	Object findCategories(int i, int j, long l, int k);

	void getCategoryInfoBO();

}