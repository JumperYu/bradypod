package com.bradypod.blog.admin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bradypod.admin.authority.retention.Menu;
import com.bradypod.admin.authority.retention.MenuResource;

/**
 * 首页配置
 *
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2015年12月29日 上午11:32:46
 */
@Controller
@Menu(name = "首页")
public class IndexController {

	@RequestMapping(value = "index")
	public String index() {
		return "index";
	}

	@RequestMapping(value = "index2")
	@MenuResource("页面2")
	public String index2() {
		return "index2";
	}

	@RequestMapping(value = "index3")
	@MenuResource("页面3")
	public String index3() {
		return "index2";
	}

	@RequestMapping(value = "menu")
	@MenuResource("菜单")
	public String menu() {
		return "menu";
	}

	@RequestMapping(value = "login")
	public String login() {
		return "login";
	}
}
