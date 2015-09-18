package com.yu.shop.json;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TestJson {

	@Test
	public void testAllJson() throws JsonParseException, JsonMappingException, IOException {
		testJackson();
		// testFastJson();
		testGson();
	}

	@Test
	public void testJackson() throws JsonParseException, JsonMappingException, IOException {
		// 做5次测试
		String json = "{1:2,3:4,5:7}";
		for (int i = 0, j = 5; i < j; i++) {
			ObjectMapper objectMapper = new ObjectMapper();
			@SuppressWarnings("unchecked")
			Map<Object, Object> entry = objectMapper.readValue(json, Map.class);
			for (Object key : entry.keySet()) {
				System.out.print(key + "-" + entry.get(key) + "\t");
			}
			System.out.println();// 用来换行
		}
		System.out.println("--------------------------JackSon---------------------------------");
	}

	@Test
	public void testFastJson() {
		String json = "{\"张学友\":\"JACKIE_ZHANG\",\"ANDY_LAU\":\"刘德华\",\"LIMING\":\"黎明\",\"Aaron_Kwok\":\"郭富城\"}";
		// String json = "{1:2,3:4,5:7}"; 不能
		// 做5次测试
		for (int i = 0, j = 5; i < j; i++) {
			JSONObject jsonObject = JSONObject.parseObject(json);
			for (java.util.Map.Entry<String, Object> entry : jsonObject.entrySet()) {
				System.out.print(entry.getKey() + "-" + entry.getValue() + "\t");
			}
			System.out.println();// 用来换行
		}
		System.out.println("--------------------------FastJson---------------------------------");
	}

	@Test
	public void testGson() {
		String json = "{\"张学友\":\"JACKIE_ZHANG\",\"ANDY_LAU\":\"刘德华\",\"LIMING\":\"黎明\",\"Aaron_Kwok\":\"郭富城\"}";
		// String json = "{1:2,3:1,111111:7}";
		// 做5次测试
		for (int i = 0, j = 5; i < j; i++) {
			JsonObject jsonObject = (JsonObject) new JsonParser().parse(json);
			for (java.util.Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
				System.out.print(entry.getKey() + "-" + entry.getValue() + "\t");
			}
			System.out.println();// 用来换行
		}
		System.out.println("--------------------------GSON---------------------------------");
		
		JsonObject nameJson = new JsonObject();
		nameJson.addProperty("黎明", "JACKIE_ZHANG");
		nameJson.addProperty("张学友", "JACKIE_ZHANG");
		nameJson.addProperty("刘德华", "JACKIE_ZHANG");
		nameJson.addProperty("郭富城", "JACKIE_ZHANG");
		
		System.out.println(nameJson.toString());
		
		JsonObject numJson = new JsonObject();
		numJson.addProperty("1", 1L);
		numJson.addProperty("4", 1L);
		numJson.addProperty("2", 1L);
		numJson.addProperty("3", 1L);
		
		System.out.println(numJson.toString().replaceAll("\"", ""));
	}

}
