package com.yu.test.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.junit.Test;

public class TestShard {

	// 测试哈希一致性不会因为传入的条件增多而影响之前的结果
	@Test
	public void testHashNotChange() {

		// 外部因素
		List<String> shards = new ArrayList<>();
		shards.add("192.168.1.1");
		shards.add("192.168.1.2");

		for (int i = 0; i != shards.size(); ++i) {
		}
	}

	// 树形
	@Test
	public void testMap() {

		TreeMap<Integer, String> nodes = new TreeMap<>();
		nodes.put(1, "123");
		nodes.put(2, "123");
		nodes.put(3, "123");
		nodes.put(4, "123");
		nodes.put(5, "123");
		nodes.put(6, "123");
		nodes.put(7, "123");

		System.out.println(nodes.tailMap(4));
	}
}
