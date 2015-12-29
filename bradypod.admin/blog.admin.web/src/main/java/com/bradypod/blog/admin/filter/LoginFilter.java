package com.bradypod.blog.admin.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bradypod.common.util.LoginUserContext;
import com.yu.user.po.Account;

public class LoginFilter implements Filter {

	private static final String LOGIN_PATH = "/login.html";

	private static Set<String> excludePaths;

	@Override
	public void init(FilterConfig config) throws ServletException {
		excludePaths = new HashSet<>();
		excludePaths.add("/");
		excludePaths.add("/login.html");
		excludePaths.add("/auth/login.html");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		String uri = request.getRequestURI();

		for (String exclude : excludePaths) {
			if (uri.equals(exclude)) {
				chain.doFilter(req, resp);
				return;
			}
		}// --> end for

		Account account = LoginUserContext.getLoginAccount(); // 从上下文寻找
		boolean isLogin = false;

		if (account == null) {
			// 尝试从cookie寻找
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie != null && "passport".equals(cookie.getName())) {
						LoginUserContext.setLoginAccount(new Account(cookie.getValue()));
						isLogin = true;
					}
				}
			}// --> end if-else
		} else {
			isLogin = true;
		}// --> end if-else

		if (isLogin) {
			chain.doFilter(req, resp);
		} else {
			response.sendRedirect(LOGIN_PATH);
		}// --> end if-else
	}

	@Override
	public void destroy() {

	}
}
