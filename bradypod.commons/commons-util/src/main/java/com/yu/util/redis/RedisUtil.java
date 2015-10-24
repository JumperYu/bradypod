package com.yu.util.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

/**
 * 基于jedis的工具类
 *
 * @author zengxm
 * @date 2015年7月31日
 *
 */
public class RedisUtil {

	public static String HOST = "redis.bradypod.com";
	public static int PORT = 6379;

	/**
	 * 创建系统默认的连接
	 * 
	 * @return - Jedis
	 */
	public static Jedis createJedis() {
		return createJedis(HOST, PORT);
	}

	/**
	 * 创建无密码的连接
	 * 
	 * @param host
	 *            - 主机地址
	 * @param port
	 *            - 主机端口
	 * @return - Jedis
	 */
	public static Jedis createJedis(String host, int port) {
		return createJedis(host, port, "");
	}

	/**
	 * 创建有密码Jedis的连接
	 * 
	 * @param host
	 *            - 主机地址
	 * @param port
	 *            - 主机端口
	 * @param passwrod
	 *            - - 验证密码
	 * @return - Jedis
	 */
	public static Jedis createJedis(String host, int port, String passwrod) {
		Jedis jedis = new Jedis(host, port);

		if (StringUtils.isNotBlank(passwrod))
			jedis.auth(passwrod);

		return jedis;
	}

	/**
	 * Redis集群分片, Hash一致性基于MurmurHash 2.0, 变种算法CityHash
	 * 
	 * @return - ShardedJedis
	 */
	public static ShardedJedis createShardJedis(String[] hosts, int[] ports, String[] passwords) {

		// 建立服务器列表
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();

		// 添加redis服务器信息
		for (int i = 0, len = hosts.length; i < len; i++) {
			JedisShardInfo jedisShardInfo = new JedisShardInfo(hosts[i], ports[i]);
			// 是否需要密码
			if (StringUtils.isNotBlank(passwords[i])) {
				jedisShardInfo.setPassword(passwords[i]);
			}
			shards.add(jedisShardInfo);
		}

		// 建立分片连接对象
		ShardedJedis jedis = new ShardedJedis(shards);

		// 建立分片连接对象,并指定Hash算法
		// ShardedJedis jedis = new ShardedJedis(shards,selfHash);
		return jedis;
	}

	/**
	 * 创建集群连接
	 * 
	 * @param hosts
	 * @param ports
	 * @param passwords
	 * @return
	 */
	public static JedisCluster createCluster(HostAndPort[] hostAndPorts) {
		Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
		for (HostAndPort hostAndPort : hostAndPorts) {
			jedisClusterNodes.add(hostAndPort);
		}
		JedisCluster jc = new JedisCluster(jedisClusterNodes);
		return jc;
	}
}
