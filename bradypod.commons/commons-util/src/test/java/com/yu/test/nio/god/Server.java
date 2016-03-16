package com.yu.test.nio.god;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;

/**
 * 服务器抽象层
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年3月16日
 */
public abstract class Server {

	public static void main(String[] args) throws IllegalAccessException,
			IOException {
		Server server = createServer(args);
		server.run();
	}

	protected ServerSocketChannel ssc;

	public Server(int port) throws IOException {
		this.ssc = ServerSocketChannel.open();
		this.ssc.bind(new InetSocketAddress(port));
	}

	/**
	 * 启动服务器
	 */
	public abstract void run() throws IOException;

	/**
	 * Usage java Server -port 8000 -type blocking | non-blocking
	 */
	public static Server createServer(String[] args)
			throws IllegalAccessException, IOException {

		if (args == null || args.length <= 0) {
			throw new IllegalAccessException(
					"Usage java Server -port 80 -type blocking | non-blocking");
		}

		int port = Integer.parseInt(args[1]);

		String type = args[3];

		switch (type) {
		case "blocking":
			return new BlockingServer(port);
		case "non-blocking":
			return new NonBlockingServer(port);
		default:
			throw new IllegalAccessException(
					"Usage java Server -port 80 -type blocking | non-blocking");
		}
	}

}
