package com.bradypod.ex03.startup;

import com.bradypod.ex03.connector.http.HttpConnector;

/**
 * 启动脚本
 * 
 * @author	zengxm<http://github.com/JumperYu>
 *
 * @date	2016年3月23日
 */
public class Bootstrap {
	
	public static void main(String[] args) {
		HttpConnector connector = new HttpConnector();
		connector.start();
	}
	
}
