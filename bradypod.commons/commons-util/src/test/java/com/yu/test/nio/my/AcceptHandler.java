package com.yu.test.nio.my;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 接收事件处理器
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年3月16日
 */
public class AcceptHandler implements Handler {

	private ServerSocketChannel channel;
	private Dispatcher dsp;

	public AcceptHandler(ServerSocketChannel ssc, Dispatcher dsp) {
		channel = ssc;
		this.dsp = dsp;
	}

	@Override
	public void handle(SelectionKey selectionKey) throws IOException {

		if (!selectionKey.isAcceptable()) {
			return;
		}

		SocketChannel sc = channel.accept();

		if (sc == null) {
			return;
		}

		ChannelIO cio = ChannelIO.getInstance(sc, false);
		
		RequestHandler requestHandler = new RequestHandler(cio);
	
		dsp.register(sc, SelectionKey.OP_READ, requestHandler);
	}

}
