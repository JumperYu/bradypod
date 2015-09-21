package com.bradypod.util.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Client;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;

/**
 * 重写RedisCommand
 *
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015年9月21日 上午10:51:12
 */
public interface Redis extends JedisCommands {

	/**
	 * 设置key的value <br/>
	 * 成功返回状态码OK
	 */
	@Override
	String set(String key, String value);

	/**
	 * 设置key的value <br/>
	 * 设置触发条件 <b>nx</b>(key is not exist) | <b>xx</b>(key is exist) <br/>
	 * 设置失效时间 <b>ex</b>(seconds) | <b>px</b>(millionseconds) <br/>
	 * 成功返回状态码OK
	 */
	@Override
	String set(String key, String value, String nxxx, String expx, long time);

	/**
	 * 返回key对应value
	 */
	@Override
	String get(String key);

	/**
	 * 返回key是否存在
	 */
	@Override
	Boolean exists(String key);

	/**
	 * 持久化, 失效时间设置为-1 <br/>
	 * 返回1设置成功, 0失败(原因是key不存在)
	 */
	@Override
	Long persist(String key);

	/**
	 * 返回key的类型 <br/>
	 * type : none, string, list, set, zset, hash
	 */
	@Override
	String type(String key);

	/**
	 * 设置key的失效时间(秒) 返回1设置成功, 0失败
	 */
	@Override
	Long expire(String key, int seconds);

	/**
	 * 设置key的失效时间(毫秒) 返回1设置成功, 0失败
	 */
	@Override
	Long pexpire(String key, long milliseconds);

	/**
	 * 设置key的失效时间点(uninx时间戳) 返回1设置成功, 0失败
	 */
	@Override
	Long expireAt(String key, long unixTime);

	/**
	 * 设置key的失效时间点(毫秒时间戳) 返回1设置成功, 0失败
	 */
	@Override
	Long pexpireAt(String key, long millisecondsTimestamp);

	/**
	 * time to live <br/>
	 * 返回剩余时间(秒), 或 -1 如果key和失效无关, -2 如果key不存在
	 */
	@Override
	Long ttl(String key);

	/**
	 * 设置key的偏移量 not test
	 */
	@Override
	Boolean setbit(String key, long offset, boolean value);

	/**
	 * 设置key的偏移量的值 not test
	 * 
	 */
	@Override
	Boolean setbit(String key, long offset, String value);

	/**
	 * not test
	 */
	@Override
	Boolean getbit(String key, long offset);

	/**
	 * 重新覆盖key的值, 从偏移量开始 <br/>
	 * 返回被修改后字符串长度
	 */
	@Override
	Long setrange(String key, long offset, String value);

	/**
	 * 返回key的值, 从startOffset开始, endOffset结束的字符串 <br/>
	 * 0和-1 代表开始和结束
	 */
	@Override
	String getrange(String key, long startOffset, long endOffset);

	/**
	 * 获取旧值并且设置新的值 <br/>
	 * 如果不存在返回null
	 */
	@Override
	String getSet(String key, String value);

	/**
	 * 当不存在key的时候才设置 <br/>
	 * 
	 * @see set(String key, String value, String nx, String expx, long time); <br/>
	 *      返回0表示没有设置
	 */
	@Override
	Long setnx(String key, String value);

	/**
	 * 当存在key的时候才设置 <br/>
	 * 
	 * @see set(String key, String value, String xx, String expx, long time); <br/>
	 *      status code OK
	 */
	@Override
	String setex(String key, int seconds, String value);

	/**
	 * 指定key减去指定的值 <br/>
	 * 返回最后的值
	 */
	@Override
	Long decrBy(String key, long integer);

	/**
	 * 自减
	 */
	Long decr(String key);

	/**
	 * 指定key加上指定的值 <br/>
	 * 返回最后的值
	 */
	@Override
	Long incrBy(String key, long integer);

	/**
	 * 浮点类型相加 <br/>
	 * 返回Double类型的值
	 */
	Double incrByFloat(String key, double value);

	/**
	 * 自增
	 */
	Long incr(String key);

	/**
	 * 拼接 <br/>
	 * 返回拼接后字符串的长度
	 */
	Long append(String key, String value);

	/**
	 * 返回截取的字符串
	 */
	String substr(String key, int start, int end);

