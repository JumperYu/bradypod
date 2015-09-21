package com.bradypod.util.redis.invoker;

import redis.clients.jedis.Jedis;

/**
 * Redis回调接口定义
 *
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015年9月21日 上午11:43:11
 */
public interface RedisCallback<T> {
	public abstract T execute(Jedis jedis);

}
