package com.bradypod.util.redis.lock;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

import com.bradypod.util.redis.RedisTemplate;

/**
 * 分布式锁
 *
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015年9月21日 上午11:20:34
 */
public class RedisLock {

	private Logger logger = LoggerFactory.getLogger(RedisLock.class);

	private RedisTemplate redisTemplate;

	/** 锁的键 */
	private String key;

	/** 锁的超时时间（秒），过期自动清除键 */
	private int expire = 0;

	// 锁状态标志
	private volatile boolean locked = false;

	/**
	 * 其他人锁的 timestamp，仅用于debug
	 */
	private String lockTimestamp = "";

	/**
	 * RedisLock构造函数，默认锁的过期时间是480秒
	 * 
	 * @param redis
	 *            - Redis 实例
	 * @param key
	 *            - 要锁的key
	 */
	public RedisLock(RedisTemplate redisTemplate, String key) {
		this(redisTemplate, key, 8 * 60);
	}

	/**
	 * RedisLock构造函数
	 * 
	 * @param redis
	 *            - Redis 实例
	 * @param key
	 *            - 要锁的key
	 * @param expire
	 *            - 过期时间，单位秒，必须大于0
	 */
	public RedisLock(RedisTemplate redisTemplate, String key, int expire) {
		if (redisTemplate == null || key == null) {
			throw new IllegalArgumentException("redis和key不能为null");
		}
		if (expire <= 0) {
			throw new IllegalArgumentException("expire必须大于0");
		}
		this.redisTemplate = redisTemplate;
		this.key = getLockKey(key);
		this.expire = expire;
	}

	/**
	 * 尝试获得锁，只尝试一次，如果获得锁成功，返回true，否则返回false。 如果锁已经被其他线程持有，本操作不会等待锁。
	 * 
	 * @return 成功返回true
	 */
	public boolean lock() {
		long now = System.currentTimeMillis() / 1000;
		// 保存超时的时间
		String time = (now + expire + 1) + "";
		if (tryLock(time)) {
			// lock success, return
			lockTimestamp = time;
		} else {
			// 锁失败，看看 timestamp 是否超时
			String value = redisTemplate.getStringValue(key);
			if (now > transformValue(value)) {
				// 锁已经超时，尝试 GETSET 操作
				value = redisTemplate.getSet(key, time);
				// 返回的时间戳如果仍然是超时的，那就说明，如愿以偿拿到锁，否则是其他进程/线程设置了锁
				if (now > transformValue(value)) {
					this.locked = true;
				} else {
					logger.error("GETSET 锁的旧值是：" + value + ", key=" + key);
				}
			} else {
				logger.error("GET 锁的当前值是：" + value + ", key=" + key);
			}
			this.lockTimestamp = value;

		}
		return this.locked;
	}

	/**
	 * 释放已经获得的锁， 只有在获得锁的情况下才会释放锁。 本方法不会抛出异常。
	 */
	public void unlock() {
		if (this.locked) {
			try {
				redisTemplate.delete(key);
				this.locked = false;
			} catch (Exception e) {
				logger.error("EXCEPTION when delete key: ", e);
			}
		}
	}

	private long transformValue(String value) {
		if (StringUtils.isNotEmpty(value)) {
			return NumberUtils.toLong(value, 0L);
		}
		return 0L;
	}

	/**
	 * 尝试获得锁
	 * 
	 * @param time
	 *            - 锁超时的时间（秒）
	 * @return true=成功
	 */
	private boolean tryLock(String time) {
		if (this.locked == false && redisTemplate.setnx(key, time) == 1) {
			// redis.expire(key, EXPIRE);
			this.locked = true;
		}
		return this.locked;
	}

	public String getLockTimestamp() {
		return lockTimestamp;
	}

	/**
	 * 获得Redis锁的key
	 * 
	 * @param key
	 *            - 要锁的key
	 * @return key
	 */
	private static String getLockKey(String key) {
		return "R_lock4_" + key;
	}

	/**
	 * 清除key
	 * 
	 * @param redis
	 *            - Redis 实例
	 * @param key
	 *            - 要清除锁的key
	 */
	public static void clearLock(Jedis redis, final String key) {
		String tmp = getLockKey(key);
		redis.del(tmp);
	}

	/* set */
	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
}
