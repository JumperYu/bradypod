package com.bradypod.ex03.connector.http;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Stack;

/**
 * http连接器
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年3月23日
 */
public class HttpConnector implements Runnable, Connector {

	int port;
	private String scheme = "http";

	static final int DEFAULT_PORT = 8080;

	public HttpConnector() {
		this(DEFAULT_PORT);
	}

	public HttpConnector(int port) {
		this.port = port;
	}

	public String getScheme() {
		return scheme;
	}

	@Override
	public void run() {
		ServerSocket serverSocket = null;
		int port = 8080;
		try {
			serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
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

	private volatile boolean started = false;
	private volatile boolean stopped = false;
	private int curProcessor;
	private int minProcessor = 5;
	private int maxProcessors = 20;
	private Stack processors = new Stack();
	private Thread thread;

	/**
	 * 启动线程
	 */
	public void start() {
		if (started) {
			System.exit(-1);
		}
		started = true;
		threadStart();

		// create the specified handler
		HttpHandler handler = newHandler();
		recycle(handler);
	}

	private HttpHandler newHandler() {
		return new HttpHandler();
	}

	private void recycle(HttpHandler handler) {

	}

	/**
	 * 线程启动
	 */
	public void threadStart() {
		System.out.println("http connector starting");
		thread = new Thread(this, "ex03.http.server.connector");
		thread.setDaemon(true);
		thread.start();
	}

	// 容器
	private Container container;

	@Override
	public Container getContainer() {
		return container;
	}

	@Override
	public void setContainer(Container container) {
		this.container = container;
	}

	@Override
	public boolean getEnableLookups() {
		return false;
	}

	@Override
	public HttpRequest createRequest() {
		return null;
	}

	@Override
	public HttpResponse createResponse() {
		return null;
	}

	@Override
	public void initialize() {

	}
}
