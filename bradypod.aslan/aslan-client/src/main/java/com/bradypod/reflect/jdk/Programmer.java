package com.bradypod.reflect.jdk;

/**
 * 测试类
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年2月14日
 */
public class Programmer {

	public int doCoding(String hello, String word) {
		
		if (hello.equals("hello")) {
			throw new IllegalArgumentException("hello is not accepted");
		}
		return 1;
	}

}
