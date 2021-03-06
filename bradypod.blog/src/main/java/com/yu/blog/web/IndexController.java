package com.yu.blog.web;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bradypod.common.exception.ServiceException;
import com.bradypod.common.po.ResultCode;
import com.bradypod.common.web.BaseController;
import com.yu.article.po.Article;

/**
 * 
 * blog.bradypod.com 博客二级域名
 *
 * @author zengxm
 * @date 2015年5月8日
 *
 */
@Controller
public class IndexController extends BaseController {

	// 根路径
	@RequestMapping("/")
	public String root(Map<String, Object> context) {
		return index(context);
	}

	// 首页
	@RequestMapping("/index.html")
	public String index(Map<String, Object> context) {
		List<Article> articles = getArticleService().getAllArticles();
		if (CollectionUtils.isEmpty(articles)) {
			throw new ServiceException(ResultCode.FAILURE, "articles not found");
		} else {
			context.put("articles", articles);
		}
		return "index";
	}

	// 阅览具体路径下的文章
	@RequestMapping(value = "/article/{articleId}")
	public String view(@PathVariable long articleId, Map<String, Object> context) {
		Article article = getArticleService().getArticle(articleId);
		if (article != null)
			context.put("article", article);
		else
			throw new ServiceException(ResultCode.FAILURE, "article not found");
		return "view";
	}

}
