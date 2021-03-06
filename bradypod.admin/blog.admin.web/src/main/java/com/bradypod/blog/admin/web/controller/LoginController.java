package com.bradypod.blog.admin.web.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bradypod.admin.authority.retention.Menu;
import com.bradypod.admin.authority.retention.MenuResource;
import com.bradypod.common.po.Result;
import com.bradypod.common.po.ResultCode;
import com.bradypod.common.util.LoginUserContext;
import com.bradypod.common.util.ParameterUtil;
import com.yu.user.po.Account;

/**
 * 用户授权
 *
 * @author zengxm
 * @date 2015年4月23日
 *
 */
@Controller
@RequestMapping("/auth")
@Menu(name = "登陆")
public class LoginController {

	@RequestMapping(value = "/login")
	@ResponseBody
	@MenuResource("")
	public Result<String> login(Account account, HttpServletResponse response) {
		Result<String> result = new Result<String>();
		try {
			ParameterUtil.assertNotBlank(account.getPassport(), "用户账号为空");
			ParameterUtil.assertNotBlank(account.getPassword(), "用户密码为空");

			// 设置cookie
			Cookie cookie = new Cookie("passport", account.getPassport());
			cookie.setDomain(".ttwg168.com"); // 请求域来源
			cookie.setHttpOnly(false); // 前端脚本无法获取
			cookie.setMaxAge(60 * 1); // -1 表示关闭浏览器则消失
			cookie.setPath("/");

			response.addCookie(cookie);

			LoginUserContext.setLoginAccount(account); // 保存副本

			result.setCode(ResultCode.SUCCESS);
			result.setMessage("成功登陆");
		} catch (Exception e) {
			log.error("系统未知错误", e);

			result.setCode(ResultCode.UNKNOWN_ERROR);
			result.setMessage("系统出现未知错误");
		}
		return result;
	}

	protected static final Logger log = LoggerFactory.getLogger(LoginController.class);
}
