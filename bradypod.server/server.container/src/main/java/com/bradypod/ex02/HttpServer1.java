package com.bradypod.ex02;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.channels.SocketChannel;

import com.bradypod.ex01.HttpServer;

public class HttpServer1 extends HttpServer {

	public HttpServer1() throws IOException {
		super();
	}

	@Override
	public void await() throws IOException, URISyntaxException {
		for (;;) {
			SocketChannel channel = ssc.accept();

			Request request = new Request(channel);
			request.parse();


			Response response = new Response(channel);
			response.setRequest(request);
			
			if (request.getUri().toString().startsWith("/servlet")) {
				ServletProcessor servletProcessor = new ServletProcessor1();
				servletProcessor.process(request, response);
			}
			
			//response.send();

			channel.close();
		}
	}

	public static void main(String[] args) throws IOException, URISyntaxException {
		HttpServer server = new HttpServer1();
		server.await();
	}
}
