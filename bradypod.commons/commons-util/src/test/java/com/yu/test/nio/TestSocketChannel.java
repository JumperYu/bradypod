package com.yu.test.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import org.junit.Test;

/**
 * 
 * 传统的java.net.Socket编程 1，需要一个线程去接收； 2，需要开一个线程池去开启一个新的连接交互
 * 
 * NIO的SocketChannel，并是不说多么的优越，而是在api上可以丰富
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年2月17日
 */
public class TestSocketChannel {

	@Test
	public void testBlockChannel() throws IOException {
		
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.bind(new InetSocketAddress(8009));
		
		while (true) {
			SocketChannel socketChannel = serverSocketChannel.accept();
			if (socketChannel != null) {
				ByteBuffer buff = ByteBuffer.allocate(24);
				while(socketChannel.read(buff) != -1){
					buff.flip();
					System.out.print(Charset.forName("utf-8").decode(buff));
					buff.clear();
				}
			}//--> end if
		}//--> end while
		
		// client or telnet localhost 8009
		
		// SocketChannel socketChannel = SocketChannel.open();
		// socketChannel.connect(new InetSocketAddress("localhost", 8009));
		// socketChannel.write(ByteBuffer.wrap("中文".getBytes()));
	}

}
