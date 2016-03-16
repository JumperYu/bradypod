package com.yu.test.nio.god;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

/**
 * 路由
 * 
 * @author	zengxm<http://github.com/JumperYu>
 *
 * @date	2016年3月16日
 */
public class Dispatcher implements Runnable {

	Selector selector;

	public Dispatcher() throws IOException {
		this.selector = Selector.open();
	}

	public void run() {
		try {
			for (;;) {
				dispatch();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void register(SelectableChannel channel, int ops, Handler handler)
			throws ClosedChannelException {
		channel.register(selector, ops, handler);
	}

	private void dispatch() throws IOException {

		selector.select();

		for (Iterator<SelectionKey> i = selector.selectedKeys().iterator(); i
				.hasNext();) {
			SelectionKey sk = (SelectionKey) i.next();
			i.remove();
			Handler handler = (Handler) sk.attachment();
			handler.handle(sk);
		}
	};

}
