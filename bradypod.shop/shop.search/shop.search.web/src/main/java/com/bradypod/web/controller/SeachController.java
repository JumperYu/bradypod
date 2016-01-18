package com.bradypod.web.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 搜索接口
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年1月18日
 */
@WebServlet("/search")
public class SeachController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setContentType("text/plain");
		resp.setCharacterEncoding("utf-8");

		PrintWriter out = resp.getWriter();
		out.write("this");
		out.flush();
		out.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setCharacterEncoding("utf-8");
		resp.setContentType("application/xml");

		PrintWriter out = resp.getWriter();
		out.write("");
		out.flush();
		out.close();
	}

	private static final long serialVersionUID = 1L;
}
