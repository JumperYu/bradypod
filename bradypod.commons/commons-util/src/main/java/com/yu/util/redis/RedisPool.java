package com.yu.util.redis;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * JedisPool工具类, 它自身是基于commons-pool
 *
 * @author zengxm
 * @date 2015年7月31日
 *
 */
public class RedisPool {

	public static JedisPool jedisPool;
	private static ShardedJedisPool shardedJedisPool;

	private static final String HOST = "redis.bradypod.com";
	private static final int PORT = 6379;
//	private static final long MAX_WAIT_MILLIS = 60000; // 最大等待时间

	/**
	 * 建立连接池 真实环境，一般把配置参数缺抽取出来。
	 * 
	 */
	private static void createJedisPool() {

		// 创建连接池
		jedisPool = new JedisPool(buildPoolCnfig(), HOST, PORT);

	}

	/**
	 * 创建分片redis连接池
	 * 
	 */
	private static void createShardedJedisPool() {
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		JedisShardInfo si = new JedisShardInfo(HOST, PORT);
		shards.add(si);
		shardedJedisPool = new ShardedJedisPool(buildPoolCnfig(), shards);
	}

	/**
	 * 创建连接池配置
	 * 
	 * @return - JedisPoolConfig
	 */
	private static JedisPoolConfig buildPoolCnfig() {

		// 建立连接池配置参数
		JedisPoolConfig config = new JedisPoolConfig();

/*		// 设置最大阻塞时间，记住是毫秒数milliseconds
		config.setMaxWaitMillis(-1);
		
		// 最小存活时间才能回收
		config.setTestWhileIdle(true);

		config.setTestOnBorrow(false);

		config.setTestOnReturn(false);

		config.setMinEvictableIdleTimeMillis(300000L);
		*/
		// 设置最大连接数
		config.setMaxTotal(300);

		// 设置最大空闲数
		config.setMaxIdle(200);
		
		return config;
	}

	/**
	 * 在多线程环境同步初始化
	 */
	private static synchronized void jedisPoolInit() {
		if (jedisPool == null)
			createJedisPool();
	}

	/**
	 * 在多线程环境同步初始化
	 */
	private static synchronized void shardedJedisPoolInit() {
		if (shardedJedisPool == null)
			createShardedJedisPool();
	}

	/**
	 * 获取一个jedis 对象
	 * 
	 * @return - Jedis
	 */
	public static Jedis getJedis() {

		if (jedisPool == null)
			jedisPoolInit();
		return jedisPool.getResource();
	}

	/**
	 * 获取一个shardedJedis对象
	 * 
	 * @return - ShardedJedis
	 */
	public static ShardedJedis getShardedJedis() {
		if (shardedJedisPool == null)
			shardedJedisPoolInit();
		return shardedJedisPool.getResource();
	}

}
