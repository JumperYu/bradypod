package com.yu.test.nio.my;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * 处理器接口定义
 * 
 * @author	zengxm<http://github.com/JumperYu>
 *
 * @date	2016年3月16日
 */
public interface Handler {
	
	/**
	 * 处理那种选择器 
	 */
	public void handle(SelectionKey selectionKey) throws IOException;
	
}
