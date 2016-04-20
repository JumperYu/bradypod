package com.bradypod.ex03.connector.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.bradypod.ex03.ResourceProcessor;
import com.bradypod.ex03.ServletProcessor;

/**
 * 处理socket
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年3月23日
 */
public class HttpHandler {

	HttpConnector connector = null;

	private HttpRequest request;

	private HttpResponse response;

	public HttpHandler(HttpConnector connector) {
		this.connector = connector;
	}

	public void handle(Socket socket) {
		InputStream input = null;
		OutputStream output = null;

		try {
			input = socket.getInputStream();
			output = socket.getOutputStream();

			// 创建请求对象
			request = new HttpRequest(input);

			// 创建响应对象
			response = new HttpResponse(output);
			response.setRequest(request);

			// 设置响应头
			response.addHeader("Server", "Bradypod Server Container");
			
			// 解析请求
			request.parse();
			
			response.sendHeaders(); // 发送响应头
			
			// 判断是否请求动态资源
			if (request.getRequestURI() == null) {
				response.getWriter().println("404 Not Found!");
			} else if (request.getRequestURI().startsWith("/servlet/")) {
				ServletProcessor processor = new ServletProcessor();
				processor.process(request, response);
			} else {
				ResourceProcessor processor = new ResourceProcessor();
				processor.process(request, response);
			} // --> end if-else

			// 关闭连接
			socket.close();
		} catch (IOException ex) {

		}
	}

}
