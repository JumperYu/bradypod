package com.yu.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BioClient {

	static Logger log = LoggerFactory.getLogger(BioClient.class);

	public static final int PORT = 2333;
	public static final int TIME_OUT = 1000;

	public static void main(String[] args) {
		try (Socket socket = new Socket()) {
			socket.setSoTimeout(TIME_OUT);
			socket.bind(new InetSocketAddress("localhost", PORT));
			while (true) {
				String line = SocketUtil.getBufferInputStream(socket)
						.readLine();
				if (line == null) {
					continue;
				}
				log.info("server say : " + line);
				TimeUnit.SECONDS.sleep(5);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
