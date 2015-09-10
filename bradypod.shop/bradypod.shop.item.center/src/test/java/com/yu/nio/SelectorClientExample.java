package com.yu.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
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
public class SelectorClientExample {

	public static void main(String[] args) {
		new SelectorClientExample().initClient(8008);
	}

	// 通道管理器
	private Selector selector;

	public void initClient(int port) {
		try {
			SocketChannel sc = SocketChannel.open();
			sc.configureBlocking(false);
			sc.connect(new InetSocketAddress(port));
			selector = Selector.open();
			sc.register(selector, SelectionKey.OP_CONNECT);
			listen();
		} catch (IOException e) {
			log.error("nio client init error", e);
		}
	}

	private void listen() throws IOException {
		while (true) {
			int selKey = selector.select();
			if (selKey > 0) {
				Set<SelectionKey> keys = selector.selectedKeys();
				for (SelectionKey key : keys) {
					if (key.isConnectable()) {
						// 可接收状态
						SocketChannel sc = (SocketChannel) key.channel();
						if (sc.isConnectionPending()) {
							sc.finishConnect();
						}
						if (!sc.isConnected()) {
							log.info(sc.getRemoteAddress() + ":closed");
							sc.close();
							keys.remove(key);
							continue;
						}
						sc.configureBlocking(false);
						// 在这里可以发送消息给客户端
						sc.write(ByteBuffer.wrap(new String("hello server")
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
		}
	}

	private void read(SelectionKey key) throws IOException {
		SocketChannel channel = (SocketChannel) key.channel();
		// 穿件读取的缓冲区
		ByteBuffer buffer = ByteBuffer.allocate(10);
		StringBuffer sb = new StringBuffer();
		while (channel.read(buffer) > 0) {
			buffer.flip();
			sb.append(CHARSET.decode(buffer));
			buffer.clear();
		}
		log.info("client receive msg:" + sb.toString());
		ByteBuffer outBuffer = CHARSET.encode("hello server");
		channel.write(outBuffer);

	}

	static final Charset CHARSET = Charset.forName("UTF-8");
	static Logger log = LoggerFactory.getLogger(BioServer.class);
}
