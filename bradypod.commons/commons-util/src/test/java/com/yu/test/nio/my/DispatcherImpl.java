package com.yu.test.nio.my;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

public class DispatcherImpl implements Dispatcher {

	private Selector selector;

	public DispatcherImpl() throws IOException {
		selector = Selector.open();
	}

	@Override
	public void run() {
		for (;;) {
			try {
				dispatch();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void register(SelectableChannel ch, int ops, Handler h)
			throws IOException {
		// 注册事件和处理器
		ch.register(selector, ops, h);	
	}

	public void dispatch() throws IOException {
		selector.select();
		for (Iterator<SelectionKey> i = selector.selectedKeys().iterator(); i
				.hasNext();) {
			SelectionKey sk = (SelectionKey) i.next();
			i.remove();
			Handler h = (Handler) sk.attachment();
			h.handle(sk);
		}
	}

}