	/**
	 * 指定key为map里field的值 <br/>
	 * 返回1成功, 0表示已存在并且成功更新
	 */
	Long hset(String key, String field, String value);

	/**
	 * 返回对应的map里field的值
	 */
	String hget(String key, String field);

	/**
	 * 当不存在的时候设置
	 */
	Long hsetnx(String key, String field, String value);

	/**
	 * 设置一个Map
	 */
	String hmset(String key, Map<String, String> hash);

	/**
	 * 获取map里多个field的值
	 */
	List<String> hmget(String key, String... fields);

	/**
	 * 对应键的值加上指定值
	 */
	Long hincrBy(String key, String field, long value);

	/**
	 * 是否存在对应的键
	 */
	Boolean hexists(String key, String field);

	/**
	 * 删除对应的键 <br/>
	 * 返回0则失效
	 */
	Long hdel(String key, String... field);

	/**
	 * 哈希表大小
	 */
	Long hlen(String key);

	/**
	 * 相当于Map.keySet
	 */
	Set<String> hkeys(String key);

	/**
	 * 和keys相反, 返回所有values
	 */
	List<String> hvals(String key);

	/**
	 * 获取整个哈希表, 时间复杂度O(N), N为fields的大小
	 */
	Map<String, String> hgetAll(String key);

	/**
	 * 从右边插入
	 */
	Long rpush(String key, String... string);

	/**
	 * 从左边插入
	 */
	Long lpush(String key, String... string);

	/**
	 * 数组长度
	 */
	Long llen(String key);

	/**
	 * 获取指定长度的数组
	 */
	List<String> lrange(String key, long start, long end);

	/**
	 * 指定保留的返回, 格式化其余的个数, O(N), N是需要移动的个数 <br/>
	 * 返回 OK
	 */
	String ltrim(String key, long start, long end);

	/**
	 * 相当于list.get(index)
	 */
	String lindex(String key, long index);

	/**
	 * 相当于list.set(index, value)
	 */
	String lset(String key, long index, String value);

	/**
	 * 等价于contains(value) + list.remove(index) <br/>
	 * index小于0 表示从尾到头, index大于0 表示从头到尾,等于0表示全部 <br/>
	 * value表示匹配的值
	 */
	Long lrem(String key, long count, String value);

	/**
	 * 删除并返回第一个数组的值
	 */
	String lpop(String key);

	/**
	 * 删除并返回最后一个数组的值
	 */
	String rpop(String key);

	/**
	 * set插入
	 */
	Long sadd(String key, String... member);

	/**
	 * 返回所有的成员
	 */
	Set<String> smembers(String key);

	/**
	 * 删除成员
	 */
	Long srem(String key, String... member);

	/**
	 * 随机返回一个成员
	 */
	String spop(String key);

	/**
	 * 随机返回指定个数成员 <br/>
	 * TODO 测试未通过
	 */
	Set<String> spop(String key, long count);

	/**
	 * 返回set的大小
	 */
	Long scard(String key);

	/**
	 * 判断是否是成员
	 */
	Boolean sismember(String key, String member);

	/**
	 * 随机返回一个成员
	 */
	String srandmember(String key);

	/**
	 * 随机返回多个成员
	 */
	List<String> srandmember(String key, int count);

	/**
	 * 返回key的长度
	 */
	Long strlen(String key);

	/* command since redis version 3.x */

	/**
	 * 指定集合的成员的记分, 用于排序 <br/>
	 * 相同分数的成员,按字符分割逐个比较
	 */
	Long zadd(String key, double score, String member);

	/**
	 * 同时插入多个
	 */
	Long zadd(String key, Map<String, Double> scoreMembers);

	/**
	 * 返回范围内的集合
	 */
	Set<String> zrange(String key, long start, long end);

	/**
	 * 删除指定的成员
	 */
	Long zrem(String key, String... member);

	/**
	 * 增加成员的记分
	 */
	Double zincrby(String key, double score, String member);

	/**
	 * 返回序列号, 从低到高 相当于indexOfKey, index从0开始
	 */
	Long zrank(String key, String member);

	/**
	 * 返回序列号, 从高到低, 相当于indexOfKey, index从0开始
	 */
	Long zrevrank(String key, String member);

	/**
	 * 从高到低
	 */
	Set<String> zrevrange(String key, long start, long end);

	/**
	 * 取出集合范围内的数据且含有得分, 相同得分允许重复
	 */
	Set<Tuple> zrangeWithScores(String key, long start, long end);

