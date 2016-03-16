package com.yu.test.nio.god;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * 
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年3月16日
 */
public class RequestHandler implements Handler {

	private SocketChannel channel;

	private ByteBuffer buffer;

	static final int CAPACITY = 1024;

	public RequestHandler(SocketChannel channel) {
		this.channel = channel;
		this.buffer = ByteBuffer.allocate(CAPACITY);
	}

	@Override
	public void handle(SelectionKey sk) throws IOException {
		receive();
		parse();
		response();
		close();
	}

	private void close() throws IOException {
		channel.close();
	}

	private void response() throws IOException {
		Response response = new Response("Welcome to my NIO NON-Blocking Server");
		response.send(channel);
	}

	private void parse() {
		// ignore
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

}
