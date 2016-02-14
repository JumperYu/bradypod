package com.yu.test.guawa;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 测试google的简易工具类
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年1月24日
 */
public class GuawaTest {

	@Test
	public void testList() {
		// immutable
		ImmutableList<String> immutableList = ImmutableList.of("a", "b", "c");
		for (String tmp : immutableList) {
			System.out.println(tmp);
		}
		// new
		List<String> newlist = Lists.newArrayList("A", "B", "C");
		immutableList = ImmutableList.copyOf(newlist);
		for (String tmp : immutableList) {
			System.out.println(tmp);
		}
		// asList
	}

	@Test
	public void testMap() {
		Map<String, Object> map = Maps.newHashMap();
		map.put("A", "B");
		ImmutableMap<String, Object> immutableMap = ImmutableMap.of("A", "B",
				"C", "D");
		for (Entry<String, Object> entry : immutableMap.entrySet()) {
			System.out.println(entry.getKey() + "," + entry.getValue());
		}
	}
}
