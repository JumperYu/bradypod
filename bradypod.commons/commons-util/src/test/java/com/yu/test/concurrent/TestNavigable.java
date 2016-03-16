package com.yu.test.concurrent;

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 可操作的映射表
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年3月14日
 */
public class TestNavigable {

	public static void main(String[] args) {

		ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<String, String>();
		map.put("A1001", "A1001");
		map.put("A1002", "A1002");
		map.put("A1003", "A1003");
		map.put("A1004", "A1004");
		System.out.println("first entry: " + map.firstEntry());
		System.out.println("last entry: " + map.lastEntry());
		ConcurrentNavigableMap<String, String> subMap = map.subMap("A1002",
				"A1004");
		System.out.println("sub entry: " + subMap.toString());
	}

}
