package com.bradypod.ex03.connector.http;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * http连接器
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年3月23日
 */
public class HttpConnector implements Runnable {

	boolean stopped;
	private String scheme = "http";

	public String getScheme() {
		return scheme;
	}

	@Override
	public void run() {
		ServerSocket serverSocket = null;
		int port = 8080;
		try {
			serverSocket = new ServerSocket(port, 1,
					InetAddress.getByName("127.0.0.1"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		while (!stopped) {
			// Accept the next incoming connection from the server socket
			Socket socket = null;
			try {
				socket = serverSocket.accept();
			} catch (Exception e) {
				continue;
			}
			// Hand this socket off to an HttpProcessor
			HttpHandler processor = new HttpHandler(this);
			processor.handle(socket);
		}
	}

	/**
	 * 启动线程
	 */
	public void start() {
		Thread thread = new Thread(this, "ex03.http.server.connector");
		thread.start();
	}
}
