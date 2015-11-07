package com.bradypod.util.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.InitializingBean;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.util.Pool;

/**
 * 工厂类
 *
 * @author zengxm
 * @date 2015年9月27日
 *
 */
public class RedisFactory implements InitializingBean {

	protected String host;
	protected int port;
	protected String password; // 可以不设置密码

	protected String hosts[];
	protected int ports[];

	protected String masterName;

	protected volatile Pool<Jedis> pool;

	static final int DEFAULT_TIMEOUT = 500; // 500毫秒就超时

	public RedisFactory() {
	}

	public RedisFactory(String host, int port) {
		this.host = host;
		this.port = port;
	}

	/**
	 * 获取或创建连接池
	 */
	public Pool<Jedis> getPool() {
		if (this.pool == null) {
			synchronized (RedisFactory.class) {
				if (this.pool == null) {
					createPool();
				}
			}// --> double check lock
		}// --> end if
		return this.pool;
	}

	private Pool<Jedis> createPool() {
		if (this.pool == null) {
			this.pool = new JedisPool(new GenericObjectPoolConfig(), host, port, DEFAULT_TIMEOUT);
		}
		return this.pool;
	}

	/* 获取资源 */
	public Jedis getResource() {
		return getPool().getResource();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		createPool();
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

	public String getMasterName() {
		return masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

}
