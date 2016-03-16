package com.yu.test.nio.my;

import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;

/**
 * java nio demo
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年3月16日
 */
public abstract class Server {

	public static void main(String[] args) throws Exception {

		Server server = createServer(args);

		server.runServer();

		System.out.println("server started...");
	}

	ServerSocketChannel ssc; // 服务端通道

	static final int PORT = 8000; // 默认端口

	public Server(int port) throws Exception {

		this.ssc = ServerSocketChannel.open();
		this.ssc.socket().bind(new InetSocketAddress(port));
	}

	public abstract void runServer() throws Exception;

	/**
	 * 创建服务器
	 * 
	 * @param args
	 *            - String[]
	 * @return
	 * @throws Exception
	 */
	public static Server createServer(String[] args) throws Exception {

		if (args == null || args.length < 1) {
			throw new IllegalArgumentException(
					"Usage: java Server -p 8000 -t  n|b");
		}

		int port = Integer.parseInt(args[1]);

		String type = args[3];

		switch (type) {
		case "b":
			return new BlockingServer(port);
		case "n":
			return new NonBlockingServer(port);
		default:
			throw new IllegalAccessException(
					"Usage: java Server -p 8000 -t n|b");
		}

	}

}
