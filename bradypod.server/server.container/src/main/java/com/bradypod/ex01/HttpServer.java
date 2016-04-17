package com.bradypod.ex01;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 简单抽象一个Socket服务器, 监听请求
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年3月17日
 */
public class HttpServer {

	static final String CLASSPATH = System.getProperty("user.dir") + "";

	static final String SHUTDOWN = "/SHUTDOWN";

	static final int DEFAULT_PORT = 80;

	private int port;

	protected ServerSocketChannel ssc;

	public HttpServer() throws IOException {
		this(DEFAULT_PORT);

	}

	public HttpServer(int port) throws IOException {
		this.port = port;
		this.ssc = ServerSocketChannel.open();
		this.ssc.bind(new InetSocketAddress(this.port));
		this.ssc.configureBlocking(true);
	}

	public static void main(String[] args) throws IOException,
			URISyntaxException {
		HttpServer server = new HttpServer();
		server.await();
	}

	public void await() throws IOException, URISyntaxException {
		for (;;) {
			SocketChannel channel = ssc.accept();

			Request request = new Request(channel);
			request.parse();
			
			Response response = new Response(channel);
			response.setRequest(request);
			response.send();

			channel.close();
		}
	}
}
