/*
 * Powered By [generator-framework]
 * Web Site: http://blog.bradypod.com
 * Github: https://github.com/JumperYu
 * Since 2015 - 2015
 */

package com.bradypod.shop.item.center.service;

import java.util.List;

import com.bradypod.common.service.BaseMybatiService;
import com.bradypod.shop.item.center.po.CtgInfo;
import com.bradypod.shop.item.center.po.ItemInfo;



/**
 *
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015-09-20
 */
public interface CtgInfoService extends BaseMybatiService<CtgInfo>  {
	
	/**
	 * 获取树状节点
	 * 
	 * @return - CtgInfo
	 */
	public CtgInfo getCtgInfoTree();
	
	/**
	 * 根据分类id获取
	 * 
	 * @param ctgId
	 * @return
	 */
	public List<ItemInfo> getItemsByCtgId(long ctgId);
}