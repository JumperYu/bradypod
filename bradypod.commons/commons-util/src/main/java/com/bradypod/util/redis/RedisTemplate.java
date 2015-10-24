package com.bradypod.util.redis;

import java.util.Collection;
import java.util.Map;

import redis.clients.jedis.ShardedJedis;

import com.bradypod.util.redis.invoker.ShardedRedisCallback;
import com.bradypod.util.redis.serializer.JacksonRedisSerializer;
import com.bradypod.util.redis.serializer.JdkRedisSerializer;
import com.bradypod.util.redis.serializer.NumberRedisSerializer;
import com.bradypod.util.redis.serializer.StringRedisSerializer;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * Redis操作模板
 *
 * @author zengxm
 * @date 2015年9月27日
 *
 */
public class RedisTemplate {

	RedisFactory redisFactory; // 由工场生成redis连接

	// 各种类型的序列化工具
	NumberRedisSerializer numberRedisSerializer = new NumberRedisSerializer();

	StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

	JdkRedisSerializer jdkRedisSerializer = new JdkRedisSerializer();

	JacksonRedisSerializer jacksonRedisSerializer = new JacksonRedisSerializer();

	/* 处理回调 */
	protected <T> T execute(final ShardedRedisCallback<T> callback) {
		if (redisFactory.getShardedJedisPool() == null) {
			redisFactory.createShardedJedisPool();
		}
		try (ShardedJedis shardedJedis = redisFactory.getShardedJedisPool()
				.getResource()) {
			return callback.execute(shardedJedis);
		}
	}

	// TODO set方法统一先不处理返回值, 并假设都能成功

	/**
	 * 普通Number类型的序列化
	 * 
	 * @param key
	 * @param value
	 */
	public void set(final String key, final Number value) {
		set(stringRedisSerializer.serialize(key),
				numberRedisSerializer.serialize(value));
	}

	/**
	 * 字符串的字节转换
	 * 
	 * @param key
	 * @param value
	 */
	public void set(final String key, final String value) {
		set(stringRedisSerializer.serialize(key),
				stringRedisSerializer.serialize(value));
	}

	/**
	 * jdk序列化对于复杂对象, 比较耗时
	 * 
	 * @param key
	 * @param collection
	 */
	public void set(final String key, final Collection<?> value) {
		set(stringRedisSerializer.serialize(key),
				jacksonRedisSerializer.serialize(value));
	}

	public void set(final String key, final Map<?, ?> value) {
		set(stringRedisSerializer.serialize(key),
				jacksonRedisSerializer.serialize(value));
	}

	/**
	 * 普通对象的序列化, jdk较优秀
	 * 
	 * @param key
	 * @param value
	 */
	public void set(final String key, final Object value) {
		// jdkRedisSerializer.serialize(value)
		set(stringRedisSerializer.serialize(key),
				jacksonRedisSerializer.serialize(value));
	}

	/**
	 * 哈希表操作
	 * 
	 * @param key
	 *            - redis hashmap key
	 * @param field
	 *            - map key
	 * @param value
	 *            - map value
	 */
	public void hset(final String key, final String field, final String value) {
		hset(stringRedisSerializer.serialize(key),
				stringRedisSerializer.serialize(field),
				stringRedisSerializer.serialize(value));
	}

	public void hset(final String key, final String field, final Number value) {
		hset(stringRedisSerializer.serialize(key),
				stringRedisSerializer.serialize(field),
				numberRedisSerializer.serialize(value));
	}

	public void hset(final String key, final String field,
			final Collection<?> value) {
		hset(stringRedisSerializer.serialize(key),
				stringRedisSerializer.serialize(field),
				jacksonRedisSerializer.serialize(value));
	}

	public void hset(final String key, final String field, final Map<?, ?> value) {
		hset(stringRedisSerializer.serialize(key),
				stringRedisSerializer.serialize(field),
				jacksonRedisSerializer.serialize(value));
	}

	public void hset(final String key, final String field, final Object value) {
		hset(stringRedisSerializer.serialize(key),
				stringRedisSerializer.serialize(field),
				jacksonRedisSerializer.serialize(value));
	}

	/**
	 * 字节存储, 节省内存
	 * 
	 * @param key
	 * @param value
	 */
	public void set(final byte[] key, final byte[] value) {
		execute(new ShardedRedisCallback<String>() {

			@Override
			public String execute(ShardedJedis shardedJedis) {
				return shardedJedis.set(key, value);
			}
		});
	}

	public void hset(final byte[] key, final byte[] field, final byte[] value) {
		execute(new ShardedRedisCallback<Long>() {

			@Override
			public Long execute(ShardedJedis shardedJedis) {
				return shardedJedis.hset(key, field, value);
			}
		});
	}

	// TODO get方法, 需要不断完善

	public String getStringValue(final String key) {
		return stringRedisSerializer.deserialize(get(stringRedisSerializer
				.serialize(key)));
	}

	// Number类型的序列化
	public Long getLongValue(final String key) {
		return numberRedisSerializer.deserializeLong(get(stringRedisSerializer
				.serialize(key)));
	}

