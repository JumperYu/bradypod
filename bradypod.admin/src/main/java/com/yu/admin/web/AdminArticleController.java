package com.yu.admin.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.ueditor.ActionEnter;
import com.bradypod.common.exception.ServiceException;
import com.bradypod.common.po.Result;
import com.bradypod.common.po.ResultCode;
import com.bradypod.common.util.LoginUserContext;
import com.bradypod.common.web.BaseController;
import com.yu.article.po.Article;
import com.yu.user.po.Account;

/**
 * 
 * 管理文章
 *
 * @author zengxm
 * @date 2015年5月8日
 *
 */
@Controller
public class AdminArticleController extends BaseController {

	@RequestMapping("/")
	public String root() {
		return "index";
	}

	@RequestMapping("/index.html")
	public String index() {
		return "index";
	}

	// 返回longin.html
	@RequestMapping("/login.html")
	public String login() {
		return "login";
	}

	// 阅览具体路径下的文章
	@RequestMapping(value = "/article/{articleId}")
	public String view(@PathVariable long articleId, Map<String, Object> context) {
		context.putAll(INIT_PARAMS);
		Article article = getArticleService().getArticle(articleId);
		if (article != null)
			context.put("article", article);
		else
			throw new ServiceException(ResultCode.UNKNOWN_ERROR,
					"article not found");
		return "article/view";
	}

	// 编辑或添加文章
	@RequestMapping("/article/edit.html")
	public String edit(
			@RequestParam(required = false, defaultValue = "0") long articleId,
			Map<String, Object> context) {
		Account account = LoginUserContext.getLoginAccount();
		context.put("account", account);
		context.putAll(INIT_PARAMS);
		if (articleId > 0) {
			Article article = getArticleService().getArticle(articleId);
			if (article != null)
				context.put("article", article);
		}
		return "article/edit";
	}

	// 文章列表
	@RequestMapping("/article/list.html")
	public String listPages(Map<String, Object> context) {
		Account account = LoginUserContext.getLoginAccount();
		context.put("account", account);
		context.put("articles", getArticleService().getAllArticles("", "", ""));
		context.putAll(INIT_PARAMS);
		return "article/list";
	}

	// 返回UEditor的初始化JSON - 有点坑
	@RequestMapping("/article/config.json")
	@ResponseBody
	public String config(HttpServletRequest request) {
		String root = getClass().getClassLoader()
				.getResource("ueditor.config.json").getPath();
		String result = new ActionEnter(request, root).exec();
		return result;
	}

	// 提交文章
	@RequestMapping(value = "/article/submit")
	@ResponseBody
	public Result<String> submit(@Valid Article article,
			BindingResult bindingResult) {// @RequestParam(required =
		// false, value = "file")
		// MultipartFile file
		Result<String> result = new Result<String>();

		if (bindingResult.hasErrors()) {
			log.error("有错误啊");
			result.setCode(ResultCode.FAILURE);
			result.setMessage("发布失败");
		} else {
			getArticleService().saveOrUpdateArticle(article);
			result.setCode(ResultCode.SUCCESS);
			result.setMessage("发布成功");
		}
		return result;
	}

}
