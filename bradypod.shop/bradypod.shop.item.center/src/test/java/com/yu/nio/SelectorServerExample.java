package com.yu.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 一条线程一个选择器处理多个通道
 * 
 * @author zengxm 2015年3月30日
 * 
 */
public class SelectorServerExample {

	private Selector selector;

	public static void main(String[] args) {
		SelectorServerExample example = new SelectorServerExample();
		example.initServer(8008);
	}

	/**
	 * 初始化NIO Server
	 * 
	 * @param port
	 *            - 监听端口
	 */
	public void initServer(int port) {
		try {
			// 设置一个通道
			ServerSocketChannel ssc = ServerSocketChannel.open();
			// 设置为非阻塞通道
			ssc.configureBlocking(false);
			// 绑定本地的端口
			ssc.bind(new InetSocketAddress(port));
			// 获得新的选择器
			selector = Selector.open();
			// 向通道注册选择器
			ssc.register(selector, SelectionKey.OP_ACCEPT);
			// 监听选择器
			listen();
		} catch (IOException e) {
			log.error("Nio Server init error", e);
		}
	}

	/**
	 * 监听事件
	 * 
	 */
	private void listen() {
		while (true) {
			try {
				int selKey = selector.select();
				if (selKey > 0) {
					Set<SelectionKey> keys = selector.selectedKeys();
					for (SelectionKey key : keys) {
						// 可接收状态
						if (key.isAcceptable()) {
							ServerSocketChannel ssc = (ServerSocketChannel) key
									.channel();
							SocketChannel sc = ssc.accept();
							if (!sc.isConnected()) {
								log.info(sc.getRemoteAddress() + ":closed");
								sc.close();
								keys.remove(key);
								continue;
							}
							sc.configureBlocking(false);
							// 在这里可以发送消息给客户端
							sc.write(ByteBuffer.wrap(new String("hello client")
									.getBytes()));
							// 在客户端 连接成功之后，为了可以接收到客户端的信息，需要给通道设置读的权限
							sc.register(this.selector, SelectionKey.OP_READ);
							// 删除已经执行过的key, 避免重复
							keys.remove(key);
						} else if (key.isReadable()) {
							read(key);
						}
					}
				}
			} catch (IOException e) {
				log.error("Nio Server Channel selector has error", e);
			}
		}
	}

	/**
	 * 读取事件处理
	 * 
	 * @param key
	 * @throws IOException
	 */
	private void read(SelectionKey key) throws IOException {
		// 服务器可读消息，得到事件发生的socket通道
		SocketChannel channel = (SocketChannel) key.channel();
		// 穿件读取的缓冲区
		ByteBuffer buffer = ByteBuffer.allocate(10);
		StringBuffer sb = new StringBuffer();
		while (channel.read(buffer) > 0) {
			buffer.flip();
			sb.append(CHARSET.decode(buffer));
			buffer.clear();
		}
		log.info("server receive from client: " + sb.toString());
		ByteBuffer outBuffer = CHARSET.encode("hello client");
		channel.write(outBuffer);
	}

	static final Charset CHARSET = Charset.forName("UTF-8");
	static Logger log = LoggerFactory.getLogger(BioServer.class);
}
