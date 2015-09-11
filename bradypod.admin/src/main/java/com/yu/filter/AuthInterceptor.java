package com.yu.filter;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.bradypod.common.util.LoginUserContext;
import com.yu.user.po.Account;

/**
 * 
 * 拦截所有请求 检查ThreadLocal的副本变量和Cookie里面的存储值
 *
 * @author zengxm
 * @date 2015年5月2日
 *
 */
public class AuthInterceptor implements HandlerInterceptor {

	/**
	 * excludeUri - 排除uri <br>
	 * exclude - 排除后缀 <br>
	 * loginPage - 重定向到登录页 <br>
	 */
	private static Set<String> excludeUri = new HashSet<String>();
	private static Set<String> excludeSuffix = new HashSet<String>();
	private static String loginPage;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		log.info("auth intercept uri: " + uri);

		// <-- TODO 待完善
		for (String suffix : excludeSuffix) {
			if (uri.endsWith(suffix)) {
				return true;
			}
		}
		for (String exclude : excludeUri) {
			if (uri.equals(exclude)) {
				return true;
			}
		}
		// --> End

		Account account = LoginUserContext.getLoginAccount();
		if (account != null) {
			log.info(String.format(
					"account:%s request uri %s is logged in context",
					account.getPassport(), uri));
			return true;
		}
		// TODO 待完善cookie
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie != null && "passport".equals(cookie.getName())) {
					LoginUserContext.setLoginAccount(new Account(cookie
							.getValue()));
					log.info(String.format(
							"request uri %s is logged in cookie", uri));
					return true;
				}
			}
		}
		log.info(String.format("sending redirect %s", loginPage));
		response.sendRedirect(loginPage + "?fromPage="
				+ request.getRequestURI());
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	/* set */
	public static void setExcludeSuffix(Set<String> excludeSuffix) {
		AuthInterceptor.excludeSuffix = excludeSuffix;
	}

	public static void setExcludeUri(Set<String> excludeUri) {
		AuthInterceptor.excludeUri = excludeUri;
	}

	public void setLoginPage(String page) {
		loginPage = page;
	}

	private static final Logger log = LoggerFactory
			.getLogger(AuthInterceptor.class);
}
