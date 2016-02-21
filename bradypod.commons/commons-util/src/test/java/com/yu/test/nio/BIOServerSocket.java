package com.yu.test.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 传统的BIO模式的Socket服务端
 * 
 * 特点：
 * 1，需要一个线程进行阻塞接收客户端请求连接；
 * 2，需要为新建连接创建一个线程维护连接交互；
 * 3，由于资源的问题，线程不能无限创建，故会使用线程池概念，线程池的大小以CPU数作为参考值；
 * 
 * @author zengxm 2015年3月13日
 * 
 */
public class BIOServerSocket {

	public static void main(String[] args) {
		
		ServerSocket server = null;
		ExecutorService executorService = Executors.newCachedThreadPool();
		try {
			server = new ServerSocket(8001);
			System.out.println("server start");
			for (;;) {
				final Socket socket = server.accept();
				System.out.println("client:" + socket.getInetAddress().getHostAddress() + " connected");
				executorService.execute(new Thread() {
					@Override
					public void run() {
						BufferedReader in;
						try {
							in = new BufferedReader(new InputStreamReader(
									socket.getInputStream()));
							PrintWriter out = new PrintWriter(socket
									.getOutputStream());
							String line = "";
							while (!(line = in.readLine()).equals("bye")) {
								System.out.println("client:" + line);
								out.println("you said: " + line);
								out.flush();
							}
							out.write("bye");
							in.close();
							out.close();
							socket.close();
						} catch (IOException e) {
							//e.printStackTrace();
							System.out.println("socket has been closed");
						}
					}
				});
			}
			// server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
