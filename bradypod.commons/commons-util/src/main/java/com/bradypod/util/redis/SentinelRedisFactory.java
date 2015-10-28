package com.bradypod.util.redis;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.yu.util.validate.AssertUtil;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.util.Pool;

public class SentinelRedisFactory extends RedisFactory {

	@Override
	public Pool<Jedis> createPool() {
		Set<String> sentinels = new HashSet<String>();
		if (hosts != null) {
			for (int i = 0; i < hosts.length; i++) {
				sentinels.add(new HostAndPort(hosts[i], ports[i]).toString());
			}
		}
		AssertUtil.assertNotNull(masterName, "master name is not set ");
		pool = new JedisSentinelPool(masterName, sentinels, new GenericObjectPoolConfig());
		return pool;
	}

}
