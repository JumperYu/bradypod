package com.bradypod.reflect.jdk;

import java.util.concurrent.TimeUnit;

/**
 * 测试类
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年2月14日
 */
public class Programmer {

	public void doCoding(String word, long sleep, TimeUnit timeUnit) {
//		System.out.println(word);
//		System.out.println(sleep);
//		System.out.println(timeUnit);
		System.out.println("I'm say:'" + word + "'");
		try {
			timeUnit.sleep(sleep);
		} catch (InterruptedException e) {
		}
//		return 10;
	}

}
