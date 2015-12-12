package com.bradypod.util.redis.lock;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bradypod.util.redis.RedisTemplate;

/**
 * 分布式锁
 *
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015年9月21日 上午11:20:34
 */
public class RedisLock implements Closeable {

	/**
	 * 实例
	 */
	private RedisTemplate redisTemplate;

	/**
	 * 锁
	 */
	private String key;

	/**
	 * 锁的超时时间（秒），过期自动清除键
	 */
	private int expire = 0;

	/**
	 * 锁状态标志
	 */
	private volatile boolean locked = false;

	// 默认失效秒数
	private static final int DEFAULT_EXPIRE_SECONDS = 60;

	/**
	 * 其他人锁的 timestamp，仅用于debug
	 */
	private String lockTimestamp = "";

	/**
	 * 
	 * @param redisTemplate
	 *            - redis实例
	 * @param key
	 *            - 锁的键
	 */
	public RedisLock(RedisTemplate redisTemplate, String key) {
		this(redisTemplate, key, DEFAULT_EXPIRE_SECONDS);
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
		// 避免参数缺少
		if (redisTemplate == null || key == null) {
			throw new IllegalArgumentException("redis和key不能为null");
		}
		if (expire <= 0) {
			throw new IllegalArgumentException("expire必须大于0");
		}
		// set
		this.redisTemplate = redisTemplate;
		this.key = key;
		this.expire = expire;
	}

	/**
	 * 尝试获得锁, 只尝试一次 <br>
	 * 如果成功获得锁返回:true, 否则返回:false <br>
	 * 如果锁已经被其他线程持有, 返回:false。<br>
	 */
	public boolean lock() {

		long now = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());

		// 保存超时的时间
		String time = (now + expire + 1) + "";

		if (tryLock(time)) {
			logger.info("You locked {}:{}", key, time);
		} else {
			// 第一次尝试失败, 则进行二次验证
			String value = redisTemplate.getStringValue(key);
			if (value == null || now > transformValue(value)) {
				// 锁已经超时或者已经释放, 尝试 GETSET 竞争锁
				String oldValue = redisTemplate.getSet(key, time);
				// 返回的时间戳如果仍然是超时的，那就说明，如愿以偿拿到锁，否则是其他进程/线程设置了锁
				if (oldValue == value && now > transformValue(oldValue)) {
					// 设置状态
					this.locked = true;
					// 设置失效时间
					redisTemplate.expire(key, expire);
					// 获取锁
					this.lockTimestamp = time;
					
					logger.info("You locked2 {}:{}", key, time);
				} else {
					// 未能获取锁
					this.lockTimestamp = oldValue;
//					logger.info("GETSET锁失败，旧值是：{}:{}", key, value);
				}
			} else {
				// 未能获取锁
				this.lockTimestamp = value;
//				logger.info("SETNX锁失败, 旧值是：{}:{}", key, value);
			}
		}// --> end if-else
		logger.info("You locked {}", locked);
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
//				logger.info("delete key {}:{}", key, lockTimestamp);
			} catch (Exception e) {
				logger.error("EXCEPTION when delete key: ", e);
			}
		}
	}

	/**
	 * 尝试获得锁
	 * 
	 * @param time
	 *            - 锁超时的时间（秒）
	 * @return true=成功
	 */
	private boolean tryLock(String time) {
		// 当锁状态为false, 且当前的key不存在redis
		if (this.locked == false && redisTemplate.setnx(key, time) == SET_NX_IS_OK) {
			// 设置失效时间
			redisTemplate.expire(key, expire);
			// 设置锁的状态
			this.locked = true;
		}
		return this.locked;
	}

	/**
	 * 将字符串转换为数字
	 * 
	 * @param value
	 * @return
	 */
	private static long transformValue(String value) {
		if (StringUtils.isNotEmpty(value)) {
			return NumberUtils.toLong(value, 0L);
		}
		return 0L;
	}

	@Override
	public void close() throws IOException {
		unlock();
	}

	/* get/set */
	public String getLockTimestamp() {
		return lockTimestamp;
	}

	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	// static
	private static final int SET_NX_IS_OK = 1;

	private static final Logger logger = LoggerFactory.getLogger(RedisLock.class);
}
