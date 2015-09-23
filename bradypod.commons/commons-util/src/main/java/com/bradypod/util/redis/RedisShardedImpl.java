package com.bradypod.util.redis;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;
import redis.clients.util.Pool;

import com.bradypod.util.redis.invoker.ShardedRedisCallback;

/**
 * 哈希一致性Redis连接
 *
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015年9月21日 下午7:38:04
 */
public class RedisShardedImpl implements Redis {

	private Pool<ShardedJedis> pool;

	/**
	 * 回调处理, 执行完成后, 自动释放资源
	 * 
	 * @see {java.io.Closeable}
	 * 
	 * @param callback
	 *            - @see {ShardedRedisCallback<T>}
	 * @return - <T>
	 */
	protected <T> T execute(final ShardedRedisCallback<T> callback) {
		// TODO jedisPool.ping(); check server is live.
		if (pool == null) {
			LOGGER.error("RedisShardingImpl.pool is not set up yet!");
		}
		try (ShardedJedis shardedJedis = pool.getResource()) {
			return callback.execute(shardedJedis);
		}
	}

	@Override
	public String set(final String key, final String value) {
		return execute(new ShardedRedisCallback<String>() {
			@Override
			public String execute(ShardedJedis jedis) {
				return jedis.set(key, value);
			}
		});
	}

	@Override
	public String set(final String key, final String value, final String nxxx, final String expx,
			final long time) {
		return execute(new ShardedRedisCallback<String>() {
			@Override
			public String execute(ShardedJedis jedis) {
				return jedis.set(key, value, nxxx, expx, time);
			}
		});
	}

	@Override
	public String get(final String key) {
		return execute(new ShardedRedisCallback<String>() {
			@Override
			public String execute(ShardedJedis jedis) {
				return jedis.get(key);
			}
		});
	}

	@Override
	public Boolean exists(final String key) {
		return execute(new ShardedRedisCallback<Boolean>() {
			@Override
			public Boolean execute(ShardedJedis jedis) {
				return jedis.exists(key);
			}
		});
	}

