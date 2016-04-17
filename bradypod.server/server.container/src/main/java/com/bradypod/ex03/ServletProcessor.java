package com.bradypod.ex03;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.bradypod.ex03.connector.http.HttpRequest;
import com.bradypod.ex03.connector.http.HttpResponse;

/**
 * servlet处理器
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年3月23日
 */
public class ServletProcessor {

	static String classPath = "E://work/new-life/bradypod/bradypod.server/server.container/target/classes";

	/**
	 * 处理servlet请求
	 * 
	 * @param request
	 * @param response
	 */
	public void process(HttpRequest request, HttpResponse response) {

		String uri = request.getRequestURI();
		String servletName = uri.substring(uri.lastIndexOf("/") + 1);

		URLClassLoader loader = null;
		try {
			URL[] urls = new URL[1];
			urls[0] = new URL("file", null, classPath);
			loader = new URLClassLoader(urls);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		Class<?> servletClass = null;
		try {
			servletClass = loader.loadClass(servletName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Servlet servlet = null;
		try {
			servlet = (Servlet) servletClass.newInstance();
			servlet.service((ServletRequest) request,
					(ServletResponse) response);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
