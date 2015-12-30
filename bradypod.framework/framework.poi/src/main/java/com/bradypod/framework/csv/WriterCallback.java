package com.bradypod.framework.csv;

/**
 * 回调
 *
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2015年12月30日 上午10:29:40
 */
public interface WriterCallback {

	public void withWriter(SimpleCSVWriter writer) throws Exception;
}
