package com.yu.test.nio.my;

import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;

public abstract class Server {

	ServerSocketChannel ssc;
	
	private static final int PORT = 8000;

	public Server(int port) throws Exception {
		
		this.ssc = ServerSocketChannel.open();
		this.ssc.socket().bind(new InetSocketAddress(port));
	}

	public static void main(String[] args) throws Exception {
		
		Server server = createServer(args);
		
		server.runServer();
		
		System.out.println("server started...");
	}

	public abstract void runServer() throws Exception;

	public static Server createServer(String[] args) throws Exception {
		
		int port = PORT;
		
		return new BlockingServer(port);
	}

}
