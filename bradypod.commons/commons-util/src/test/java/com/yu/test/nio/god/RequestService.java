package com.yu.test.nio.god;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * 请求处理逻辑
 * 
 * 1, 接收请求信息
 * 
 * 2, 分析请求信息
 * 
 * 3, 处理响应
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年3月16日
 */
public class RequestService implements Runnable {

	private SocketChannel channel;

	private ByteBuffer buffer;

	static final int CAPACITY = 1024;

	public RequestService(SocketChannel channel) {
		this.channel = channel;
		this.buffer = ByteBuffer.allocate(CAPACITY);
	}

	@Override
	public void run() {
		try {
			// 服务
			service();

			// 关闭IO
			channel.close();
		} catch (IOException e) {

		}
	}

	public void service() throws IOException {
		receive();
		parse();
		response();
	}

	Charset utf8 = Charset.forName("UTF-8");

	private void receive() throws IOException {
		for (;;) {
			if (buffer.remaining() < CAPACITY) {
				ByteBuffer newBuffer = ByteBuffer
						.allocate(buffer.capacity() * 2);
				newBuffer.put(buffer);
				buffer = newBuffer;
			}
			int readLen = channel.read(buffer);
			if (readLen <= 0 || Request.isComplete(buffer)) {
				break;
			}
		}
		// 打印看接收到什么东西
		// buffer.flip();
		// System.out.println("receive: \n" + utf8.decode(buffer));
	}

	private void parse() {
		// 忽略这一步
	}

	private void response() throws IOException {
		Response response = new Response("Welcome to my NIO Blocking Server");
		response.send(channel);
	}
}
