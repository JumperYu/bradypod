package com.yu.article.po;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class ArticleTest {

	Date createTime;
	
	@DateTimeFormat(pattern = "YYYY-MM-dd")
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
