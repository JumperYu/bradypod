package com.yu.nio;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BIO Tcp连接服务
 *
 * @author zengxm
 * @date 2015年9月3日
 *
 */
public class BioServer {

	static Logger log = LoggerFactory.getLogger(BioServer.class);

	public static final int PORT = 2333;
	public static final int TIME_OUT = 1000;

	public static void main(String[] args) {
		try (ServerSocket sc = new ServerSocket(PORT)) {
			// sc.setSoTimeout(1000);
			log.info("Bio Server start");
			while (true) {
				Socket socket = sc.accept();
				log.info("A connection " + socket.getRemoteSocketAddress());
				PrintWriter out = SocketUtil.getPrintWriter(socket);
				out.write("Hi !");
				out.close();
				socket.close();
			}
		} catch (Exception e) {
			// TODO
			e.printStackTrace();
		}

	}

}
