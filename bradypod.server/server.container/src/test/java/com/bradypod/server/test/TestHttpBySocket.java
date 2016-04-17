package com.bradypod.server.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import org.junit.Test;

/**
 * 帮助了解http协议
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年3月17日
 */
public class TestHttpBySocket {

	static final String CLRF = "\r\n";

	@Test
	public void testHttpRequest() {
		try (Socket socket = new Socket("127.0.0.1", 80)) {
			OutputStream os = socket.getOutputStream();
			PrintWriter out = new PrintWriter(os);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			// send http GET
			out.println("GET /index.php HTTP/1.1");
			out.println("Host: 127.0.0.1");

			out.println("Connection: Close");
			out.println();
			
			out.flush();
			
			StringBuilder sb = new StringBuilder();
			while (true) {
				if (in.ready()) {
					String line = null;
					while ((line = in.readLine()) != null) {
						sb.append(line).append("\n");
					}
					break;
				}
				Thread.sleep(50);
			}
			// output:
			System.out.println(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
