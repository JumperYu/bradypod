package com.bradypod.util.redis;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 工厂类
 *
 * @author zengxm
 * @date 2015年9月27日
 *
 */
public class RedisFactory {

	private String host;
	private int port;
	private String password; // 可以不设置密码

	private String hosts[];
	private int ports[];
	private String passwords[];

	private JedisPool jedisPool;
	private ShardedJedisPool shardedJedisPool; // 推荐使用哈希一致性连接池

	/* 创建连接池 */
	public JedisPool createJedisPool() {
		if (this.jedisPool == null) {
			this.jedisPool = new JedisPool(host, port);
		}
		return this.jedisPool;
	}

	/**
	 * 创建redis分片客户端
	 * 
	 */
	public ShardedJedisPool createShardedJedisPool() {
		if (this.shardedJedisPool == null) {
			List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
			if (hosts != null) {
				for (int i = 0, len = hosts.length; i < len; i++) {
					shards.add(createJedisShardInfo(hosts[i], ports[i], passwords[i]));
				}
			} else {
				shards.add(createJedisShardInfo(host, port, password));
			}
			this.shardedJedisPool = new ShardedJedisPool(buildPoolConfig(), shards);
		}
		return this.shardedJedisPool;
	}

	/**
	 * 分片实例
	 * 
	 * @param host
	 *            - 主机地址
	 * @param port
	 *            - 端口
	 * @param password
	 *            - 可不传
	 * @return
	 */
	private JedisShardInfo createJedisShardInfo(String host, int port, String password) {
		JedisShardInfo jedisShardInfo = new JedisShardInfo(host, port);
		if (StringUtils.isNotBlank(password)) {
			jedisShardInfo.setPassword(password);
		}
		return jedisShardInfo;
	}

	/**
	 * 创建连接池配置
	 * 
	 * @return - JedisPoolConfig
	 */
	private static JedisPoolConfig buildPoolConfig() {

		// 建立连接池配置参数
		JedisPoolConfig config = new JedisPoolConfig();

		// 设置最大连接数
		config.setMaxTotal(300);

		// 设置最大空闲数
		config.setMaxIdle(200);

		return config;
	}

	/* get/set */
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String[] getHosts() {
		return hosts;
	}

	public void setHosts(String[] hosts) {
		this.hosts = hosts;
	}

	public int[] getPorts() {
		return ports;
	}

	public void setPorts(int[] ports) {
		this.ports = ports;
	}

	public String[] getPasswords() {
		return passwords;
	}

	public void setPasswords(String[] passwords) {
		this.passwords = passwords;
	}

	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	public ShardedJedisPool getShardedJedisPool() {
		return shardedJedisPool;
	}

	public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
		this.shardedJedisPool = shardedJedisPool;
	}

}
