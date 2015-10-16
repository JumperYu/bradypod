package com.yu.test.redis;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bradypod.util.redis.RedisFactory;
import com.bradypod.util.redis.RedisTemplate;
import com.bradypod.util.redis.util.SerializeUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TestJson {

	private static RedisTemplate redis;

	public static void init() {
		redis = new RedisTemplate();
		RedisFactory redisFactory = new RedisFactory();
		redisFactory.setHost("redis.bradypod.com");
		redisFactory.setPort(6379);
		redis.setRedisFactory(redisFactory);
	}

	public static void main(String[] args) throws Exception {
		// 假设redis连接恒定耗时30ms偏差
		init();
		// 序列化 + redis
		// int count = 100000;
//		int time = 100;
		// testJdkSerialaize(count);
		// testGsonSerialize(count, time);
		// testJacksonSerialize(count, time);
		// testFasterJsonSerialize(count, time);

//		testJacksonDeserialize(time);
//		testGsonDeserialize(time);
//		testFasterDeserialize(time);
	}

	public static void testJacksonSerialize(int listSize, int time)
			throws Exception {
		List<TestUser> list = buildData(listSize);
		long start = System.currentTimeMillis();
		ObjectMapper mapper = new ObjectMapper();
		for (int i = 0; i < time; i++)
			mapper.writeValueAsBytes(list);
		// redis.set("jackson".getBytes(), );
		long end = System.currentTimeMillis();
		System.out.println("------------- jackson 序列化总计耗时 -------------"
				+ (end - start) / time + " 毫秒");
	}

	public static void testFasterJsonSerialize(int listSize, int time) {
		List<TestUser> list = buildData(listSize);
		long start = System.currentTimeMillis();
		for (int i = 0; i < time; i++)
			JSONObject.toJSONBytes(list);
		// redis.set("faster".getBytes(), );
		long end = System.currentTimeMillis();
		System.out.println("------------- faster 总计耗时 -------------"
				+ (end - start) / time + " 毫秒");
	}

	public static void testGsonSerialize(int listSize, int time) {
		List<TestUser> list = buildData(listSize);
		long start = System.currentTimeMillis();
		Gson gson = new Gson();
		for (int i = 0; i < time; i++)
			gson.toJson(list);
		// redis.set("gson", );
		long end = System.currentTimeMillis();
		System.out.println("-------------gson 总计耗时 -------------"
				+ (end - start) / 100 + " 毫秒");
	}

	public static void testJdkSerialaize(int listSize) {
		List<TestUser> list = buildData(listSize);
		long start = System.currentTimeMillis();
		byte[] bytes = SerializeUtil.serialize(list);
		redis.set("jdk".getBytes(), bytes);
		long end = System.currentTimeMillis();
		System.out.println("-------------jdk 总计耗时 -------------"
				+ (end - start) + " 毫秒");
	}

	public static List<TestUser> buildData(int listSize) {
		List<TestUser> list = new LinkedList<>();
		for (int i = 0; i < listSize; i++) {
			TestUser testUser = new TestUser();
			testUser.setId(RandomUtils.nextLong());
			testUser.setUserId(RandomUtils.nextLong());
			list.add(testUser);
		}
		return list;
	}

	// 反序列化
	public static void testJacksonDeserialize(int time) throws Exception {
		byte[] jack = redis.get("jackson".getBytes());
		long start = System.currentTimeMillis();
		for (int i = 0; i < time; i++) {
			new ObjectMapper().readValue(jack,
					new TypeReference<List<TestUser>>() {
					});
		}
		long end = System.currentTimeMillis();
		System.out.println("------------- jackson 反序列化总计耗时 -------------"
				+ (end - start) + " 毫秒");
	}

	public static void testFasterDeserialize(int time) throws Exception {
		byte[] faster = redis.get("faster".getBytes());
		long start = System.currentTimeMillis();
		for (int i = 0; i < time; i++) {
			JSON.parseObject(faster, new TypeReference<List<TestUser>>() {
			}.getType());
		}
		long end = System.currentTimeMillis();
		System.out.println("------------- faster 反序列化总计耗时 -------------"
				+ (end - start) + " 毫秒");
	}

	public static void testGsonDeserialize(int time) throws Exception {
		String gson = redis.getStringValue("gson");
		long start = System.currentTimeMillis();
		for (int i = 0; i < time; i++) {
			new Gson().fromJson(gson, new TypeToken<List<TestUser>>() {
			}.getType());
		}
		long end = System.currentTimeMillis();
		System.out.println("------------- gson 反序列化总计耗时 -------------"
				+ (end - start) + " 毫秒");
	}
}

class TestUser implements Serializable {

	private static final long serialVersionUID = 1L;

	public TestUser() {
	}

	public TestUser(Long id, Long userId) {
		this.id = id;
		this.userId = userId;
	}

	private Long id;
	private Long userId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "TestUser [id=" + id + ", userId=" + userId + "]";
	}

}
