package com.bradypod.ex02;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ServletProcessor1 implements ServletProcessor {

	static String classPath = "E://work/new-life/bradypod/bradypod.server/server.container/target/classes";

	@Override
	public void process(Request request, Response response) {

		String uri = request.getUri().toString();
		String servletName = uri.substring(uri.lastIndexOf("/") + 1);

		URLClassLoader loader = null;
		try {
			URL[] urls = new URL[1];
			urls[0] = new URL("file", null, classPath);
			System.out.println(urls[0].toString());
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
