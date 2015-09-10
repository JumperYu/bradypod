package com.yu.util.cache;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/**
 * 缓存
 *
 * @author zengxm<github.com/JumperYu>
 *
 *         2015年8月28日 下午2:15:09
 */
public class CachedData {

	private final Map<String, Object> memory = new TreeMap<String, Object>();

	final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	final ReadLock readLock = readWriteLock.readLock();
	final WriteLock writeLock = readWriteLock.writeLock();

	/**
	 * 从缓存查找值
	 * 
	 * @param key
	 *            - String 键
	 * @return - Object 对象 不存在时返回NULL
	 */
	public Object get(String key) {
		readLock.lock();
		try {
			return memory.get(key);
		} finally {
			readLock.unlock();
		}
	}

	/**
	 * 获取所有键的集合
	 * 
	 * @return - Set<String>
	 */
	public Set<String> allKeys() {
		readLock.lock();
		try {
			return memory.keySet();
		} finally {
			readLock.unlock();
		}
	}

	/**
	 * 添加缓存
	 * 
	 * @param key
	 *            - 键
	 * @param value
	 *            - 值
	 * @return Object - 键之前关联的值或null @see {Object java.util.Map.put(String key,
	 *         Object value)}
	 */
	public Object put(String key, Object value) {
		writeLock.lock();
		try {
			return memory.put(key, value);
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * 清空缓存
	 * 
	 */
	public void clear() {
		writeLock.lock();
		try {
			memory.clear();
		} finally {
			writeLock.unlock();
		}
	}
}
