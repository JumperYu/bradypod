package com.bradypod.shop.item.center.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.*;

/**
 * 商品评论统计
 *
 * @author zengxm<https://github.com/JumperYu/bradypod>
 * @date 2015-09-11 12:02:22
 *
 */
public class CommentCount implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id; // 主键
	private Long entityId; // 被评论的实体id
	private Integer entityType; // 被评论的实体类型
	private Integer starNum; // 评星
	private Long commentId; // 评论键

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityType(Integer entityType) {
		this.entityType = entityType;
	}

	public Integer getEntityType() {
		return entityType;
	}

	public Integer getStarNum() {
		return starNum;
	}

	public void setStarNum(Integer starNum) {
		this.starNum = starNum;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public Long getCommentId() {
		return commentId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}