	@Override
	public Long persist(final String key) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.persist(key);
			}
		});
	}

	@Override
	public String type(final String key) {
		return execute(new ShardedRedisCallback<String>() {
			@Override
			public String execute(ShardedJedis jedis) {
				return jedis.type(key);
			}
		});
	}

	@Override
	public Long expire(final String key, final int seconds) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.expire(key, seconds);
			}
		});
	}

	@Override
	public Long pexpire(final String key, final long milliseconds) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.pexpire(key, milliseconds);
			}
		});
	}

	@Override
	public Long expireAt(final String key, final long unixTime) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.expireAt(key, unixTime);
			}
		});
	}

	@Override
	public Long pexpireAt(final String key, final long millisecondsTimestamp) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.pexpireAt(key, millisecondsTimestamp);
			}
		});
	}

	@Override
	public Long ttl(final String key) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.ttl(key);
			}
		});
	}

	@Override
	public Boolean setbit(final String key, final long offset, final boolean value) {
		return execute(new ShardedRedisCallback<Boolean>() {
			@Override
			public Boolean execute(ShardedJedis jedis) {
				return jedis.setbit(key, offset, value);
			}
		});
	}

	@Override
	public Boolean setbit(final String key, final long offset, final String value) {
		return execute(new ShardedRedisCallback<Boolean>() {
			@Override
			public Boolean execute(ShardedJedis jedis) {
				return jedis.setbit(key, offset, value);
			}
		});
	}

	@Override
	public Boolean getbit(final String key, final long offset) {
		return execute(new ShardedRedisCallback<Boolean>() {
			@Override
			public Boolean execute(ShardedJedis jedis) {
				return jedis.getbit(key, offset);
			}
		});
	}

	@Override
	public Long setrange(final String key, final long offset, final String value) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.setrange(key, offset, value);
			}
		});
	}

	@Override
	public String getrange(final String key, final long startOffset, final long endOffset) {
		return execute(new ShardedRedisCallback<String>() {
			@Override
			public String execute(ShardedJedis jedis) {
				return jedis.getrange(key, startOffset, endOffset);
			}
		});
	}

	@Override
	public String getSet(final String key, final String value) {
		return execute(new ShardedRedisCallback<String>() {
			@Override
			public String execute(ShardedJedis jedis) {
				return jedis.getSet(key, value);
			}
		});
	}

	@Override
	public Long setnx(final String key, final String value) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.setnx(key, value);
			}
		});
	}

	@Override
	public String setex(final String key, final int seconds, final String value) {
		return execute(new ShardedRedisCallback<String>() {
			@Override
			public String execute(ShardedJedis jedis) {
				return jedis.setex(key, seconds, value);
			}
		});
	}

	@Override
	public Long decrBy(final String key, final long integer) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.decrBy(key, integer);
			}
		});
	}

	@Override
	public Long decr(final String key) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.decr(key);
			}
		});
	}

	@Override
	public Long incrBy(final String key, final long integer) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.incrBy(key, integer);
			}
		});
	}

	@Override
	public Long incr(final String key) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.incr(key);
			}
		});
	}

	@Override
	public Double incrByFloat(final String key, final double value) {
		return execute(new ShardedRedisCallback<Double>() {
			@Override
			public Double execute(ShardedJedis jedis) {
				return jedis.incrByFloat(key, value);
			}
		});
	}

	@Override
	public Long append(final String key, final String value) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.append(key, value);
			}
		});
	}

	@Override
	public String substr(final String key, final int start, final int end) {
		return execute(new ShardedRedisCallback<String>() {
			@Override
			public String execute(ShardedJedis jedis) {
				return jedis.substr(key, start, end);
			}
		});
	}

	@Override
	public Long hset(final String key, final String field, final String value) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.hset(key, field, value);
			}
		});
	}

	@Override
	public String hget(final String key, final String field) {
		return execute(new ShardedRedisCallback<String>() {
			@Override
			public String execute(ShardedJedis jedis) {
				return jedis.hget(key, field);
			}
		});
	}

	@Override
	public Long hsetnx(final String key, final String field, final String value) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.hsetnx(key, field, value);
			}
		});
	}

	@Override
	public String hmset(final String key, final Map<String, String> hash) {
		return execute(new ShardedRedisCallback<String>() {
			@Override
			public String execute(ShardedJedis jedis) {
				return jedis.hmset(key, hash);
			}
		});
	}

	@Override
	public List<String> hmget(final String key, final String... fields) {
		return execute(new ShardedRedisCallback<List<String>>() {
			@Override
			public List<String> execute(ShardedJedis jedis) {
				return jedis.hmget(key, fields);
			}
		});
	}

	@Override
	public Long hincrBy(final String key, final String field, final long value) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.hincrBy(key, field, value);
			}
		});
	}

	@Override
	public Boolean hexists(final String key, final String field) {
		return execute(new ShardedRedisCallback<Boolean>() {
			@Override
			public Boolean execute(ShardedJedis jedis) {
				return jedis.hexists(key, field);
			}
		});
	}

	@Override
	public Long hdel(final String key, final String... fields) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.hdel(key, fields);
			}
		});
	}

	@Override
	public Long hlen(final String key) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.hlen(key);
			}
		});
	}

	@Override
	public Set<String> hkeys(final String key) {
		return execute(new ShardedRedisCallback<Set<String>>() {
			@Override
			public Set<String> execute(ShardedJedis jedis) {
				return jedis.hkeys(key);
			}
		});
	}

	@Override
	public List<String> hvals(final String key) {
		return execute(new ShardedRedisCallback<List<String>>() {
			@Override
			public List<String> execute(ShardedJedis jedis) {
				return jedis.hvals(key);
			}
		});
	}

	@Override
	public Map<String, String> hgetAll(final String key) {
		return execute(new ShardedRedisCallback<Map<String, String>>() {
			@Override
			public Map<String, String> execute(ShardedJedis jedis) {
				return jedis.hgetAll(key);
			}
		});
	}

	@Override
	public Long rpush(final String key, final String... strings) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.rpush(key, strings);
			}
		});
	}

	@Override
	public Long lpush(final String key, final String... strings) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.lpush(key, strings);
			}
		});
	}

	@Override
	public Long llen(final String key) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.llen(key);
			}
		});
	}

	@Override
	public List<String> lrange(final String key, final long start, final long end) {
		return execute(new ShardedRedisCallback<List<String>>() {
			@Override
			public List<String> execute(ShardedJedis jedis) {
				return jedis.lrange(key, start, end);
			}
		});
	}

	@Override
	public String ltrim(final String key, final long start, final long end) {
		return execute(new ShardedRedisCallback<String>() {
			@Override
			public String execute(ShardedJedis jedis) {
				return jedis.ltrim(key, start, end);
			}
		});
	}

	@Override
	public String lindex(final String key, final long index) {
		return execute(new ShardedRedisCallback<String>() {
			@Override
			public String execute(ShardedJedis jedis) {
				return jedis.lindex(key, index);
			}
		});
	}

	@Override
	public String lset(final String key, final long index, final String value) {
		return execute(new ShardedRedisCallback<String>() {
			@Override
			public String execute(ShardedJedis jedis) {
				return jedis.lset(key, index, value);
			}
		});
	}

	@Override
	public Long lrem(final String key, final long count, final String value) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.lrem(key, count, value);
			}
		});
	}

	@Override
	public String lpop(final String key) {
		return execute(new ShardedRedisCallback<String>() {
			@Override
			public String execute(ShardedJedis jedis) {
				return jedis.lpop(key);
			}
		});
	}

	@Override
	public String rpop(final String key) {
		return execute(new ShardedRedisCallback<String>() {
			@Override
			public String execute(ShardedJedis jedis) {
				return jedis.rpop(key);
			}
		});
	}

	@Override
	public Long sadd(final String key, final String... members) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.sadd(key, members);
			}
		});
	}

	@Override
	public Set<String> smembers(final String key) {
		return execute(new ShardedRedisCallback<Set<String>>() {
			@Override
			public Set<String> execute(ShardedJedis jedis) {
				return jedis.smembers(key);
			}
		});
	}

	@Override
	public Long srem(final String key, final String... members) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.srem(key, members);
			}
		});
	}

	@Override
	public String spop(final String key) {
		return execute(new ShardedRedisCallback<String>() {
			@Override
			public String execute(ShardedJedis jedis) {
				return jedis.spop(key);
			}
		});
	}

	// TODO 测试未通过
	@Override
	public Set<String> spop(final String key, final long count) {
		return execute(new ShardedRedisCallback<Set<String>>() {
			@Override
			public Set<String> execute(ShardedJedis jedis) {
				return jedis.spop(key, count);
			}
		});
	}

	@Override
	public Long scard(final String key) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.scard(key);
			}
		});
	}

	@Override
	public Boolean sismember(final String key, final String member) {
		return execute(new ShardedRedisCallback<Boolean>() {
			@Override
			public Boolean execute(ShardedJedis jedis) {
				return jedis.sismember(key, member);
			}
		});
	}

	@Override
	public String srandmember(final String key) {
		return execute(new ShardedRedisCallback<String>() {
			@Override
			public String execute(ShardedJedis jedis) {
				return jedis.srandmember(key);
			}
		});
	}

	@Override
	public List<String> srandmember(final String key, final int count) {
		return execute(new ShardedRedisCallback<List<String>>() {
			@Override
			public List<String> execute(ShardedJedis jedis) {
				return jedis.srandmember(key, count);
			}
		});
	}

	@Override
	public Long strlen(final String key) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.strlen(key);
			}
		});
	}

	@Override
	public Long zadd(final String key, final double score, final String member) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.zadd(key, score, member);
			}
		});
	}

	@Override
	public Set<String> zrange(final String key, final long start, final long end) {
		return execute(new ShardedRedisCallback<Set<String>>() {
			@Override
			public Set<String> execute(ShardedJedis jedis) {
				return jedis.zrange(key, start, end);
			}
		});
	}

	@Override
	public Long zrem(final String key, final String... members) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.zrem(key, members);
			}
		});
	}

	@Override
	public Double zincrby(final String key, final double score, final String member) {
		return execute(new ShardedRedisCallback<Double>() {
			@Override
			public Double execute(ShardedJedis jedis) {
				return jedis.zincrby(key, score, member);
			}
		});
	}

	@Override
	public Long zrank(final String key, final String member) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.zrank(key, member);
			}
		});
	}

	@Override
	public Long zrevrank(final String key, final String member) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.zrevrank(key, member);
			}
		});
	}

	@Override
	public Set<String> zrevrange(final String key, final long start, final long end) {
		return execute(new ShardedRedisCallback<Set<String>>() {
			@Override
			public Set<String> execute(ShardedJedis jedis) {
				return jedis.zrevrange(key, start, end);
			}
		});
	}

	@Override
	public Set<Tuple> zrangeWithScores(final String key, final long start, final long end) {
		return execute(new ShardedRedisCallback<Set<Tuple>>() {
			@Override
			public Set<Tuple> execute(ShardedJedis jedis) {
				return jedis.zrangeWithScores(key, start, end);
			}
		});
	}

	@Override
	public Set<Tuple> zrevrangeWithScores(final String key, final long start, final long end) {
		return execute(new ShardedRedisCallback<Set<Tuple>>() {
			@Override
			public Set<Tuple> execute(ShardedJedis jedis) {
				return jedis.zrevrangeWithScores(key, start, end);
			}
		});
	}

	@Override
	public Long zcard(final String key) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.zcard(key);
			}
		});
	}

	@Override
	public Double zscore(final String key, final String member) {
		return execute(new ShardedRedisCallback<Double>() {
			@Override
			public Double execute(ShardedJedis jedis) {
				return jedis.zscore(key, member);
			}
		});
	}

	@Override
	public List<String> sort(final String key) {
		return execute(new ShardedRedisCallback<List<String>>() {
			@Override
			public List<String> execute(ShardedJedis jedis) {
				return jedis.sort(key);
			}
		});
	}

	@Override
	public List<String> sort(final String key, final SortingParams sortingParameters) {
		return execute(new ShardedRedisCallback<List<String>>() {
			@Override
			public List<String> execute(ShardedJedis jedis) {
				return jedis.sort(key, sortingParameters);
			}
		});
	}

	@Override
	public Long zcount(final String key, final double min, final double max) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.zcount(key, min, max);
			}
		});
	}

	@Override
	public Long zcount(final String key, final String min, final String max) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.zcount(key, min, max);
			}
		});
	}

	@Override
	public Set<String> zrangeByScore(final String key, final double min, final double max) {
		return execute(new ShardedRedisCallback<Set<String>>() {
			@Override
			public Set<String> execute(ShardedJedis jedis) {
				return jedis.zrangeByScore(key, min, max);
			}
		});
	}

	@Override
	public Set<String> zrangeByScore(final String key, final String min, final String max) {
		return execute(new ShardedRedisCallback<Set<String>>() {
			@Override
			public Set<String> execute(ShardedJedis jedis) {
				return jedis.zrangeByScore(key, min, max);
			}
		});
	}

	@Override
	public Set<String> zrevrangeByScore(final String key, final double max, final double min) {
		return execute(new ShardedRedisCallback<Set<String>>() {
			@Override
			public Set<String> execute(ShardedJedis jedis) {
				return jedis.zrevrangeByScore(key, max, min);
			}
		});
	}

	@Override
	public Set<String> zrangeByScore(final String key, final double min, final double max,
			final int offset, final int count) {
		return execute(new ShardedRedisCallback<Set<String>>() {
			@Override
			public Set<String> execute(ShardedJedis jedis) {
				return jedis.zrangeByScore(key, min, max, offset, count);
			}
		});
	}

	@Override
	public Set<String> zrevrangeByScore(final String key, final String max, final String min) {
		return execute(new ShardedRedisCallback<Set<String>>() {
			@Override
			public Set<String> execute(ShardedJedis jedis) {
				return jedis.zrevrangeByScore(key, max, min);
			}
		});
	}

	@Override
	public Set<String> zrangeByScore(final String key, final String min, final String max,
			final int offset, final int count) {
		return execute(new ShardedRedisCallback<Set<String>>() {
			@Override
			public Set<String> execute(ShardedJedis jedis) {
				return jedis.zrangeByScore(key, min, max, offset, count);
			}
		});
	}

	@Override
	public Set<String> zrevrangeByScore(final String key, final double max, final double min,
			final int offset, final int count) {
		return execute(new ShardedRedisCallback<Set<String>>() {
			@Override
			public Set<String> execute(ShardedJedis jedis) {
				return jedis.zrevrangeByScore(key, max, min, offset, count);
			}
		});
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(final String key, final double min, final double max) {
		return execute(new ShardedRedisCallback<Set<Tuple>>() {
			@Override
			public Set<Tuple> execute(ShardedJedis jedis) {
				return jedis.zrangeByScoreWithScores(key, min, max);
			}
		});
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(final String key, final double max,
			final double min) {
		return execute(new ShardedRedisCallback<Set<Tuple>>() {
			@Override
			public Set<Tuple> execute(ShardedJedis jedis) {
				return jedis.zrevrangeByScoreWithScores(key, max, min);
			}
		});
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(final String key, final double min, final double max,
			final int offset, final int count) {
		return execute(new ShardedRedisCallback<Set<Tuple>>() {
			@Override
			public Set<Tuple> execute(ShardedJedis jedis) {
				return jedis.zrangeByScoreWithScores(key, min, max, offset, count);
			}
		});
	}

	@Override
	public Set<String> zrevrangeByScore(final String key, final String max, final String min,
			final int offset, final int count) {
		return execute(new ShardedRedisCallback<Set<String>>() {
			@Override
			public Set<String> execute(ShardedJedis jedis) {
				return jedis.zrevrangeByScore(key, max, min, offset, count);
			}
		});
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(final String key, final String min, final String max) {
		return execute(new ShardedRedisCallback<Set<Tuple>>() {
			@Override
			public Set<Tuple> execute(ShardedJedis jedis) {
				return jedis.zrangeByScoreWithScores(key, min, max);
			}
		});
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(final String key, final String max,
			final String min) {
		return execute(new ShardedRedisCallback<Set<Tuple>>() {
			@Override
			public Set<Tuple> execute(ShardedJedis jedis) {
				return jedis.zrevrangeByScoreWithScores(key, max, min);
			}
		});
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(final String key, final String min, final String max,
			final int offset, final int count) {
		return execute(new ShardedRedisCallback<Set<Tuple>>() {
			@Override
			public Set<Tuple> execute(ShardedJedis jedis) {
				return jedis.zrangeByScoreWithScores(key, min, max, offset, count);
			}
		});
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(final String key, final double max,
			final double min, final int offset, final int count) {
		return execute(new ShardedRedisCallback<Set<Tuple>>() {
			@Override
			public Set<Tuple> execute(ShardedJedis jedis) {
				return jedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
			}
		});
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(final String key, final String max,
			final String min, final int offset, final int count) {
		return execute(new ShardedRedisCallback<Set<Tuple>>() {
			@Override
			public Set<Tuple> execute(ShardedJedis jedis) {
				return jedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
			}
		});
	}

	@Override
	public Long zremrangeByRank(final String key, final long start, final long end) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.zremrangeByRank(key, start, end);
			}
		});
	}

	@Override
	public Long zremrangeByScore(final String key, final double start, final double end) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.zremrangeByScore(key, start, end);
			}
		});
	}

	@Override
	public Long zremrangeByScore(final String key, final String start, final String end) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.zremrangeByScore(key, start, end);
			}
		});
	}

	@Override
	public Long linsert(final String key, final LIST_POSITION where, final String pivot,
			final String value) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.linsert(key, where, pivot, value);
			}
		});
	}

	@Override
	public Long lpushx(final String key, final String... strings) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.lpushx(key, strings);
			}
		});
	}

	@Override
	public Long rpushx(final String key, final String... strings) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.rpushx(key, strings);
			}
		});
	}

	@Deprecated
	@Override
	public List<String> blpop(final String arg) {
		return execute(new ShardedRedisCallback<List<String>>() {
			@Override
			public List<String> execute(ShardedJedis jedis) {
				return jedis.blpop(arg);
			}
		});
	}

	@Deprecated
	@Override
	public List<String> brpop(final String arg) {
		return execute(new ShardedRedisCallback<List<String>>() {
			@Override
			public List<String> execute(ShardedJedis jedis) {
				return jedis.brpop(arg);
			}
		});
	}

	@Override
	public Long del(final String key) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.del(key);
			}
		});
	}

	@Override
	public String echo(final String string) {
		return execute(new ShardedRedisCallback<String>() {
			@Override
			public String execute(ShardedJedis jedis) {
				return jedis.echo(string);
			}
		});
	}

	@Override
	public Long move(final String key, final int dbIndex) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.move(key, dbIndex);
			}
		});
	}

	@Override
	public Long bitcount(final String key) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.bitcount(key);
			}
		});
	}

	@Override
	public Long bitcount(final String key, final long start, final long end) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.bitcount(key, start, end);
			}
		});
	}

	@Override
	public Long zadd(final String key, final Map<String, Double> scoreMembers) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.zadd(key, scoreMembers);
			}
		});
	}

	// TODO not test yet!!!

	@Override
	public Long zlexcount(final String key, final String min, final String max) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.zlexcount(key, min, max);
			}
		});
	}

	@Override
	public Set<String> zrangeByLex(final String key, final String min, final String max) {
		return execute(new ShardedRedisCallback<Set<String>>() {
			@Override
			public Set<String> execute(ShardedJedis jedis) {
				return jedis.zrangeByLex(key, min, max);
			}
		});
	}

	@Override
	public Set<String> zrangeByLex(final String key, final String min, final String max,
			final int offset, final int count) {
		return execute(new ShardedRedisCallback<Set<String>>() {
			@Override
			public Set<String> execute(ShardedJedis jedis) {
				return jedis.zrangeByLex(key, min, max, offset, count);
			}
		});
	}

	@Override
	public Set<String> zrevrangeByLex(final String key, final String max, final String min) {
		return execute(new ShardedRedisCallback<Set<String>>() {
			@Override
			public Set<String> execute(ShardedJedis jedis) {
				return jedis.zrevrangeByLex(key, max, min);
			}
		});
	}

	@Override
	public Set<String> zrevrangeByLex(final String key, final String max, final String min,
			final int offset, final int count) {
		return execute(new ShardedRedisCallback<Set<String>>() {
			@Override
			public Set<String> execute(ShardedJedis jedis) {
				return jedis.zrevrangeByLex(key, max, min, offset, count);
			}
		});
	}

	@Override
	public Long zremrangeByLex(final String key, final String min, final String max) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.zremrangeByLex(key, max, min);
			}
		});
	}

	@Override
	public List<String> blpop(final int timeout, final String key) {
		return execute(new ShardedRedisCallback<List<String>>() {
			@Override
			public List<String> execute(ShardedJedis jedis) {
				return jedis.blpop(timeout, key);
			}
		});
	}

	@Override
	public List<String> brpop(final int timeout, final String key) {
		return execute(new ShardedRedisCallback<List<String>>() {
			@Override
			public List<String> execute(ShardedJedis jedis) {
				return jedis.blpop(timeout, key);
			}
		});
	}

	@Deprecated
	@Override
	public ScanResult<Entry<String, String>> hscan(final String key, final int cursor) {
		return execute(new ShardedRedisCallback<ScanResult<Entry<String, String>>>() {
			@Override
			public ScanResult<Entry<String, String>> execute(ShardedJedis jedis) {
				return jedis.hscan(key, cursor);
			}
		});
	}

	@Deprecated
	@Override
	public ScanResult<String> sscan(final String key, final int cursor) {
		return execute(new ShardedRedisCallback<ScanResult<String>>() {
			@Override
			public ScanResult<String> execute(ShardedJedis jedis) {
				return jedis.sscan(key, cursor);
			}
		});
	}

	@Deprecated
	@Override
	public ScanResult<Tuple> zscan(final String key, final int cursor) {
		return execute(new ShardedRedisCallback<ScanResult<Tuple>>() {
			@Override
			public ScanResult<Tuple> execute(ShardedJedis jedis) {
				return jedis.zscan(key, cursor);
			}
		});
	}

	@Override
	public ScanResult<Entry<String, String>> hscan(final String key, final String cursor) {
		return execute(new ShardedRedisCallback<ScanResult<Entry<String, String>>>() {
			@Override
			public ScanResult<Entry<String, String>> execute(ShardedJedis jedis) {
				return jedis.hscan(key, cursor);
			}
		});
	}

	@Override
	public ScanResult<String> sscan(final String key, final String cursor) {
		return execute(new ShardedRedisCallback<ScanResult<String>>() {
			@Override
			public ScanResult<String> execute(ShardedJedis jedis) {
				return jedis.sscan(key, cursor);
			}
		});
	}

	@Override
	public ScanResult<Tuple> zscan(final String key, final String cursor) {
		return execute(new ShardedRedisCallback<ScanResult<Tuple>>() {
			@Override
			public ScanResult<Tuple> execute(ShardedJedis jedis) {
				return jedis.zscan(key, cursor);
			}
		});
	}

	@Override
	public Long pfadd(final String key, final String... elements) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.pfadd(key, elements);
			}
		});
	}

	@Override
	public long pfcount(final String key) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.pfcount(key);
			}
		});
	}

	/* ShardedJedisPool is required */
	public void setPool(Pool<ShardedJedis> pool) {
		this.pool = pool;
	}

	/* not finish yet! */

	@Override
	public String set(byte[] key, byte[] value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String set(byte[] key, byte[] value, byte[] nxxx, byte[] expx, long time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] get(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean exists(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long persist(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String type(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long expire(byte[] key, int seconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long pexpire(byte[] key, long milliseconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long expireAt(byte[] key, long unixTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long pexpireAt(byte[] key, long millisecondsTimestamp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long ttl(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean setbit(byte[] key, long offset, boolean value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean setbit(byte[] key, long offset, byte[] value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean getbit(byte[] key, long offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long setrange(byte[] key, long offset, byte[] value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getrange(byte[] key, long startOffset, long endOffset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getSet(byte[] key, byte[] value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long setnx(byte[] key, byte[] value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setex(byte[] key, int seconds, byte[] value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long decrBy(byte[] key, long integer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long decr(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long incrBy(byte[] key, long integer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double incrByFloat(byte[] key, double value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long incr(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long append(byte[] key, byte[] value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] substr(byte[] key, int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long hset(final byte[] key, final byte[] field, final byte[] value) {
		return execute(new ShardedRedisCallback<Long>() {
			@Override
			public Long execute(ShardedJedis jedis) {
				return jedis.hset(key, field, value);
			}
		});
	}

	@Override
	public byte[] hget(final byte[] key, final byte[] field) {
		return execute(new ShardedRedisCallback<byte[]>() {
			@Override
			public byte[] execute(ShardedJedis jedis) {
				return jedis.hget(key, field);
			}
		});
	}

	@Override
	public Long hsetnx(byte[] key, byte[] field, byte[] value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String hmset(byte[] key, Map<byte[], byte[]> hash) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<byte[]> hmget(byte[] key, byte[]... fields) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long hincrBy(byte[] key, byte[] field, long value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double hincrByFloat(byte[] key, byte[] field, double value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean hexists(byte[] key, byte[] field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long hdel(byte[] key, byte[]... field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long hlen(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<byte[]> hkeys(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<byte[]> hvals(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<byte[], byte[]> hgetAll(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long rpush(byte[] key, byte[]... args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long lpush(byte[] key, byte[]... args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long llen(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<byte[]> lrange(byte[] key, long start, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String ltrim(byte[] key, long start, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] lindex(byte[] key, long index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String lset(byte[] key, long index, byte[] value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long lrem(byte[] key, long count, byte[] value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] lpop(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] rpop(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long sadd(byte[] key, byte[]... member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<byte[]> smembers(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long srem(byte[] key, byte[]... member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] spop(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<byte[]> spop(byte[] key, long count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long scard(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean sismember(byte[] key, byte[] member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] srandmember(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<byte[]> srandmember(byte[] key, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long strlen(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zadd(byte[] key, double score, byte[] member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zadd(byte[] key, Map<byte[], Double> scoreMembers) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<byte[]> zrange(byte[] key, long start, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zrem(byte[] key, byte[]... member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double zincrby(byte[] key, double score, byte[] member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zrank(byte[] key, byte[] member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zrevrank(byte[] key, byte[] member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<byte[]> zrevrange(byte[] key, long start, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Tuple> zrangeWithScores(byte[] key, long start, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Tuple> zrevrangeWithScores(byte[] key, long start, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zcard(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double zscore(byte[] key, byte[] member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<byte[]> sort(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<byte[]> sort(byte[] key, SortingParams sortingParameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zcount(byte[] key, double min, double max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zcount(byte[] key, byte[] min, byte[] max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<byte[]> zrangeByScore(byte[] key, double min, double max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<byte[]> zrangeByScore(byte[] key, double min, double max, int offset, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max, int offset, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min, int offset, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max, int offset,
			int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min, int offset, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, byte[] max, byte[] min) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max, int offset,
			int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min, int offset,
			int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, byte[] max, byte[] min, int offset,
			int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zremrangeByRank(byte[] key, long start, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zremrangeByScore(byte[] key, double start, double end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zremrangeByScore(byte[] key, byte[] start, byte[] end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zlexcount(byte[] key, byte[] min, byte[] max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<byte[]> zrangeByLex(byte[] key, byte[] min, byte[] max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<byte[]> zrangeByLex(byte[] key, byte[] min, byte[] max, int offset, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<byte[]> zrevrangeByLex(byte[] key, byte[] max, byte[] min) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<byte[]> zrevrangeByLex(byte[] key, byte[] max, byte[] min, int offset, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zremrangeByLex(byte[] key, byte[] min, byte[] max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long linsert(byte[] key, LIST_POSITION where, byte[] pivot, byte[] value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long lpushx(byte[] key, byte[]... arg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long rpushx(byte[] key, byte[]... arg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<byte[]> blpop(byte[] arg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<byte[]> brpop(byte[] arg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long del(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] echo(byte[] arg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long move(byte[] key, int dbIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long bitcount(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long bitcount(byte[] key, long start, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long pfadd(byte[] key, byte[]... elements) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long pfcount(byte[] key) {
		// TODO Auto-generated method stub
		return 0;
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(RedisShardedImpl.class);
}
