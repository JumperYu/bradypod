package com.yu.util.redis;

import redis.clients.jedis.Jedis;

/**
 * 回调函数
 *
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015年9月21日 上午10:07:14
 */
public interface RedisCallback {

	public Object doWithRedis(Jedis jedis);

}
