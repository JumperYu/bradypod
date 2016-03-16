package com.yu.test.nio.god;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用线程池
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年3月16日
 */
public class BlockingServer extends Server {

	public BlockingServer(int port) throws IOException {
		super(port);
		// 设置为阻塞模式
		ssc.configureBlocking(true);
	}

	@Override
	public void run() throws IOException {
		final int availableThreads = Runtime.getRuntime().availableProcessors() + 1;

		ExecutorService pool = Executors.newFixedThreadPool(availableThreads);

		for (;;) {
			SocketChannel channel = ssc.accept();
			RequestService service = new RequestService(channel);
			pool.execute(service);
		}
	}

}
