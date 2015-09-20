package com.bradypod.shop.item.center.service;

import com.bradypod.common.service.BaseMybatiService;
import com.bradypod.shop.item.center.po.CommentCount;

;
/**
 * 商品评论
 *
 * @author zengxm<https://github.com/JumperYu/bradypod>
 * @date Fri Sep 11 12:02:22 CST 2015
 *
 */
public interface CommentService extends BaseMybatiService<CommentCount> {

	/**
	 * 查询实体好评评论数
	 * 
	 * @param entityId
	 *            - 被评论的实体id
	 * @param entityType
	 *            - 被评论的实体类型
	 * @return - Integer
	 */
	Integer getGoodCommentCount(Long entityId, Integer entityType);

	/**
	 * 查询实体中评评论数
	 * 
	 * @param entityId
	 *            - 被评论的实体id
	 * @param entityType
	 *            - 被评论的实体类型
	 * @return - Integer
	 */
	Integer getGeneralCommentCount(Long entityId, Integer entityType);

	/**
	 * 查询实体坏评评论数
	 * 
	 * @param entityId
	 *            - 被评论的实体id
	 * @param entityType
	 *            - 被评论的实体类型
	 * @return - Integer
	 */
	Integer getBadCommentCount(Long entityId, Integer entityType);

	/**
	 * 查询实体所有评论数
	 * 
	 * @param entityId
	 *            - 被评论的实体id
	 * @param entityType
	 *            - 被评论的实体类型
	 * @return - Integer
	 */
	Integer getAllCommentCount(Long entityId, Integer entityType);

	/**
	 * 保存一个评论
	 * 
	 * @param commentCount
	 *            - 评论个数
	 * 
	 */
	void saveInCommentCount(CommentCount commentCount);
}