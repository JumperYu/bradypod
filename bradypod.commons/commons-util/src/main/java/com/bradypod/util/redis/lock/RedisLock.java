package com.bradypod.util.redis.lock;

import java.io.Closeable;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.math.RandomUtils;

import com.bradypod.util.redis.RedisTemplate;

/**
 * 分布式锁 - redis实现
 *
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2015年12月17日 下午5:20:13
 */
public class RedisLock implements Closeable {

	private String key;

	private int expireTime;

	private RedisTemplate redisTemplate;

	/**
	 * 
	 * @param expireTime
	 * @param redisTemplate
	 */
	public RedisLock(String key, int expireTime, RedisTemplate redisTemplate) {
		this.key = key;
		this.expireTime = expireTime;
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 
	 * try(RedisLock redisLock = new RedisLock();){ // 上锁 redisLock.lock(); //
	 * 执行业务 doSomething(); }
	 */
	public void lock() {
		tryLockAndWait();
	}

	/**
	 * 解锁
	 */
	public void unlock() {
		redisTemplate.delete(key);
	}

	/**
	 * 尝试上锁
	 * 
	 * @param key
	 * @return - true/false
	 */
	public boolean tryLock() {
		// 记录当前时间点（秒）
		long nowtime = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
		String value = stringify(nowtime + expireTime + 1);
		// --> 第一步setnx, 如果成功证明上锁
		if (redisTemplate.setnx(key, value) == SET_NX_IS_OK) {
			return true;
		}
		// 记录旧锁的时间（秒）
		String currentLockValue = redisTemplate.getStringValue(key);
		// --> 第二步get, 如果时间已经超时则继续尝试上锁, 否则等待
		if (currentLockValue == null || nowtime > longify(currentLockValue)) {
			// 再次记录旧锁的值（秒）
			String oldValue = redisTemplate.getSet(key, value);
			// --> 第三步getSet, 如果获取的值和记录的旧值相吻合则上锁, 否则结束尝试
			if (currentLockValue == oldValue || currentLockValue.equals(oldValue)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 尝试上锁, 重入版
	 * 
	 * @param key
	 * @return - true/false
	 */
	private boolean tryLockAndWait() {
		// 记录当前时间点（秒）
		boolean isLocked = false;
		while (!isLocked) {
			long nowtime = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
			String value = stringify(nowtime + expireTime + 1);
			// --> 第一步setnx, 如果成功证明上锁
			if (redisTemplate.setnx(key, value) == SET_NX_IS_OK) {
				isLocked = true;
				redisTemplate.expire(value, expireTime);
				break;
			}
			// 记录旧锁的时间（秒）
			String currentLockValue = redisTemplate.getStringValue(key);
			// --> 第二步get, 如果时间已经超时则继续尝试上锁, 否则等待
			if (currentLockValue == null || nowtime > longify(currentLockValue)) {
				// 再次记录旧锁的值（秒）
				String oldValue = redisTemplate.getSet(key, value);
				// --> 第三步getSet, 如果获取的值和记录的旧值相吻合则上锁, 否则结束尝试
				if (currentLockValue == oldValue || currentLockValue.equals(oldValue)) {
					isLocked = true;
					redisTemplate.expire(value, expireTime);
					break;
				}
			}
			// --> 如果没有获取到锁, 则随机休息后重入锁
			try {
				TimeUnit.MILLISECONDS.sleep(RandomUtils.nextInt(WAIT_TIME));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}// --> end while
		return isLocked;
	}

	@Override
	public void close() {
		unlock();
	}

	// --> 数字和字符串的互相转换
	private static String stringify(long time) {
		return String.valueOf(time);
	}

	private static long longify(String time) {
		return Long.parseLong(time);
	}

	// redis操作返回正确的响应码
	private static final long SET_NX_IS_OK = 1;

	private static final int WAIT_TIME = 100;
}
