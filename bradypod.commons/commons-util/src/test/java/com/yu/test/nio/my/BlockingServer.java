package com.yu.test.nio.my;

import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * NIO的阻塞模式
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年2月24日
 */
public class BlockingServer extends Server {

	static AtomicInteger clients = new AtomicInteger(1);

	public BlockingServer(int port) throws Exception {
		super(port);
	}

	@Override
	public void runServer() throws Exception {
		for (;;) {
			SocketChannel sc = ssc.accept();
			// 阻塞
			ChannelIO channelIO = ChannelIO.getInstance(sc, true);
			channelIO.notify();
		}
	}

}
