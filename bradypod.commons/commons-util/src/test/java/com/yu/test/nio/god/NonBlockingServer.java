package com.yu.test.nio.god;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * 费阻塞式服务
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年3月16日
 */
public class NonBlockingServer extends Server {

	public NonBlockingServer(int port) throws IOException {
		super(port);
		ssc.configureBlocking(false);
	}

	@Override
	public void run() throws IOException {

		Dispatcher dispatcher = new Dispatcher();
		dispatcher.register(ssc, SelectionKey.OP_ACCEPT, new AcceptHandler(ssc,
				dispatcher));
		dispatcher.run();
	}

}
