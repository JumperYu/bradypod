package com.bradypod.framework.config.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LongPollController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		writerResponse(resp, "");
	}

	protected void writerResponse(HttpServletResponse response, String body) throws IOException {
		StringBuffer sb = new StringBuffer();
		sb.append(body);
		response.setContentType("text/html;charset=GBK");
		response.addHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
		response.setHeader("Cache-Control", "pre-check=0,post-check=0");
		response.setDateHeader("Expires", 0);
		response.getWriter().print(sb.toString());
		response.flushBuffer();
	}

	private static final long serialVersionUID = 1L;

}
