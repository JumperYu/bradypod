package com.yu.test.nio.my;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ChannelIO {

	protected SocketChannel sc;

	protected ByteBuffer requestBuffer;

	private static final int REQUEST_BUF_SIZE = 4096;

	public ChannelIO(SocketChannel sc, boolean blocking) throws IOException {
		this.sc = sc;
		sc.configureBlocking(blocking);
	}

	public static ChannelIO getInstance(SocketChannel sc, boolean blocking)
			throws IOException {
		ChannelIO cio = new ChannelIO(sc, blocking);
		cio.requestBuffer = ByteBuffer.allocate(REQUEST_BUF_SIZE);
		return cio;
	}
}
