package com.bradypod.shop.item.center.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.bradypod.common.po.Page;
import com.bradypod.common.po.PageData;
import com.bradypod.common.service.BaseMybatisServiceImpl;
import com.bradypod.shop.item.center.bo.CategoryInfoBO;
import com.bradypod.shop.item.center.constants.CategoryInfoConstants;
import com.bradypod.shop.item.center.po.CategoryInfo;
import com.bradypod.shop.item.center.service.CategoryInfoService;

/**
 * 分类信息
 *
 * @author zengxm
 * @date Wed Aug 26 11:47:20 CST 2015
 *
 */
@Service
public class CategoryInfoServiceImpl extends
		BaseMybatisServiceImpl<CategoryInfo, Long> implements
		CategoryInfoService {

	/**
	 * 获取所有可用分类信息
	 * 
	 * @return - List<CategoryInfo>
	 */
	public List<CategoryInfo> getAllAvailableInfo() {
		CategoryInfo categoryInfo = new CategoryInfo();
		categoryInfo.setStatus(CategoryInfoConstants.STATUS_NORMAL);
		return getAll(categoryInfo);
	}

	/**
	 * 查找所有父节点
	 * 
	 * @return - List<Long>
	 */
	private List<Long> getAllParentIdList() {
		// return getMapper().getAllParentId();
		return null;
	}

	/**
	 * 获取类目信息业务模型
	 * 
	 * TODO : 需要完善
	 * 
	 * @return - 树形结构的JavaBean对象 @see {CategoryInfoBO}
	 */
	public CategoryInfoBO getCategoryInfoBO() {
		List<Long> parentIdList = getAllParentIdList(); // 找到所有父节点
		List<CategoryInfo> availableList = getAllAvailableInfo(); // 查找所有可用数据
		// 填装数据
		CategoryInfoBO root = new CategoryInfoBO();
		root.setId(CategoryInfoConstants.ROOT_ID);
		root.setName("root");
		root.setDescription("NOT-MEANFUL");

		for (Long pid : parentIdList) {// <-- begin for
			for (CategoryInfo categoryInfo : availableList) {// <-- begin for
				// step-1: 如果是当前节点
				if (pid.equals(categoryInfo.getParentId())) {// <-- begin if
					CategoryInfoBO bo = new CategoryInfoBO();
					BeanUtils.copyProperties(categoryInfo, bo);
					if (categoryInfo.getDepth() == CategoryInfoConstants.DEPTH_ROOT_LEVEL) {
						// 根
						root.getChildren().add(bo);
					} else if (categoryInfo.getDepth() == CategoryInfoConstants.DEPTH_SECOND_LEVEL) {
						// 二级节点
						Set<CategoryInfoBO> children = root.getChildren();
						for (CategoryInfoBO child : children) {
							if (pid.equals(child.getId())) {
								child.getChildren().add(bo);
							}
						}
					} else if (categoryInfo.getDepth() == CategoryInfoConstants.DEPTH_SECOND_LEVEL) {
						// 三级节点
						Set<CategoryInfoBO> children = root.getChildren();
						for (CategoryInfoBO child : children) {
							Set<CategoryInfoBO> children_children = child
									.getChildren();
							for (CategoryInfoBO child_child : children_children) {
								if (pid.equals(child.getId())) {
									child_child.getChildren().add(bo);
								}
							}
						}
					}
				}// --> end if pid equals
			}// --> end for availableList
		}// --> end for parentIdList

		return root;
	}

	/**
	 * 分页查找查找分类信息
	 * 
	 * @param pageSize
	 * @param pageNO
	 * @param pid
	 * @param depth
	 * @return
	 */
	public PageData<List<CategoryInfo>> findCategories(int pageSize,
			int pageNO, Long pid, Integer depth) {
		Page page = new Page(pageSize, pageNO);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pid", pid);
		params.put("depth", depth);
		return findPageData(page, params);
	}

}