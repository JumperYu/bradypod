package com.yu.test.nio.god;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class AcceptHandler implements Handler {

	Dispatcher dispatcher;
	ServerSocketChannel ssc;

	public AcceptHandler(ServerSocketChannel ssc, Dispatcher dispatcher) {
		this.ssc = ssc;
		this.dispatcher = dispatcher;
	}

	@Override
	public void handle(SelectionKey sk) throws IOException {
		if (!sk.isAcceptable()) {
			return;
		}

		final SocketChannel channel = ssc.accept();
		if (channel == null) {
			return;
		}
		channel.configureBlocking(false);

		dispatcher.register(channel, SelectionKey.OP_READ, new RequestHandler(
				channel));
	}

}
