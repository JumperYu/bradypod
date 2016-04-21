package com.bradypod.ex03.connector.http;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Stack;

/**
 * Implementation of an HTTP/1.1 connector
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年3月23日
 */
public final class HttpConnector implements Runnable, Connector {

	private int port;
	private String scheme = "http";

	static final int DEFAULT_PORT = 8080;

	public HttpConnector() {
		this(DEFAULT_PORT);
	}

	public HttpConnector(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		while (!stopped) {
			// Accept the next incoming connection from the server socket
			Socket socket = null;
			try {
				socket = serverSocket.accept();
			} catch (Exception e) {
				continue;
			}
			// Hand this socket off to an HttpProcessor
			HttpHandler handler = new HttpHandler(this);
			handler.handle(socket);
		}
	}

	private volatile boolean started = false;
	private volatile boolean stopped = false;
	private boolean initialized = false;
	private int curProcessor = 0;
	private int minProcessor = 5;
	private int maxProcessors = 20;
	private Stack processors = new Stack();
	private Thread thread;

	private ServerSocket serverSocket;

	/**
	 * 启动线程
	 */
	public void start() {
		if (started) {
			System.exit(-1);
		}

		threadStart(); // 启动线程

		started = true; // 设置标识位

		// create the specified handler
		// HttpHandler handler = newHandler();
		// recycle(handler);
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
		System.out.println("BradyPod Server: http connector starting");
		thread = new Thread(this, "ex03.http.server.connector");
		thread.setDaemon(true);
		thread.start();
	}

	/**
	 *	 
	 */
	public ServerSocket open() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(this.port, 1, InetAddress.getByName("127.0.0.1"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return serverSocket;
	}

	/**
	 * Initialize this connector (create ServerSocket here!)
	 */
	@Override
	public void initialize() {
		serverSocket = open();
		initialized = true;
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

	public String getScheme() {
		return scheme;
	}

}
