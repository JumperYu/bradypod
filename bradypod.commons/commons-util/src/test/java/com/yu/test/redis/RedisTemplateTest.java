package com.yu.test.redis;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.bradypod.util.redis.RedisFactory;
import com.bradypod.util.redis.RedisTemplate;
import com.fasterxml.jackson.core.type.TypeReference;

public class RedisTemplateTest {

	RedisTemplate redisTemplate;

	@Before
	public void init() {
		redisTemplate = new RedisTemplate();
		RedisFactory redisFactory = new RedisFactory();
		redisFactory.setHosts(new String[] { "192.168.1.201", "192.168.1.201", "192.168.1.201" });
		redisFactory.setPorts(new int[] { 7001, 7002, 7003, 7004 });
		redisFactory.setPasswords(new String[] { "", "", "" });
		redisTemplate.setRedisFactory(redisFactory);
	}

	@Test
	public void testPubSub() {
		
	}

	@Test
	public void testNumber() {
		redisTemplate.set("my-num-int", Integer.valueOf(123));
		redisTemplate.set("my-num-long", Long.valueOf(123));
		redisTemplate.set("my-num-float", Float.valueOf(1.1f));
		redisTemplate.set("my-num-double", Double.valueOf(2.23));
		redisTemplate.set("my-num-byte", 0xA);
		System.out.println(redisTemplate.getIntegerValue("my-num-int"));
		System.out.println(redisTemplate.getLongValue("my-num-long"));
		System.out.println(redisTemplate.getFloatValue("my-num-float"));
		System.out.println(redisTemplate.getDoubleValue("my-num-double"));
		System.out.println(redisTemplate.getByteValue("my-num-byte"));
	}

	@Test
	public void testString() {
		// 对比字节长度
		redisTemplate.set("my-string", "中文");
		redisTemplate.set("my-string-object", (Object) "中文");
		System.out.println(redisTemplate.getStringValue("my-string"));
	}

	@Test
	public void testList() {
		List<TestUser> list = new ArrayList<TestUser>();
		list.add(new TestUser(1L, 2L));
		list.add(new TestUser(1L, 2L));
		list.add(new TestUser(1L, 2L));
		list.add(new TestUser(1L, 2L));
		list.add(new TestUser(1L, 2L));
		redisTemplate.set("my-list", list);
		redisTemplate.set("my-list", (Object) list);
	}

	@Test
	public void testHash() {
		redisTemplate.hset("my-hash", "key-1", "字符");
		redisTemplate.hset("my-hash", "key-2", Integer.valueOf(123));
		redisTemplate.hset("my-hash", "key-3", Long.valueOf(123));
		redisTemplate.hset("my-hash", "key-4", Float.valueOf(1.1f));
		redisTemplate.hset("my-hash", "key-5", Double.valueOf(2.23));
		redisTemplate.hset("my-hash", "key-6", Byte.valueOf("1"));
		redisTemplate.hset("my-hash", "key-7", new TestUser(1L, 1L));
		List<TestUser> list = new ArrayList<TestUser>();
		list.add(new TestUser(1L, 2L));
		list.add(new TestUser(1L, 2L));
		list.add(new TestUser(1L, 2L));
		list.add(new TestUser(1L, 2L));
		list.add(new TestUser(1L, 2L));
		redisTemplate.hset("my-hash", "key-8", list);
		System.out.println(redisTemplate.hgetString("my-hash", "key-1"));
		System.out.println(redisTemplate.hgetInteger("my-hash", "key-2"));
		System.out.println(redisTemplate.hgetLong("my-hash", "key-3"));
		System.out.println(redisTemplate.hgetFloat("my-hash", "key-4"));
		System.out.println(redisTemplate.hgetDouble("my-hash", "key-5"));
		System.out.println(redisTemplate.hgetByte("my-hash", "key-6"));
		System.out.println(redisTemplate.hgetObject("my-hash", "key-8",
				new TypeReference<List<TestUser>>() {
				}));
	}

	public void testSpringRedis() {
		/*
		 * JedisConnectionFactory connectionFactory = new
		 * JedisConnectionFactory(); RedisTemplate<String, Object> template =
		 * new RedisTemplate<>();
		 * template.setConnectionFactory(connectionFactory);
		 * template.setValueSerializer(null);
		 */
	}

}
