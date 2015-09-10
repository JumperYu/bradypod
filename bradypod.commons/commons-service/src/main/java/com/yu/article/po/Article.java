package com.yu.article.po;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 文章实体
 *
 * @author zengxm
 * @date 2015年5月7日
 *
 */
public class Article implements Serializable {

	private static final long serialVersionUID = 1L;

	private long articleId;

	private String path;

	@NotNull
	private String title;

	private String summary;

	private String content;
	
	@DateTimeFormat(pattern = "YYYY-MM-dd")
	private Date createTime;
	
	@DateTimeFormat(pattern = "YYYY-MM-dd")
	private Date updateTime;

	public long getArticleId() {
		return articleId;
	}

	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
