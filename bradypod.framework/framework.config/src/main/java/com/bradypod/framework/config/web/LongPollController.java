package com.bradypod.framework.config.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LongPollController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		for (int i = 0; i < 100; i++) {
			try {
				Thread.sleep(1500);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss E");
			String date_str = df.format(new Date());

			writerResponse(resp, date_str);// msg是test.jsp中的那个js方法的名称
		}
		return;
	}

	protected void writerResponse(HttpServletResponse response, String body) throws IOException {
		StringBuffer sb = new StringBuffer();
		sb.append(body);
		response.setContentType("text/html;charset=GBK");
		response.addHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
		response.setHeader("Cache-Control", "pre-check=0,post-check=0");
		response.setDateHeader("Expires", 0);
		response.getWriter().write(sb.toString());
		response.flushBuffer();
	}

	private static final long serialVersionUID = 1L;

}
