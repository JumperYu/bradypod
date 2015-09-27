package com.bradypod.util.redis;

import java.util.Collection;

import redis.clients.jedis.ShardedJedis;

import com.bradypod.util.redis.invoker.ShardedRedisCallback;
import com.bradypod.util.redis.serializer.RedisSerializer;

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
	public void set(final String key, final Number value) {

	}

	public void set(final String key, final String value) {

	}

	public void set(final String key, final Object obj) {

	}

	public void set(final String key, final Collection<?> collection) {

	}

	// TODO get方法, 扩展类型
	public Object get(final String key) {
		return null;
	}
}
