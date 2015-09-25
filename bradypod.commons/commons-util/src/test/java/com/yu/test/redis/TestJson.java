package com.yu.test.redis;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;

import redis.clients.jedis.JedisPool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bradypod.util.redis.RedisImpl;
import com.bradypod.util.redis.util.SerializeUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yu.util.json.JacksonUtil;

public class TestJson {

	private static RedisImpl redis;

	public static void init() {
		// JedisPool pool = new JedisPool("redis.bradypod.com", 6379);
		JedisPool pool = new JedisPool("192.168.1.201", 7001);
		redis = new RedisImpl();
		redis.setPool(pool);
	}

	public static void main(String[] args) throws Exception {
		// 假设redis连接恒定耗时30ms偏差
		init();
		// 序列化 + redis
//		int count = 1000;
//		testJdkSerialaize(count);
//		testJacksonSerialize(count);
//		testGsonSerialize(count);
//		testFasterJsonSerialize(count);

		// testJacksonDeserialize();
		// testFasterDeserialize();
		// testGsonDeserialize();
	}

	public static void testJacksonSerialize(int listSize) throws Exception {
		long start = System.currentTimeMillis();
		String json = "";
		List<TestUser> list = new LinkedList<>();
		for (int i = 0; i < listSize; i++) {
			TestUser testUser = new TestUser();
			testUser.setId(RandomUtils.nextLong());
			testUser.setUserId(RandomUtils.nextLong());
			list.add(testUser);
		}
		json = JacksonUtil.beanToJson(list);
		redis.set("jackson", json);
		long end = System.currentTimeMillis();
		System.out.println("字符串原长度:" + json.length());
		System.out.println("------------- jackson 总计耗时 -------------" + (end - start) + " 毫秒");
	}

	public static void testFasterJsonSerialize(int listSize) {
		long start = System.currentTimeMillis();
		String json = "";
		List<TestUser> list = new LinkedList<>();
		for (int i = 0; i < listSize; i++) {
			TestUser testUser = new TestUser();
			testUser.setId(RandomUtils.nextLong());
			testUser.setUserId(RandomUtils.nextLong());
			list.add(testUser);
		}
		json = JSONObject.toJSONString(list);
		redis.set("faster", json);
		long end = System.currentTimeMillis();
		System.out.println("字符串原长度:" + json.length());
		System.out.println("------------- faster 总计耗时 -------------" + (end - start) + " 毫秒");
	}

	public static void testGsonSerialize(int listSize) {
		long start = System.currentTimeMillis();
		String json = "";
		Gson gson = new Gson();
		List<TestUser> list = new LinkedList<>();
		for (int i = 0; i < listSize; i++) {
			TestUser testUser = new TestUser();
			testUser.setId(RandomUtils.nextLong());
			testUser.setUserId(RandomUtils.nextLong());
			list.add(testUser);
		}
		json = gson.toJson(list);
		redis.set("gson", json);
		long end = System.currentTimeMillis();
		System.out.println("字符串原长度:" + json.length());
		System.out.println("-------------gson 总计耗时 -------------" + (end - start) + " 毫秒");
	}

	public static void testJdkSerialaize(int listSize) {
		long start = System.currentTimeMillis();
		List<TestUser> list = new LinkedList<>();
		for (int i = 0; i < listSize; i++) {
			TestUser testUser = new TestUser();
			testUser.setId(RandomUtils.nextLong());
			testUser.setUserId(RandomUtils.nextLong());
			list.add(testUser);
		}
		byte[] bytes = SerializeUtil.serialize(list);
		redis.set("gson".getBytes(), bytes);
		long end = System.currentTimeMillis();
		System.out.println("-------------jdk 总计耗时 -------------" + (end - start) + " 毫秒");
	}

	// 反序列化
	public static void testJacksonDeserialize() throws Exception {
		long start = System.currentTimeMillis();
		String jack = redis.get("jackson");
		@SuppressWarnings("rawtypes")
		List object = JacksonUtil.jsonToBean(jack, List.class);
		System.out.println(object.size());
		long end = System.currentTimeMillis();
		System.out.println("------------- jackson 总计耗时 -------------" + (end - start) + " 毫秒");
	}

	public static void testFasterDeserialize() throws Exception {
		long start = System.currentTimeMillis();
		String jack = redis.get("jackson");
		// @SuppressWarnings("rawtypes") new
		List<TestUser> object = JSON.parseObject(jack, new TypeToken<List<TestUser>>() {
		}.getType());
		System.out.println(object.size());
		long end = System.currentTimeMillis();
		System.out.println("------------- jackson 总计耗时 -------------" + (end - start) + " 毫秒");
	}

	public static void testGsonDeserialize() throws Exception {
		long start = System.currentTimeMillis();
		String jack = redis.get("jackson");
		// @SuppressWarnings("rawtypes") new
		List<TestUser> object = new Gson().fromJson(jack, new TypeToken<List<TestUser>>() {
		}.getType());
		System.out.println(object.size());
		long end = System.currentTimeMillis();
		System.out.println("------------- jackson 总计耗时 -------------" + (end - start) + " 毫秒");
	}
}

class TestUser implements Serializable {

	private static final long serialVersionUID = 1L;

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

}
