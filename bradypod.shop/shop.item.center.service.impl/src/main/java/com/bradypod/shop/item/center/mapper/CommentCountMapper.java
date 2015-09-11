package com.bradypod.shop.item.center.mapper;

import java.util.Map;

import com.bradypod.common.mapper.BaseMapper;
import com.bradypod.shop.item.center.po.CommentCount;

public interface CommentCountMapper extends BaseMapper<CommentCount> {

	/**
	 * 根据条件查找评论的个数
	 * 
	 * @param params
	 *            - minStar(最小星), maxStar(最大星), entityId(评论实体), entityType(评论类型)
	 * @return
	 */
	public Integer countComment(Map<String, Object> params);

}
