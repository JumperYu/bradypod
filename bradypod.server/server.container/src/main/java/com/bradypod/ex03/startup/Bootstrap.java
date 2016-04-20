package com.bradypod.ex03.startup;

import com.bradypod.ex03.connector.http.Container;
import com.bradypod.ex03.connector.http.HttpConnector;
import com.bradypod.ex03.connector.http.SimpleContainer;

/**
 * 启动脚本
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年3月23日
 */
public class Bootstrap {

	public static void main(String[] args) {
		HttpConnector connector = new HttpConnector(8080);
		Container container = new SimpleContainer();
		connector.setContainer(container);
		try {
			connector.initialize();
			connector.start();

			// wait until user press any key
			System.in.read();
		} catch (Exception e) {
			System.exit(-1);
		}
	}

}