	public Integer getIntegerValue(final String key) {
		return numberRedisSerializer
				.deserializeInteger(get(stringRedisSerializer.serialize(key)));
	}

	public Byte getByteValue(final String key) {
		return numberRedisSerializer.deserializeByte(get(stringRedisSerializer
				.serialize(key)));
	}

	public Float getFloatValue(final String key) {
		return numberRedisSerializer.deserializeFloat(get(stringRedisSerializer
				.serialize(key)));
	}

	public Double getDoubleValue(final String key) {
		return numberRedisSerializer
				.deserializeDouble(get(stringRedisSerializer.serialize(key)));
	}

	/**
	 * 可以指定类型转换, 默认使用的Jackson序列化
	 * 
	 * @param key
	 *            - redis key
	 * @param clazz
	 *            - 需要转化的类型
	 * @return
	 */
	public <T> T getObject(final String key, final Class<T> clazz) {
		return jacksonRedisSerializer.deserialize(
				get(stringRedisSerializer.serialize(key)), clazz);
	}

	/**
	 * 指定类型获取, 支持泛型
	 * 
	 * @param key
	 *            - redis key
	 * @param typeReference
	 *            - 透传泛型
	 * @return
	 */
	public <T> T getObject(final String key,
			final TypeReference<T> typeReference) {
		return jacksonRedisSerializer.deserialize(
				get(stringRedisSerializer.serialize(key)), typeReference);
	}

	/**
	 * 不要使用这个方法
	 */
	@SuppressWarnings("unchecked")
	@Deprecated
	public <T> T getObject(final String key) {
		return (T) jdkRedisSerializer.deserialize(get(stringRedisSerializer
				.serialize(key)));
	}

	/**
	 * 哈希获取
	 */

	public <T> T hgetObject(final String key, final String field, final Class<T> clazz) {
		return jacksonRedisSerializer.deserialize(
				hget(stringRedisSerializer.serialize(key),
						stringRedisSerializer.serialize(field)), clazz);
	}

	public <T> T hgetObject(final String key, final String field,
			final TypeReference<T> typeReference) {
		return jacksonRedisSerializer.deserialize(
				hget(stringRedisSerializer.serialize(key),
						stringRedisSerializer.serialize(field)), typeReference);
	}

	/**
	 * 不要使用这个方法了 <br/>
	 * 
	 * 使用 :  <br/> 
	 * hget(final String key, final String field, final TypeReference<T> typeReference)  <br/>
	 * hget(final String key, final String field, final Class<T> clazz) <br/>
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public <T> T hgetObject(final String key, final String field) {
		return (T) jdkRedisSerializer.deserialize(hget(
				stringRedisSerializer.serialize(key),
				stringRedisSerializer.serialize(field)));
	}

	public String hgetString(final String key, final String field) {
		return stringRedisSerializer.deserialize(hget(
				stringRedisSerializer.serialize(key),
				stringRedisSerializer.serialize(field)));
	}

	public Long hgetLong(final String key, final String field) {
		return numberRedisSerializer.deserializeLong(hget(
				stringRedisSerializer.serialize(key),
				stringRedisSerializer.serialize(field)));
	}

	public Integer hgetInteger(final String key, final String field) {
		return numberRedisSerializer.deserializeInteger(hget(
				stringRedisSerializer.serialize(key),
				stringRedisSerializer.serialize(field)));
	}

	public Float hgetFloat(final String key, final String field) {
		return numberRedisSerializer.deserializeFloat(hget(
				stringRedisSerializer.serialize(key),
				stringRedisSerializer.serialize(field)));
	}

	public Double hgetDouble(final String key, final String field) {
		return numberRedisSerializer.deserializeDouble(hget(
				stringRedisSerializer.serialize(key),
				stringRedisSerializer.serialize(field)));
	}

	public Byte hgetByte(final String key, final String field) {
		return numberRedisSerializer.deserializeByte(hget(
				stringRedisSerializer.serialize(key),
				stringRedisSerializer.serialize(field)));
	}

	/**
	 * 按字节key获取原始字节类型的值
	 * 
	 * @param key
	 *            - 字节key
	 * @return bytes - 为转换的字节值
	 */
	public byte[] get(final byte[] key) {
		return execute(new ShardedRedisCallback<byte[]>() {

			@Override
			public byte[] execute(ShardedJedis shardedJedis) {
				return shardedJedis.get(key);
			}
		});
	}

	public byte[] hget(final byte[] key, final byte[] field) {
		return execute(new ShardedRedisCallback<byte[]>() {

			@Override
			public byte[] execute(ShardedJedis shardedJedis) {
				return shardedJedis.hget(key, field);
			}
		});
	}

	/**
	 * 删除缓存, 先不处理返回值
	 */
	public void delete(final String key) {
		execute(new ShardedRedisCallback<Long>() {

			@Override
			public Long execute(ShardedJedis shardedJedis) {
				return shardedJedis.del(key);
			}
		});
	}

	/* set */

	public void setRedisFactory(RedisFactory redisFactory) {
		this.redisFactory = redisFactory;
	}
	
}
