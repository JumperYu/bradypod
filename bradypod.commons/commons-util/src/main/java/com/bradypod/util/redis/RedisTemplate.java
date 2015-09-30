package com.bradypod.util.redis;

import java.util.Collection;
import java.util.Map;

import redis.clients.jedis.ShardedJedis;

import com.bradypod.util.redis.invoker.ShardedRedisCallback;
import com.bradypod.util.redis.serializer.JacksonRedisSerializer;
import com.bradypod.util.redis.serializer.JdkRedisSerializer;
import com.bradypod.util.redis.serializer.NumberRedisSerializer;
import com.bradypod.util.redis.serializer.RedisSerializer;
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

	RedisFactory redisFactory;

	RedisSerializer<String> serializer;

	// 各种类型的序列化工具
	NumberRedisSerializer numberRedisSerializer = new NumberRedisSerializer();

	StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

	JdkRedisSerializer jdkRedisSerializer = new JdkRedisSerializer();

	JacksonRedisSerializer jacksonRedisSerializer = new JacksonRedisSerializer();

	protected <T> T execute(final ShardedRedisCallback<T> callback) {
		if (redisFactory.getShardedJedisPool() == null) {
			redisFactory.createShardedJedisPool();
		}
		try (ShardedJedis shardedJedis = redisFactory.getShardedJedisPool().getResource()) {
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
		set(stringRedisSerializer.serialize(key), numberRedisSerializer.serialize(value));
	}

	/**
	 * 字符串的字节转换
	 * 
	 * @param key
	 * @param value
	 */
	public void set(final String key, final String value) {
		set(stringRedisSerializer.serialize(key), stringRedisSerializer.serialize(value));
	}

	/**
	 * jdk序列化对于复杂对象, 比较耗时
	 * 
	 * @param key
	 * @param collection
	 */
	public void set(final String key, final Collection<?> value) {
		set(stringRedisSerializer.serialize(key), jacksonRedisSerializer.serialize(value));
	}

	public void set(final String key, final Map<?, ?> value) {
		set(stringRedisSerializer.serialize(key), jacksonRedisSerializer.serialize(value));
	}

	/**
	 * 普通对象的序列化, jdk较优秀
	 * 
	 * @param key
	 * @param value
	 */
	public void set(final String key, final Object value) {
		set(stringRedisSerializer.serialize(key), jdkRedisSerializer.serialize(value));
	}

	/**
	 * hashset
	 * 
	 * @param key
	 * @param field
	 * @param value
	 */
	public void hset(final String key, final String field, final Number value) {
		hset(stringRedisSerializer.serialize(key), stringRedisSerializer.serialize(field),
				numberRedisSerializer.serialize(value));
	}

	public void hset(final String key, final String field, final Collection<?> value) {
		hset(stringRedisSerializer.serialize(key), stringRedisSerializer.serialize(field),
				jacksonRedisSerializer.serialize(value));
	}

	public void hset(final String key, final String field, final Map<?, ?> value) {
		hset(stringRedisSerializer.serialize(key), stringRedisSerializer.serialize(field),
				jacksonRedisSerializer.serialize(value));
	}

	public void hset(final String key, final String field, final Object value) {
		hset(stringRedisSerializer.serialize(key), stringRedisSerializer.serialize(field),
				jdkRedisSerializer.serialize(value));
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

	// TODO get方法, 扩展类型

	/**
	 * 获取
	 * 
	 * @param key
	 * @return
	 */
	public String getStringValue(final String key) {
		return execute(new ShardedRedisCallback<String>() {

			@Override
			public String execute(ShardedJedis shardedJedis) {
				return shardedJedis.get(key);
			}
		});
	}

	public Number getNumberValue(final String key) {
		return execute(new ShardedRedisCallback<Number>() {

			@Override
			public Number execute(ShardedJedis shardedJedis) {
				byte[] bytes = shardedJedis.get(stringRedisSerializer.serialize(key));
				if (bytes != null)
					return numberRedisSerializer.deserialize(bytes);
				else
					return null;
			}
		});
	}

	// TODO 待定方法
	public <T> T getObject(final String key) {
		return execute(new ShardedRedisCallback<T>() {

			@SuppressWarnings("unchecked")
			@Override
			public T execute(ShardedJedis shardedJedis) {
				byte[] bytes = shardedJedis.get(stringRedisSerializer.serialize(key));
				if (bytes != null)
					return (T) jdkRedisSerializer.deserialize(bytes);
				else
					return null;
			}
		});
	}

	// 默认使用的Jackson序列化
	public <T> T getObject(final String key, final Class<T> clazz) {
		return execute(new ShardedRedisCallback<T>() {

			@Override
			public T execute(ShardedJedis shardedJedis) {
				byte[] bytes = shardedJedis.get(stringRedisSerializer.serialize(key));
				if (bytes != null)
					return jacksonRedisSerializer.deserialize(bytes, clazz);
				else
					return null;
			}
		});
	}

	public <T> T getObject(final String key, final TypeReference<T> typeReference) {
		return execute(new ShardedRedisCallback<T>() {

			@Override
			public T execute(ShardedJedis shardedJedis) {
				byte[] bytes = shardedJedis.get(stringRedisSerializer.serialize(key));
				if (bytes != null)
					return jacksonRedisSerializer.deserialize(bytes, typeReference);
				else
					return null;
			}
		});
	}

	/**
	 * 哈希获取
	 * 
	 * @param key
	 * @param field
	 * @param typeReference
	 * @return
	 */
	public <T> T hget(final String key, final String field, final TypeReference<T> typeReference) {
		return execute(new ShardedRedisCallback<T>() {

			@Override
			public T execute(ShardedJedis shardedJedis) {
				byte[] bytes = shardedJedis.hget(stringRedisSerializer.serialize(key),
						stringRedisSerializer.serialize(field));
				if (bytes != null)
					return jacksonRedisSerializer.deserialize(bytes, typeReference);
				else
					return null;
			}
		});
	}

	public <T> T hgetObject(final String key, final String field) {
		return execute(new ShardedRedisCallback<T>() {

			@SuppressWarnings("unchecked")
			@Override
			public T execute(ShardedJedis shardedJedis) {
				byte[] bytes = shardedJedis.hget(stringRedisSerializer.serialize(key),
						stringRedisSerializer.serialize(field));
				if (bytes != null)
					return (T) jdkRedisSerializer.deserialize(bytes);
				else
					return null;
			}
		});
	}

	public String hgetString(final String key, final String field) {
		return execute(new ShardedRedisCallback<String>() {

			@Override
			public String execute(ShardedJedis shardedJedis) {
				byte[] bytes = shardedJedis.hget(stringRedisSerializer.serialize(key),
						stringRedisSerializer.serialize(field));
				if (bytes != null)
					return stringRedisSerializer.deserialize(bytes);
				else
					return null;
			}
		});
	}

	public Number hgetNumber(final String key, final String field) {
		return execute(new ShardedRedisCallback<Number>() {

			@Override
			public Number execute(ShardedJedis shardedJedis) {
				byte[] bytes = shardedJedis.hget(stringRedisSerializer.serialize(key),
						stringRedisSerializer.serialize(field));
				if (bytes != null)
					return numberRedisSerializer.deserialize(bytes);
				else
					return null;
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