	/**
	 * 和zrang相反的顺序
	 */
	Set<Tuple> zrevrangeWithScores(String key, long start, long end);

	/**
	 * 集合大小
	 */
	Long zcard(String key);

	/**
	 * 获取成员的记分
	 */
	Double zscore(String key, String member);

	List<String> sort(String key);

	List<String> sort(String key, SortingParams sortingParameters);

	/**
	 * 统计集合内分数在min和max间的个数
	 */
	Long zcount(String key, double min, double max);

	/**
	 * 统计集合内分数在min和max间的个数
	 */
	Long zcount(String key, String min, String max);

	/**
	 * 返回集合内分数在min和max间的集合
	 */
	Set<String> zrangeByScore(String key, double min, double max);

	/**
	 * 返回集合内分数在min和max间的集合
	 */
	Set<String> zrangeByScore(String key, String min, String max);

	/**
	 * 以由高到低的顺序返回集合内分数在min和max间的集合
	 */
	Set<String> zrevrangeByScore(String key, double max, double min);

	/**
	 * 返回符合条件的集合
	 */
	Set<String> zrangeByScore(String key, double min, double max, int offset, int count);

	/**
	 * 以由高到低的顺序返回符合条件的集合
	 */
	Set<String> zrevrangeByScore(String key, String max, String min);

	/**
	 * 返回符合条件的集合
	 */
	Set<String> zrangeByScore(String key, String min, String max, int offset, int count);

	/**
	 * 以由高到低的顺序返回符合条件的集合
	 */
	Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count);

	/* not test */

	Set<Tuple> zrangeByScoreWithScores(String key, double min, double max);

	Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min);

	Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count);

	Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count);

	Set<Tuple> zrangeByScoreWithScores(String key, String min, String max);

	Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min);

	Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count);

	Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count);

	Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count);

	Long zremrangeByRank(String key, long start, long end);

	Long zremrangeByScore(String key, double start, double end);

	Long zremrangeByScore(String key, String start, String end);

	Long zlexcount(final String key, final String min, final String max);

	Set<String> zrangeByLex(final String key, final String min, final String max);

	Set<String> zrangeByLex(final String key, final String min, final String max, final int offset,
			final int count);

	Set<String> zrevrangeByLex(final String key, final String max, final String min);

	Set<String> zrevrangeByLex(final String key, final String max, final String min,
			final int offset, final int count);

	Long zremrangeByLex(final String key, final String min, final String max);

	Long linsert(String key, Client.LIST_POSITION where, String pivot, String value);

	Long lpushx(String key, String... string);

	Long rpushx(String key, String... string);

	/**
	 * @deprecated unusable command, this will be removed in 3.0.0.
	 */
	@Deprecated
	List<String> blpop(String arg);

	List<String> blpop(int timeout, String key);

	/**
	 * @deprecated unusable command, this will be removed in 3.0.0.
	 */
	@Deprecated
	List<String> brpop(String arg);

	List<String> brpop(int timeout, String key);

	Long del(String key);

	String echo(String string);

	Long move(String key, int dbIndex);

	Long bitcount(final String key);

	Long bitcount(final String key, long start, long end);

	@Deprecated
	/**
	 * This method is deprecated due to bug (scan cursor should be unsigned long)
	 * And will be removed on next major release
	 * @see https://github.com/xetorthio/jedis/issues/531 
	 */
	ScanResult<Map.Entry<String, String>> hscan(final String key, int cursor);

	@Deprecated
	/**
	 * This method is deprecated due to bug (scan cursor should be unsigned long)
	 * And will be removed on next major release
	 * @see https://github.com/xetorthio/jedis/issues/531 
	 */
	ScanResult<String> sscan(final String key, int cursor);

	@Deprecated
	/**
	 * This method is deprecated due to bug (scan cursor should be unsigned long)
	 * And will be removed on next major release
	 * @see https://github.com/xetorthio/jedis/issues/531 
	 */
	ScanResult<Tuple> zscan(final String key, int cursor);

	ScanResult<Map.Entry<String, String>> hscan(final String key, final String cursor);

	ScanResult<String> sscan(final String key, final String cursor);

	ScanResult<Tuple> zscan(final String key, final String cursor);

	Long pfadd(final String key, final String... elements);

	long pfcount(final String key);

}
