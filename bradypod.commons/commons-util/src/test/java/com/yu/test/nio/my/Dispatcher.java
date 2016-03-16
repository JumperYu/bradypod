package com.yu.test.nio.my;

import java.io.IOException;
import java.nio.channels.SelectableChannel;

/**
 * 事件路由
 * 
 * @author	zengxm<http://github.com/JumperYu>
 *
 * @date	2016年3月16日
 */
public interface Dispatcher extends Runnable {

	public void register(SelectableChannel ch, int ops, Handler h)
			throws IOException;

}
