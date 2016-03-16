package com.yu.test.nio.my;

import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * NIO的阻塞模式
 * 
 * 阻塞模式有三种, 一种是单线程，第二种是每一个客户端一个线程， 第三种线程池
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年2月24日
 */
public class BlockingServer extends Server {

	static AtomicInteger clients = new AtomicInteger(1);

	static final int POOL_SIZE = 4;

	public BlockingServer(int port) throws Exception {
		super(port);
	}

	@Override
	public void runServer() throws Exception {

		int availableThreads = Runtime.getRuntime().availableProcessors()
				* POOL_SIZE;

		ExecutorService pool = Executors.newFixedThreadPool(availableThreads);

		for (;;) {
			SocketChannel sc = ssc.accept();

			System.err.printf("Server: %d client connecting\n",
					clients.getAndIncrement());

			// 阻塞模式
			ChannelIO channelIO = ChannelIO.getInstance(sc, true);

			// 处理业务
			RequestService requestService = new RequestService(channelIO);

			pool.execute(requestService);
		}
	}

}
