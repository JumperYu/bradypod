package com.yu.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * @see ExceptionHandlerController
 * 
 *
 * @author zengxm<github.com/JumperYu>
 *
 * 2015年9月10日 下午1:24:20
 */
//@Component(value = DispatcherServlet.HANDLER_EXCEPTION_RESOLVER_BEAN_NAME)
public class ExceptionHandlerResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {

		return null;
	}

}
