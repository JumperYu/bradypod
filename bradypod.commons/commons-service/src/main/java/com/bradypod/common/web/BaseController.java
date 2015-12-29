package com.bradypod.common.web;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yu.article.service.ArticleService;
import com.yu.user.service.UserService;

/**
 * 
 * 基础模板
 *
 * @author zengxm
 * @date 2015年4月14日
 *
 */
public class BaseController {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource
	private UserService userService;

	@Resource
	private ArticleService articleService;

	public UserService getUserService() {
		return userService;
	}

	public ArticleService getArticleService() {
		return articleService;
	}
}
