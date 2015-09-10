package com.yu.nio;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * 非阻塞UDP
 *
 * @author zengxm
 * @date 2015年9月4日
 *
 */
public class DataGramExample {

	public static void main(String[] args) throws IOException {
		int port = 8088;
		initReceive(port);
	}

	public static void initReceive(int port) throws IOException {
		DatagramChannel channel = DatagramChannel.open();
		channel.configureBlocking(false);
		DatagramSocket socket = channel.socket();
		socket.bind(new InetSocketAddress(port));
		Selector selector = Selector.open();
		channel.register(selector, SelectionKey.OP_READ);
		while (true) {
			selector.select();
			Set<SelectionKey> keys = selector.selectedKeys();
			for (SelectionKey key : keys) {
				// 可接收状态
				if (key.isAcceptable()) {
					ServerSocketChannel ssc = (ServerSocketChannel) key
							.channel();
					SocketChannel sc = ssc.accept();
					if (!sc.isConnected()) {
						sc.close();
						keys.remove(key);
						continue;
					}
					sc.configureBlocking(false);
					// 在这里可以发送消息给客户端
					sc.write(ByteBuffer.wrap(new String("hello client")
							.getBytes()));
					// 在客户端 连接成功之后，为了可以接收到客户端的信息，需要给通道设置读的权限
					sc.register(selector, SelectionKey.OP_READ);
					// 删除已经执行过的key, 避免重复
					keys.remove(key);
				} else if (key.isReadable()) {
				}
			}
		}

	}

}
