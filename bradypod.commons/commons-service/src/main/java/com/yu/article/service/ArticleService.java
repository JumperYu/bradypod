package com.yu.article.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bradypod.common.aop.RedisCache;
import com.bradypod.common.aop.RedisCacheKey;
import com.bradypod.common.po.Page;
import com.bradypod.common.po.PageData;
import com.bradypod.common.service.BaseMybatisServiceImpl;
import com.yu.article.mapper.ArticleMapper;
import com.yu.article.po.Article;
import com.yu.util.validate.AssertUtil;

/**
 * 
 * 文章相关操作
 *
 * @author zengxm
 * @date 2015年5月7日
 *
 */
//@Service
//@Transactional(propagation = Propagation.SUPPORTS)
public class ArticleService extends
		BaseMybatisServiceImpl<ArticleMapper, Article> {

	/**
	 * 添加或者更新文章
	 * 
	 * 
	 * @param Article
	 *            articleId决定
	 * 
	 * @return int effected rows
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveOrUpdateArticle(Article article) {
		int ret = 0;
		if (article.getArticleId() == 0) {
			ret = getMapper().save(article);
		} else {
			getMapper().update(article);
		}
		System.out.println(ret);
	}

	/**
	 * 获取文章
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	@RedisCache(expire = 3600)
	public Article getArticle(@RedisCacheKey long path) {
		AssertUtil.assertGreaterThanZero(path, "需要传入大于0的正整数");
		Article article = new Article();
		article.setArticleId(path);
		article = getMapper().get(article);
		return article;
	}

	/**
	 * 按最新时间批量获取文章简介
	 * 
	 * @throws Exception
	 * @return - List<Article>
	 */
	@RedisCache(expire = 3600)
	public List<Article> getAllArticles() {
		List<Article> articles = getMapper().getAll(new Article());
		return articles;
	}

	/**
	 * 按最新时间批量获取文章简介
	 * 
	 * @param year
	 *            - 年
	 * @param month
	 *            - 月
	 * @param day
	 *            - 日
	 * 
	 * @return - List<Article>
	 */
	public List<Article> getAllArticles(String year, String month, String day) {
		log.info("查找所有文章");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("year", StringUtils.isBlank(year) ? null : year);
		params.put("month", StringUtils.isBlank(month) ? null : month);
		params.put("day", StringUtils.isBlank(day) ? null : day);
		List<Article> articles = getMapper().getAll(null);
		return articles;
	}

	/**
	 * 分页查找数据
	 * 
	 * @param pageSize
	 *            - 页大小
	 * @param pageNO
	 *            - 页码
	 * @return - PageData<List<Article>>
	 */
	public PageData<List<Article>> getArticles(int pageSize, int pageNO) {
		Page page = new Page(pageSize, pageNO);
		Map<String, Object> params = new HashMap<String, Object>();
		return findPageData(page, params);
	}

}
