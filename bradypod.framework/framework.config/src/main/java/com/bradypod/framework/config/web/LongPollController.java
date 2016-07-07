package com.bradypod.framework.config.web;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bradypod.framework.config.job.CheckingFileJob;

public class LongPollController extends HttpServlet {

	static ExecutorService pool = Executors.newCachedThreadPool();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CheckingFileJob checkingFileJob = new CheckingFileJob(Paths.get("D://a.txt"));
		Future<Boolean> future = pool.submit(checkingFileJob);
		try {
			writerResponse(resp, future.get().toString());
		} catch (InterruptedException | ExecutionException e) {

		}
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
