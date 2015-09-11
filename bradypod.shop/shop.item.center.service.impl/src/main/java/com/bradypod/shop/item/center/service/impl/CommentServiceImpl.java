package com.bradypod.shop.item.center.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.bradypod.common.service.MyBatisBaseService;
import com.bradypod.shop.item.center.constants.CommentConstants;
import com.bradypod.shop.item.center.mapper.CommentCountMapper;
import com.bradypod.shop.item.center.mapper.CommentMapper;
import com.bradypod.shop.item.center.po.Comment;
import com.bradypod.shop.item.center.po.CommentCount;
import com.bradypod.shop.item.center.service.CommentService;

/**
 * 评论业务实现
 *
 * @author zengxm<github.com/JumperYu>
 *
 *         2015年9月11日 上午10:37:41
 */
@Service
public class CommentServiceImpl extends MyBatisBaseService<Comment, CommentMapper> implements
		CommentService<Comment, CommentMapper> {

	/**
	 * 查询评论个数
	 */
	private Integer getCommentCount(CommentConstants commentConstants, Long entityId,
			Integer entityType) {
		Map<String, Object> params = new HashMap<>();
		params.put("minStar", commentConstants.getMinStar());
		params.put("maxStar", commentConstants.getMaxStar());
		params.put("entityId", entityId);
		params.put("entityType", entityType);
		return commentCountMapper.countComment(params);
	}

	@Override
	public Integer getGoodCommentCount(Long entityId, Integer entityType) {
		return getCommentCount(CommentConstants.GOOD_COMMENT, entityId, entityType);
	}

	@Override
	public Integer getGeneralCommentCount(Long entityId, Integer entityType) {
		return getCommentCount(CommentConstants.GENERAL_COMMENT, entityId, entityType);
	}

	@Override
	public Integer getBadCommentCount(Long entityId, Integer entityType) {
		return getCommentCount(CommentConstants.BAD_COMMENT, entityId, entityType);
	}

	@Override
	public Integer getAllCommentCount(Long entityId, Integer entityType) {
		return getCommentCount(CommentConstants.ALL_COMMENT, entityId, entityType);
	}

	@Override
	public void saveInCommentCount(CommentCount commentCount) {
		commentCountMapper.save(commentCount);
	}

	/* set */
	private CommentCountMapper commentCountMapper;

	@PostConstruct
	public void setCommentCountMapper() {
		this.commentCountMapper = getMyBatisBaseDAO().getSqlSession().getMapper(
				CommentCountMapper.class);
	}
}
