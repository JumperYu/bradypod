package com.bradypod.shop.item.center.service.impl;

import org.springframework.stereotype.Service;

/**
 * 分类信息
 *
 * @author zengxm
 * @date Wed Aug 26 11:47:20 CST 2015
 *
 */
@Service
public class CtgInfoServiceImpl {

/*	*//**
	 * 获取所有可用分类信息
	 * 
	 * @return - List<CtgInfo>
	 *//*
	public List<CtgInfo> getAllAvailableInfo() {
		CtgInfo CtgInfo = new CtgInfo();
		CtgInfo.setStatus(CtgInfoConstants.STATUS_NORMAL);
		return getAll(CtgInfo);
	}

	*//**
	 * 查找所有父节点
	 * 
	 * @return - List<Long>
	 *//*
	private List<Long> getAllParentIdList() {
		// return getMapper().getAllParentId();
		return null;
	}

	*//**
	 * 获取类目信息业务模型
	 * 
	 * TODO : 需要完善
	 * 
	 * @return - 树形结构的JavaBean对象 @see {CtgInfo}
	 *//*
	public CtgInfo getCtgInfo() {
		List<Long> parentIdList = getAllParentIdList(); // 找到所有父节点
		List<CtgInfo> availableList = getAllAvailableInfo(); // 查找所有可用数据
		// 填装数据
		CtgInfo root = new CtgInfo();
		root.setId(CtgInfoConstants.ROOT_ID);
		root.setName("root");
		root.setDescription("NOT-MEANFUL");

		for (Long pid : parentIdList) {// <-- begin for
			for (CtgInfo CtgInfo : availableList) {// <-- begin for
				// step-1: 如果是当前节点
				if (pid.equals(CtgInfo.getParentId())) {// <-- begin if
					CtgInfo bo = new CtgInfo();
					BeanUtils.copyProperties(CtgInfo, bo);
					if (CtgInfo.getDepth() == CtgInfoConstants.DEPTH_ROOT_LEVEL) {
						// 根
						root.getChildren().add(bo);
					} else if (CtgInfo.getDepth() == CtgInfoConstants.DEPTH_SECOND_LEVEL) {
						// 二级节点
						Set<CtgInfo> children = root.getChildren();
						for (CtgInfo child : children) {
							if (pid.equals(child.getId())) {
								child.getChildren().add(bo);
							}
						}
					} else if (CtgInfo.getDepth() == CtgInfoConstants.DEPTH_SECOND_LEVEL) {
						// 三级节点
						Set<CtgInfo> children = root.getChildren();
						for (CtgInfo child : children) {
							Set<CtgInfo> children_children = child
									.getChildren();
							for (CtgInfo child_child : children_children) {
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

	*//**
	 * 分页查找查找分类信息
	 * 
	 * @param pageSize
	 * @param pageNO
	 * @param pid
	 * @param depth
	 * @return
	 *//*
	public PageData<List<CtgInfo>> findCategories(int pageSize,
			int pageNO, Long pid, Integer depth) {
		Page page = new Page(pageSize, pageNO);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pid", pid);
		params.put("depth", depth);
//		return findPageData(page, params);
		return null;
	}*/

}