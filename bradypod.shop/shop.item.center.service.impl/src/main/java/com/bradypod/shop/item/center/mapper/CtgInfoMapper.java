/*
 * Powered By [generator-framework]
 * Web Site: http://blog.bradypod.com
 * Github: https://github.com/JumperYu
 * Since 2015 - 2015
 */

package com.bradypod.shop.item.center.mapper;

import java.util.List;

import com.bradypod.common.mapper.BaseMapper;
import com.bradypod.shop.item.center.po.CtgInfo;

/**
 *
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015-09-19
 */
public interface CtgInfoMapper extends BaseMapper<CtgInfo> {

	/**
	 * 获取所有父节点列表
	 * 
	 * @return - List<Long>
	 */
	public List<Long> getAllParentId();
}