package com.yu.util.redis;

import java.util.concurrent.TimeUnit;

import redis.clients.jedis.Jedis;

import com.yu.util.date.DateUtils;

/**
 * Redis原子锁
 *
 * @author zengxm
 * @date 2015年7月31日
 *
 */
public class RedisLock implements AutoCloseable {

	private static final int MAX_EXPIRE_TIME = 60 * 3; // 锁最大失效时间（秒）

	private static final int MAX_RETRY_NUM = 5; // 重试次数

	private static final int SLEEP_TIMEOUT = 30; // 等待重试时间（毫秒）

	private Jedis jedis;

	private boolean locked = false; // 实例锁

	private String key; // 锁的key

	public RedisLock(String key) {
		this.key = key;
		this.jedis = RedisPool.getJedis();
	}

	/**
	 * 1, setnx -- 当key不存在时, 设定值为当前时间+过期间隔;<br/>
	 * 2, 如果返回0, 返回true;<br/>
	 * 3, 如果返回1, 使用get key获取值;<br/>
	 * 4, 如果 当前时间 - 获取值 > 最大时间间隔, 使用getset获取原值并设定为当前时间;<br/>
	 * 5, 如果原值=获取值, 说明成功获取锁, 返回true;<br/>
	 * 6, 如果不相等, 说明被其他客户端突然获取了;<br/>
	 * 7, 等待对应秒数, 重新回到第一步;<br/>
	 * 
	 * @return - boolean
	 * @throws InterruptedException
	 */
	public boolean lock() {
		int count = MAX_RETRY_NUM;

		while (count > 0) {
			if (jedis == null)
				jedis = RedisPool.getJedis();
			// 设定锁的时间为当前秒数+失效时间+1
			long lockTime = DateUtils.getCurrentSeconds() + MAX_EXPIRE_TIME + 1;
			long ret = jedis.setnx(key, String.valueOf(lockTime));
			// 成功设定, 则返回获取锁成功
			if (ret == 1) {
				this.locked = true;
				break;
			}
			// 尝试分析锁的时间是否过期
			String oldTime = jedis.get(key);
			if (oldTime != null
					&& Long.valueOf(oldTime) < DateUtils.getCurrentSeconds()) {
				String anotherOldTime = jedis.getSet(key,
						String.valueOf(lockTime));
				// 判断是否在这个尝试获取锁的过程没有被其他进程改变格局
				if (anotherOldTime != null && anotherOldTime.equals(oldTime)) {
					this.locked = true;
					break;
				}
			}
			count--;
			try {
				TimeUnit.MILLISECONDS.sleep(SLEEP_TIMEOUT);
			} catch (InterruptedException e) {
				// TODO 是否可以忽略
			}
		}// --> end while

		return this.locked;
	}

	/**
	 * 释放锁
	 * 
	 */
	public void unlock() {
		try {
			if (this.locked) {
				this.jedis.del(key);
			}
		} finally {
			jedis.close();
			jedis = null;
		}
	}

	@Override
	public void close() throws Exception {
		// TODO 是否可行
		unlock();
	}

}
