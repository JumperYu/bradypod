package com.yu.test.nio.my;

import java.nio.channels.SelectionKey;

/**
 * 非阻塞服务
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年3月16日
 */
public class NonBlockingServer extends Server {

	public NonBlockingServer(int port) throws Exception {
		super(port);
		// 非阻塞模式
		ssc.configureBlocking(false);
	}

	@Override
	public void runServer() throws Exception {
		Dispatcher dispatcher = new DispatcherImpl();
		dispatcher.register(ssc, SelectionKey.OP_ACCEPT, new AcceptHandler(ssc,
				dispatcher));
		dispatcher.run();
	}

}
