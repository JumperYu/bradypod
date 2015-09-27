package com.bradypod.util.redis;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;

import com.bradypod.util.redis.invoker.ShardedRedisCallback;

public abstract class ShardedRedisTemplate {

	protected abstract <T> T execute(final ShardedRedisCallback<T> callback);

	public String set(final String key, final String value) {
		return execute(new ShardedRedisCallback<String>() {

			public String execute(ShardedJedis jedis) {
				return jedis.set(key, value);
			}
		});
	}

	public String set(final String key, final String value, final String nxxx,
			final String expx, final long time) {
		return execute(new ShardedRedisCallback<String>() {

			public String execute(ShardedJedis jedis) {
				return jedis.set(key, value, nxxx, expx, time);
			}
		});
	}

	public String get(final String key) {
		return execute(new ShardedRedisCallback<String>() {

			public String execute(ShardedJedis jedis) {
				return jedis.get(key);
			}
		});
	}

	public Boolean exists(final String key) {
		return execute(new ShardedRedisCallback<Boolean>() {

			public Boolean execute(ShardedJedis jedis) {
				return jedis.exists(key);
			}
		});
	}

	public Long persist(final String key) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.persist(key);
			}
		});
	}

	public String type(final String key) {
		return execute(new ShardedRedisCallback<String>() {

			public String execute(ShardedJedis jedis) {
				return jedis.type(key);
			}
		});
	}

	public Long expire(final String key, final int seconds) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.expire(key, seconds);
			}
		});
	}

	public Long pexpire(final String key, final long milliseconds) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.pexpire(key, milliseconds);
			}
		});
	}

	public Long expireAt(final String key, final long unixTime) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.expireAt(key, unixTime);
			}
		});
	}

	public Long pexpireAt(final String key, final long millisecondsTimestamp) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.pexpireAt(key, millisecondsTimestamp);
			}
		});
	}

	public Long ttl(final String key) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.ttl(key);
			}
		});
	}

	public Boolean setbit(final String key, final long offset,
			final boolean value) {
		return execute(new ShardedRedisCallback<Boolean>() {

			public Boolean execute(ShardedJedis jedis) {
				return jedis.setbit(key, offset, value);
			}
		});
	}

	public Boolean setbit(final String key, final long offset,
			final String value) {
		return execute(new ShardedRedisCallback<Boolean>() {

			public Boolean execute(ShardedJedis jedis) {
				return jedis.setbit(key, offset, value);
			}
		});
	}

	public Boolean getbit(final String key, final long offset) {
		return execute(new ShardedRedisCallback<Boolean>() {

			public Boolean execute(ShardedJedis jedis) {
				return jedis.getbit(key, offset);
			}
		});
	}

	public Long setrange(final String key, final long offset, final String value) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.setrange(key, offset, value);
			}
		});
	}

	public String getrange(final String key, final long startOffset,
			final long endOffset) {
		return execute(new ShardedRedisCallback<String>() {

			public String execute(ShardedJedis jedis) {
				return jedis.getrange(key, startOffset, endOffset);
			}
		});
	}

	public String getSet(final String key, final String value) {
		return execute(new ShardedRedisCallback<String>() {

			public String execute(ShardedJedis jedis) {
				return jedis.getSet(key, value);
			}
		});
	}

	public Long setnx(final String key, final String value) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.setnx(key, value);
			}
		});
	}

	public String setex(final String key, final int seconds, final String value) {
		return execute(new ShardedRedisCallback<String>() {

			public String execute(ShardedJedis jedis) {
				return jedis.setex(key, seconds, value);
			}
		});
	}

	public Long decrBy(final String key, final long integer) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.decrBy(key, integer);
			}
		});
	}

	public Long decr(final String key) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.decr(key);
			}
		});
	}

	public Long incrBy(final String key, final long integer) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.incrBy(key, integer);
			}
		});
	}

	public Long incr(final String key) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.incr(key);
			}
		});
	}

	public Double incrByFloat(final String key, final double value) {
		return execute(new ShardedRedisCallback<Double>() {

			public Double execute(ShardedJedis jedis) {
				return jedis.incrByFloat(key, value);
			}
		});
	}

	public Long append(final String key, final String value) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.append(key, value);
			}
		});
	}

	public String substr(final String key, final int start, final int end) {
		return execute(new ShardedRedisCallback<String>() {

			public String execute(ShardedJedis jedis) {
				return jedis.substr(key, start, end);
			}
		});
	}

	public Long hset(final String key, final String field, final String value) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.hset(key, field, value);
			}
		});
	}

	public String hget(final String key, final String field) {
		return execute(new ShardedRedisCallback<String>() {

			public String execute(ShardedJedis jedis) {
				return jedis.hget(key, field);
			}
		});
	}

	public Long hsetnx(final String key, final String field, final String value) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.hsetnx(key, field, value);
			}
		});
	}

	public String hmset(final String key, final Map<String, String> hash) {
		return execute(new ShardedRedisCallback<String>() {

			public String execute(ShardedJedis jedis) {
				return jedis.hmset(key, hash);
			}
		});
	}

	public List<String> hmget(final String key, final String... fields) {
		return execute(new ShardedRedisCallback<List<String>>() {

			public List<String> execute(ShardedJedis jedis) {
				return jedis.hmget(key, fields);
			}
		});
	}

	public Long hincrBy(final String key, final String field, final long value) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.hincrBy(key, field, value);
			}
		});
	}

	public Boolean hexists(final String key, final String field) {
		return execute(new ShardedRedisCallback<Boolean>() {

			public Boolean execute(ShardedJedis jedis) {
				return jedis.hexists(key, field);
			}
		});
	}

	public Long hdel(final String key, final String... fields) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.hdel(key, fields);
			}
		});
	}

	public Long hlen(final String key) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.hlen(key);
			}
		});
	}

	public Set<String> hkeys(final String key) {
		return execute(new ShardedRedisCallback<Set<String>>() {

			public Set<String> execute(ShardedJedis jedis) {
				return jedis.hkeys(key);
			}
		});
	}

	public List<String> hvals(final String key) {
		return execute(new ShardedRedisCallback<List<String>>() {

			public List<String> execute(ShardedJedis jedis) {
				return jedis.hvals(key);
			}
		});
	}

	public Map<String, String> hgetAll(final String key) {
		return execute(new ShardedRedisCallback<Map<String, String>>() {

			public Map<String, String> execute(ShardedJedis jedis) {
				return jedis.hgetAll(key);
			}
		});
	}

	public Long rpush(final String key, final String... strings) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.rpush(key, strings);
			}
		});
	}

	public Long lpush(final String key, final String... strings) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.lpush(key, strings);
			}
		});
	}

	public Long llen(final String key) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.llen(key);
			}
		});
	}

	public List<String> lrange(final String key, final long start,
			final long end) {
		return execute(new ShardedRedisCallback<List<String>>() {

			public List<String> execute(ShardedJedis jedis) {
				return jedis.lrange(key, start, end);
			}
		});
	}

	public String ltrim(final String key, final long start, final long end) {
		return execute(new ShardedRedisCallback<String>() {

			public String execute(ShardedJedis jedis) {
				return jedis.ltrim(key, start, end);
			}
		});
	}

	public String lindex(final String key, final long index) {
		return execute(new ShardedRedisCallback<String>() {

			public String execute(ShardedJedis jedis) {
				return jedis.lindex(key, index);
			}
		});
	}

	public String lset(final String key, final long index, final String value) {
		return execute(new ShardedRedisCallback<String>() {

			public String execute(ShardedJedis jedis) {
				return jedis.lset(key, index, value);
			}
		});
	}

	public Long lrem(final String key, final long count, final String value) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.lrem(key, count, value);
			}
		});
	}

	public String lpop(final String key) {
		return execute(new ShardedRedisCallback<String>() {

			public String execute(ShardedJedis jedis) {
				return jedis.lpop(key);
			}
		});
	}

	public String rpop(final String key) {
		return execute(new ShardedRedisCallback<String>() {

			public String execute(ShardedJedis jedis) {
				return jedis.rpop(key);
			}
		});
	}

	public Long sadd(final String key, final String... members) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.sadd(key, members);
			}
		});
	}

	public Set<String> smembers(final String key) {
		return execute(new ShardedRedisCallback<Set<String>>() {

			public Set<String> execute(ShardedJedis jedis) {
				return jedis.smembers(key);
			}
		});
	}

	public Long srem(final String key, final String... members) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.srem(key, members);
			}
		});
	}

	public String spop(final String key) {
		return execute(new ShardedRedisCallback<String>() {

			public String execute(ShardedJedis jedis) {
				return jedis.spop(key);
			}
		});
	}

	// TODO 测试未通过

	public Set<String> spop(final String key, final long count) {
		return execute(new ShardedRedisCallback<Set<String>>() {

			public Set<String> execute(ShardedJedis jedis) {
				return jedis.spop(key, count);
			}
		});
	}

	public Long scard(final String key) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.scard(key);
			}
		});
	}

	public Boolean sismember(final String key, final String member) {
		return execute(new ShardedRedisCallback<Boolean>() {

			public Boolean execute(ShardedJedis jedis) {
				return jedis.sismember(key, member);
			}
		});
	}

	public String srandmember(final String key) {
		return execute(new ShardedRedisCallback<String>() {

			public String execute(ShardedJedis jedis) {
				return jedis.srandmember(key);
			}
		});
	}

	public List<String> srandmember(final String key, final int count) {
		return execute(new ShardedRedisCallback<List<String>>() {

			public List<String> execute(ShardedJedis jedis) {
				return jedis.srandmember(key, count);
			}
		});
	}

	public Long strlen(final String key) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.strlen(key);
			}
		});
	}

	public Long zadd(final String key, final double score, final String member) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.zadd(key, score, member);
			}
		});
	}

	public Set<String> zrange(final String key, final long start, final long end) {
		return execute(new ShardedRedisCallback<Set<String>>() {

			public Set<String> execute(ShardedJedis jedis) {
				return jedis.zrange(key, start, end);
			}
		});
	}

	public Long zrem(final String key, final String... members) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.zrem(key, members);
			}
		});
	}

	public Double zincrby(final String key, final double score,
			final String member) {
		return execute(new ShardedRedisCallback<Double>() {

			public Double execute(ShardedJedis jedis) {
				return jedis.zincrby(key, score, member);
			}
		});
	}

	public Long zrank(final String key, final String member) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.zrank(key, member);
			}
		});
	}

	public Long zrevrank(final String key, final String member) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.zrevrank(key, member);
			}
		});
	}

	public Set<String> zrevrange(final String key, final long start,
			final long end) {
		return execute(new ShardedRedisCallback<Set<String>>() {

			public Set<String> execute(ShardedJedis jedis) {
				return jedis.zrevrange(key, start, end);
			}
		});
	}

	public Set<Tuple> zrangeWithScores(final String key, final long start,
			final long end) {
		return execute(new ShardedRedisCallback<Set<Tuple>>() {

			public Set<Tuple> execute(ShardedJedis jedis) {
				return jedis.zrangeWithScores(key, start, end);
			}
		});
	}

	public Set<Tuple> zrevrangeWithScores(final String key, final long start,
			final long end) {
		return execute(new ShardedRedisCallback<Set<Tuple>>() {

			public Set<Tuple> execute(ShardedJedis jedis) {
				return jedis.zrevrangeWithScores(key, start, end);
			}
		});
	}

	public Long zcard(final String key) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.zcard(key);
			}
		});
	}

	public Double zscore(final String key, final String member) {
		return execute(new ShardedRedisCallback<Double>() {

			public Double execute(ShardedJedis jedis) {
				return jedis.zscore(key, member);
			}
		});
	}

	public List<String> sort(final String key) {
		return execute(new ShardedRedisCallback<List<String>>() {

			public List<String> execute(ShardedJedis jedis) {
				return jedis.sort(key);
			}
		});
	}

	public List<String> sort(final String key,
			final SortingParams sortingParameters) {
		return execute(new ShardedRedisCallback<List<String>>() {

			public List<String> execute(ShardedJedis jedis) {
				return jedis.sort(key, sortingParameters);
			}
		});
	}

	public Long zcount(final String key, final double min, final double max) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.zcount(key, min, max);
			}
		});
	}

	public Long zcount(final String key, final String min, final String max) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.zcount(key, min, max);
			}
		});
	}

	public Set<String> zrangeByScore(final String key, final double min,
			final double max) {
		return execute(new ShardedRedisCallback<Set<String>>() {

			public Set<String> execute(ShardedJedis jedis) {
				return jedis.zrangeByScore(key, min, max);
			}
		});
	}

	public Set<String> zrangeByScore(final String key, final String min,
			final String max) {
		return execute(new ShardedRedisCallback<Set<String>>() {

			public Set<String> execute(ShardedJedis jedis) {
				return jedis.zrangeByScore(key, min, max);
			}
		});
	}

	public Set<String> zrevrangeByScore(final String key, final double max,
			final double min) {
		return execute(new ShardedRedisCallback<Set<String>>() {

			public Set<String> execute(ShardedJedis jedis) {
				return jedis.zrevrangeByScore(key, max, min);
			}
		});
	}

	public Set<String> zrangeByScore(final String key, final double min,
			final double max, final int offset, final int count) {
		return execute(new ShardedRedisCallback<Set<String>>() {

			public Set<String> execute(ShardedJedis jedis) {
				return jedis.zrangeByScore(key, min, max, offset, count);
			}
		});
	}

	public Set<String> zrevrangeByScore(final String key, final String max,
			final String min) {
		return execute(new ShardedRedisCallback<Set<String>>() {

			public Set<String> execute(ShardedJedis jedis) {
				return jedis.zrevrangeByScore(key, max, min);
			}
		});
	}

	public Set<String> zrangeByScore(final String key, final String min,
			final String max, final int offset, final int count) {
		return execute(new ShardedRedisCallback<Set<String>>() {

			public Set<String> execute(ShardedJedis jedis) {
				return jedis.zrangeByScore(key, min, max, offset, count);
			}
		});
	}

	public Set<String> zrevrangeByScore(final String key, final double max,
			final double min, final int offset, final int count) {
		return execute(new ShardedRedisCallback<Set<String>>() {

			public Set<String> execute(ShardedJedis jedis) {
				return jedis.zrevrangeByScore(key, max, min, offset, count);
			}
		});
	}

	public Set<Tuple> zrangeByScoreWithScores(final String key,
			final double min, final double max) {
		return execute(new ShardedRedisCallback<Set<Tuple>>() {

			public Set<Tuple> execute(ShardedJedis jedis) {
				return jedis.zrangeByScoreWithScores(key, min, max);
			}
		});
	}

	public Set<Tuple> zrevrangeByScoreWithScores(final String key,
			final double max, final double min) {
		return execute(new ShardedRedisCallback<Set<Tuple>>() {

			public Set<Tuple> execute(ShardedJedis jedis) {
				return jedis.zrevrangeByScoreWithScores(key, max, min);
			}
		});
	}

	public Set<Tuple> zrangeByScoreWithScores(final String key,
			final double min, final double max, final int offset,
			final int count) {
		return execute(new ShardedRedisCallback<Set<Tuple>>() {

			public Set<Tuple> execute(ShardedJedis jedis) {
				return jedis.zrangeByScoreWithScores(key, min, max, offset,
						count);
			}
		});
	}

	public Set<String> zrevrangeByScore(final String key, final String max,
			final String min, final int offset, final int count) {
		return execute(new ShardedRedisCallback<Set<String>>() {

			public Set<String> execute(ShardedJedis jedis) {
				return jedis.zrevrangeByScore(key, max, min, offset, count);
			}
		});
	}

	public Set<Tuple> zrangeByScoreWithScores(final String key,
			final String min, final String max) {
		return execute(new ShardedRedisCallback<Set<Tuple>>() {

			public Set<Tuple> execute(ShardedJedis jedis) {
				return jedis.zrangeByScoreWithScores(key, min, max);
			}
		});
	}

	public Set<Tuple> zrevrangeByScoreWithScores(final String key,
			final String max, final String min) {
		return execute(new ShardedRedisCallback<Set<Tuple>>() {

			public Set<Tuple> execute(ShardedJedis jedis) {
				return jedis.zrevrangeByScoreWithScores(key, max, min);
			}
		});
	}

	public Set<Tuple> zrangeByScoreWithScores(final String key,
			final String min, final String max, final int offset,
			final int count) {
		return execute(new ShardedRedisCallback<Set<Tuple>>() {

			public Set<Tuple> execute(ShardedJedis jedis) {
				return jedis.zrangeByScoreWithScores(key, min, max, offset,
						count);
			}
		});
	}

	public Set<Tuple> zrevrangeByScoreWithScores(final String key,
			final double max, final double min, final int offset,
			final int count) {
		return execute(new ShardedRedisCallback<Set<Tuple>>() {

			public Set<Tuple> execute(ShardedJedis jedis) {
				return jedis.zrevrangeByScoreWithScores(key, max, min, offset,
						count);
			}
		});
	}

	public Set<Tuple> zrevrangeByScoreWithScores(final String key,
			final String max, final String min, final int offset,
			final int count) {
		return execute(new ShardedRedisCallback<Set<Tuple>>() {

			public Set<Tuple> execute(ShardedJedis jedis) {
				return jedis.zrevrangeByScoreWithScores(key, max, min, offset,
						count);
			}
		});
	}

	public Long zremrangeByRank(final String key, final long start,
			final long end) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.zremrangeByRank(key, start, end);
			}
		});
	}

	public Long zremrangeByScore(final String key, final double start,
			final double end) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.zremrangeByScore(key, start, end);
			}
		});
	}

	public Long zremrangeByScore(final String key, final String start,
			final String end) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.zremrangeByScore(key, start, end);
			}
		});
	}

	public Long linsert(final String key, final LIST_POSITION where,
			final String pivot, final String value) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.linsert(key, where, pivot, value);
			}
		});
	}

	public Long lpushx(final String key, final String... strings) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.lpushx(key, strings);
			}
		});
	}

	public Long rpushx(final String key, final String... strings) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.rpushx(key, strings);
			}
		});
	}

	@Deprecated
	public List<String> blpop(final String arg) {
		return execute(new ShardedRedisCallback<List<String>>() {

			public List<String> execute(ShardedJedis jedis) {
				return jedis.blpop(arg);
			}
		});
	}

	@Deprecated
	public List<String> brpop(final String arg) {
		return execute(new ShardedRedisCallback<List<String>>() {

			public List<String> execute(ShardedJedis jedis) {
				return jedis.brpop(arg);
			}
		});
	}

	public Long del(final String key) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.del(key);
			}
		});
	}

	public String echo(final String string) {
		return execute(new ShardedRedisCallback<String>() {

			public String execute(ShardedJedis jedis) {
				return jedis.echo(string);
			}
		});
	}

	public Long move(final String key, final int dbIndex) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.move(key, dbIndex);
			}
		});
	}

	public Long bitcount(final String key) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.bitcount(key);
			}
		});
	}

	public Long bitcount(final String key, final long start, final long end) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.bitcount(key, start, end);
			}
		});
	}

	public Long zadd(final String key, final Map<String, Double> scoreMembers) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.zadd(key, scoreMembers);
			}
		});
	}

	// TODO not test yet!!!

	public Long zlexcount(final String key, final String min, final String max) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.zlexcount(key, min, max);
			}
		});
	}

	public Set<String> zrangeByLex(final String key, final String min,
			final String max) {
		return execute(new ShardedRedisCallback<Set<String>>() {

			public Set<String> execute(ShardedJedis jedis) {
				return jedis.zrangeByLex(key, min, max);
			}
		});
	}

	public Set<String> zrangeByLex(final String key, final String min,
			final String max, final int offset, final int count) {
		return execute(new ShardedRedisCallback<Set<String>>() {

			public Set<String> execute(ShardedJedis jedis) {
				return jedis.zrangeByLex(key, min, max, offset, count);
			}
		});
	}

	public Set<String> zrevrangeByLex(final String key, final String max,
			final String min) {
		return execute(new ShardedRedisCallback<Set<String>>() {

			public Set<String> execute(ShardedJedis jedis) {
				return jedis.zrevrangeByLex(key, max, min);
			}
		});
	}

	public Set<String> zrevrangeByLex(final String key, final String max,
			final String min, final int offset, final int count) {
		return execute(new ShardedRedisCallback<Set<String>>() {

			public Set<String> execute(ShardedJedis jedis) {
				return jedis.zrevrangeByLex(key, max, min, offset, count);
			}
		});
	}

	public Long zremrangeByLex(final String key, final String min,
			final String max) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.zremrangeByLex(key, max, min);
			}
		});
	}

	public List<String> blpop(final int timeout, final String key) {
		return execute(new ShardedRedisCallback<List<String>>() {

			public List<String> execute(ShardedJedis jedis) {
				return jedis.blpop(timeout, key);
			}
		});
	}

	public List<String> brpop(final int timeout, final String key) {
		return execute(new ShardedRedisCallback<List<String>>() {

			public List<String> execute(ShardedJedis jedis) {
				return jedis.blpop(timeout, key);
			}
		});
	}

	@Deprecated
	public ScanResult<Entry<String, String>> hscan(final String key,
			final int cursor) {
		return execute(new ShardedRedisCallback<ScanResult<Entry<String, String>>>() {

			public ScanResult<Entry<String, String>> execute(ShardedJedis jedis) {
				return jedis.hscan(key, cursor);
			}
		});
	}

	@Deprecated
	public ScanResult<String> sscan(final String key, final int cursor) {
		return execute(new ShardedRedisCallback<ScanResult<String>>() {

			public ScanResult<String> execute(ShardedJedis jedis) {
				return jedis.sscan(key, cursor);
			}
		});
	}

	@Deprecated
	public ScanResult<Tuple> zscan(final String key, final int cursor) {
		return execute(new ShardedRedisCallback<ScanResult<Tuple>>() {

			public ScanResult<Tuple> execute(ShardedJedis jedis) {
				return jedis.zscan(key, cursor);
			}
		});
	}

	public ScanResult<Entry<String, String>> hscan(final String key,
			final String cursor) {
		return execute(new ShardedRedisCallback<ScanResult<Entry<String, String>>>() {

			public ScanResult<Entry<String, String>> execute(ShardedJedis jedis) {
				return jedis.hscan(key, cursor);
			}
		});
	}

	public ScanResult<String> sscan(final String key, final String cursor) {
		return execute(new ShardedRedisCallback<ScanResult<String>>() {

			public ScanResult<String> execute(ShardedJedis jedis) {
				return jedis.sscan(key, cursor);
			}
		});
	}

	public ScanResult<Tuple> zscan(final String key, final String cursor) {
		return execute(new ShardedRedisCallback<ScanResult<Tuple>>() {

			public ScanResult<Tuple> execute(ShardedJedis jedis) {
				return jedis.zscan(key, cursor);
			}
		});
	}

	public Long pfadd(final String key, final String... elements) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.pfadd(key, elements);
			}
		});
	}

	public long pfcount(final String key) {
		return execute(new ShardedRedisCallback<Long>() {

			public Long execute(ShardedJedis jedis) {
				return jedis.pfcount(key);
			}
		});
	}
}
