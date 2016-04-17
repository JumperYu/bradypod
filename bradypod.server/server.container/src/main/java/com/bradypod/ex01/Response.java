package com.bradypod.ex01;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Response {

	SocketChannel channel;
	Request request;

	public Response(SocketChannel channel) {
		this.channel = channel;
	}

	public void send() throws IOException {
		// ByteBuffer.wrap("Welcome to my container.".getBytes())
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		FileChannel.open(Paths.get("E://index.txt"), StandardOpenOption.READ).read(buffer);
		buffer.flip();
		channel.write(buffer);
	}

	public void setRequest(Request request) {
		this.request = request;
	}
}
