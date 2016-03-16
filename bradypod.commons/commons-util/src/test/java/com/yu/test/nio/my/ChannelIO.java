package com.yu.test.nio.my;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 负责读取数据和关闭通道 
 * 
 * @author	zengxm<http://github.com/JumperYu>
 *
 * @date	2016年3月16日
 */
public class ChannelIO {

	protected SocketChannel sc;

	protected ByteBuffer requestBuffer;

	private static final int REQUEST_BUF_SIZE = 4096;

	public ChannelIO(SocketChannel sc, boolean blocking) throws IOException {
		this.sc = sc;
		sc.configureBlocking(blocking);
	}

	/**
	 * 获取实例
	 * 
	 * @param sc
	 *            - SocketChannel
	 * @param blocking
	 *            - 是否阻塞
	 * @return
	 * @throws IOException
	 */
	public static ChannelIO getInstance(SocketChannel sc, boolean blocking)
			throws IOException {
		ChannelIO cio = new ChannelIO(sc, blocking);
		cio.requestBuffer = ByteBuffer.allocate(REQUEST_BUF_SIZE);
		return cio;
	}

	/**
	 * 读取到缓冲区
	 * 
	 * @return
	 * @throws IOException
	 */
	public int read() throws IOException {
		resizeBB(REQUEST_BUF_SIZE);
		return sc.read(requestBuffer);
	}

	protected void resizeBB(int remaining) {
		if (requestBuffer.remaining() < remaining) {
			ByteBuffer buffer = ByteBuffer
					.allocate(requestBuffer.capacity() * 2);
			buffer.flip();
			buffer.put(requestBuffer);
			requestBuffer = buffer;
		}
	}

	/**
	 * 获取缓冲区
	 */
	public ByteBuffer getRequestBuffer() {
		return requestBuffer;
	}

	public boolean shutdown() {
		return false;
	}

	public void close() throws IOException {
		sc.close();
	}

	public int write(ByteBuffer bb) throws IOException {
		return sc.write(bb);
	}
}
