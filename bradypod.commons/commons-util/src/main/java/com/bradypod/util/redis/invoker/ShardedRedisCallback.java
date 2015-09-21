package com.bradypod.util.redis.invoker;

import redis.clients.jedis.ShardedJedis;

public interface ShardedRedisCallback<T> {

	public T execute(ShardedJedis shardedJedis);
}
