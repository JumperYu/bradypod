package com.bradypod.util.redis.cache;

import com.bradypod.util.redis.Redis;
import com.bradypod.util.redis.constant.CacheType;
import com.bradypod.util.redis.util.SerializeUtil;
import com.bradypod.util.reflect.OgnlUtil;

/**
 * 重构RedisCache
 *
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015年9月22日 上午9:46:33
 */
public class RedisCache {

	private Redis redis; // 使用RedisShardedImpl

	private String namespace_single; // 建议使用xxxDaoImpl:xxx:single
	private String namespace_multiple; // 建议使用xxxDaoImpl:xxx:multiple

	// private final ReentrantLock readWriteLok = new ReentrantLock();

	public RedisCache() {
	}

	/**
	 * 往缓存存储对象
	 * 
	 * @param cacheType
	 *            - 缓存类型 @see {CacheType}
	 * @param params
	 *            - 参数
	 * @param value
	 *            - 值
	 * @param keys
	 *            - 所有参数名
	 */
	public void putObject(CacheType cacheType, Object params, final Object value,
			final Object... keys) {
		// 统一生成key
		String redisKey = getRedisKey(params, keys);
		// namespace + cachetype 为唯一个哈希表
		redis.hset(getTableName(cacheType).getBytes(), redisKey.getBytes(),
				SerializeUtil.serialize(value));
	}

	/**
	 * 从缓存查找对象
	 * 
	 * @param cacheType
	 *            - 缓存类型 @see {CacheType}
	 * @param params
	 *            - 参数
	 * @param keys
	 *            - 参数名
	 * @return
	 */
	public Object getObject(CacheType cacheType, Object params, Object... keys) {
		// 统一生成key
		String redisKey = getRedisKey(params, keys);
		// namespace + cachetype 为唯一个哈希表
		return SerializeUtil.unserialize(redis.hget(getTableName(cacheType).getBytes(),
				redisKey.getBytes()));
	}

	/**
	 * 删除缓存
	 * 
	 * @param cacheType
	 *            - 缓存类型 @see {CacheType}
	 * @param keys
	 *            - 参数名
	 */
	public void removeObject(CacheType cacheType, Object params, Object... keys) {
		// 统一生成key
		String redisKey = getRedisKey(params, keys);
		redis.hdel(getTableName(cacheType), redisKey);
	}

	/**
	 * 清空缓存
	 * 
	 * @param cacheType
	 *            - 缓存类型 @see {CacheType}
	 */
	public void clear(CacheType cacheType) {
		redis.del(getTableName(cacheType));
	}

	/**
	 * 获取缓存大小
	 * 
	 * @param cacheType
	 *            - 缓存类型
	 * @return - 大小
	 */
	public long getSize(CacheType cacheType) {
		return redis.hlen(getTableName(cacheType));
	}

	/**
	 * 获取缓存大小
	 * 
	 * @param cacheType
	 *            - 缓存类型
	 * 
	 * @return - 哈希表的名字
	 */
	public String getTableName(CacheType cacheType) {
		switch (cacheType) {
		case SINGLE:
			return namespace_single;
		case MULTIPLE:
			return namespace_multiple;
		default:
			throw new IllegalArgumentException("unknown cache type");
		}
	}

	/**
	 * 获取redisKey, 统一key的生成
	 * 
	 * @param root
	 *            - 对象
	 * @return - key like {namespace}:{domain}:{id}:{userId}
	 */
	public String getRedisKey(Object root, Object... keys) {
		StringBuilder sb = new StringBuilder();
		// sb.append(...).append(SPLIT_FLAG);
		for (Object key : keys) {
			sb.append(OgnlUtil.getValue(key.toString(), root)).append(SPLIT_FLAG);
		}
		return sb.substring(0, sb.length() - 1).toString();
	}

	/* get/set */
	public void setRedis(Redis redis) {
		this.redis = redis;
	}

	public Redis getRedis() {
		return redis;
	}

	private static char SPLIT_FLAG = ':';
}
