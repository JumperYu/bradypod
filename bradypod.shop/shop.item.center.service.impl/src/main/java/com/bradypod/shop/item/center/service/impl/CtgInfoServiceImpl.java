/*
 * Powered By [generator-framework]
 * Web Site: http://blog.bradypod.com
 * Github: https://github.com/JumperYu
 * Since 2015 - 2015
 */

package com.bradypod.shop.item.center.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bradypod.bean.bo.PageData;
import com.bradypod.common.service.BaseMybatisServiceImpl;
import com.bradypod.shop.item.center.constants.CtgInfoConstants;
import com.bradypod.shop.item.center.mapper.CtgInfoMapper;
import com.bradypod.shop.item.center.po.CtgInfo;
import com.bradypod.shop.item.center.po.CtgItem;
import com.bradypod.shop.item.center.po.ItemInfo;
import com.bradypod.shop.item.center.service.CtgInfoService;
import com.bradypod.shop.item.center.service.CtgItemService;
import com.bradypod.shop.item.center.service.ItemInfoService;

@Service("ctgInfoService")
@Transactional(propagation = Propagation.SUPPORTS)
public class CtgInfoServiceImpl extends
		BaseMybatisServiceImpl<CtgInfoMapper, CtgInfo> implements
		CtgInfoService {

	/**
	 * 查找所有父节点
	 * 
	 * @return - List<Long>
	 */
	private List<Long> getAllParentIdList() {
		return getMapper().getAllParentId();
	}

	/**
	 * 获取类目信息业务模型
	 * 
	 * TODO : 需要完善
	 * 
	 * @return - 树形结构的JavaBean对象 @see {CtgInfo}
	 */
	@Override
	public CtgInfo getCtgInfoTree() {
		List<Long> parentIdList = getAllParentIdList(); // 找到所有父节点
		List<CtgInfo> availableList = getAll(new CtgInfo()); // 查找所有可用数据
		// 填装数据
		CtgInfo root = new CtgInfo();
		root.setId(CtgInfoConstants.ROOT_ID);
		root.setName("root");
		root.setDescription("NOT-MEANFUL");

		for (Long pid : parentIdList) {// <-- begin for
			for (CtgInfo ctgInfo : availableList) {// <-- begin for
				// step-1: 如果是当前节点
				if (pid.equals(ctgInfo.getParentId())) {// <-- begin if
					CtgInfo ctg = new CtgInfo();
					if (ctgInfo.getDepth() == CtgInfoConstants.DEPTH_ROOT_LEVEL) {
						// 根
						root.getChildren().add(ctgInfo);
					} else if (ctgInfo.getDepth() == CtgInfoConstants.DEPTH_SECOND_LEVEL) {
						// 二级节点
						Set<CtgInfo> children = root.getChildren();
						for (CtgInfo child : children) {
							if (pid.equals(child.getId())) {
								child.getChildren().add(ctgInfo);
							}
						}
					} else if (ctgInfo.getDepth() == CtgInfoConstants.DEPTH_THIRD__LEVEL) {
						// 三级节点
						Set<CtgInfo> children = root.getChildren();
						for (CtgInfo child : children) {
							Set<CtgInfo> children_children = child
									.getChildren();
							for (CtgInfo child_child : children_children) {
								if (pid.equals(child.getId())) {
									child_child.getChildren().add(ctg);
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
	public PageData<CtgInfo> findCategories(int pageNO, int pageSize,
			Long pid, Integer depth) {
/*		GenericQueryParam params = new GenericQueryParam(pageSize, pageNO);
		params.put("pid", pid);
		params.put("depth", depth);

		List<CtgInfo> list = getMapper().listData(params);
		long count = getMapper().countData(params);*/
		return null;
	}

	@Override
	public List<ItemInfo> getItemsByCtgId(long ctgId) {
		List<ItemInfo> items = new LinkedList<ItemInfo>();

		CtgItem ctgItem = new CtgItem();
		ctgItem.setId(ctgId);
		List<CtgItem> ctgItems = ctgItemService.getAll(ctgItem);
		for (CtgItem ctg : ctgItems) {
			ItemInfo itemInfo = new ItemInfo();
			itemInfo.setId(ctg.getItemId());
			itemInfo = itemInfoService.get(itemInfo);
			items.add(itemInfo);
		}
		return items;
	}

	@Autowired
	private ItemInfoService itemInfoService;

	@Autowired
	private CtgItemService ctgItemService;
}