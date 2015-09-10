package com.yu.test.json;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.yu.sys.po.Task;
import com.yu.util.json.JacksonUtil;

public class JacksonUtilTest {

	@SuppressWarnings("unchecked")
	@Test
	public void testJackson() throws Exception {
		Task task = new Task();
		task.setStartTime(new Date());
		task.setEndTime(new Date());
		List<Task> tasks = new ArrayList<>();
		tasks.add(task);
		String json = JacksonUtil.beanToJson(tasks);
		tasks = (List<Task>) JacksonUtil.jsonToBean(json, List.class);
		System.out.println(tasks);
	}

}
