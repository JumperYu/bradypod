package com.bradypod.util.redis;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPubSub;

import com.bradypod.util.redis.serializer.JacksonRedisSerializer;
import com.bradypod.util.redis.serializer.NumberRedisSerializer;
import com.bradypod.util.redis.serializer.StringRedisSerializer;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * Redis集群客户端
 *
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015年10月28日 下午4:23:22
 */
public class RedisClusterTemplate {

	JedisCluster jedisCluster; // 集群连接

	HostAndPort[] hostAndPorts; // 连接信息

	// 各种类型的序列化工具
	NumberRedisSerializer numberRedisSerializer = new NumberRedisSerializer();

	StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

	JacksonRedisSerializer jacksonRedisSerializer = new JacksonRedisSerializer();

	public RedisClusterTemplate(HostAndPort[] hostAndPorts) {
		this.hostAndPorts = hostAndPorts;
		jedisCluster = createCluster(hostAndPorts);
	}

	/**
	 * 创建集群连接
	 * 
	 * @param hosts
	 * @param ports
	 * @param passwords
	 * @return
	 */
	private JedisCluster createCluster(HostAndPort[] hostAndPorts) {
		Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
		for (HostAndPort hostAndPort : hostAndPorts) {
			jedisClusterNodes.add(hostAndPort);
		}
		JedisCluster jc = new JedisCluster(jedisClusterNodes);
		return jc;
	}

	/**
	 * 字符串的字节转换
	 * 
	 * @param key
	 * @param value
	 */
	public void set(final String key, final String value) {
		jedisCluster.set(key, value);
	}

	public void set(final String key, final Number value) {
		set(key, value.toString());
	}

	public void set(final String key, final Collection<?> value) {
		set(key, jacksonRedisSerializer.serialize(value));
	}

	public void set(final String key, final Map<?, ?> value) {
		set(key, jacksonRedisSerializer.serialize(value));
	}

	public void set(final String key, final Object value) {
		set(key, jacksonRedisSerializer.serialize(value));
	}

	// 兼容
	public void set(final String key, final byte[] value) {
		set(key, new String(value));
	}

	/**
	 * 哈希表操作
	 * 
	 */
	public void hset(final String key, final String field, final String value) {
		jedisCluster.hset(key, field, value);
	}

	public void hset(final String key, final String field, final Number value) {
		hset(key, field, value.toString());
	}

	public void hset(final String key, final String field, final Collection<?> value) {
		hset(key, field, jacksonRedisSerializer.serialize(value));
	}

	public void hset(final String key, final String field, final Map<?, ?> value) {
		hset(key, field, jacksonRedisSerializer.serialize(value));
	}

	public void hset(final String key, final String field, final Object value) {
		hset(key, field, jacksonRedisSerializer.serialize(value));
	}

	public void hset(final String key, final String field, final byte[] value) {
		hset(key, field, new String(value));
	}

	// TODO get方法, 需要不断完善

	public String getStringValue(final String key) {
		return jedisCluster.get(key);
	}

	// Number类型的序列化
	public Long getLongValue(final String key) {
		return numberRedisSerializer.deserializeLong(get(key));
	}

	public Integer getIntegerValue(final String key) {
		return numberRedisSerializer.deserializeInteger(get(key));
	}

	public Byte getByteValue(final String key) {
		return numberRedisSerializer.deserializeByte(get(key));
	}

	public Float getFloatValue(final String key) {
		return numberRedisSerializer.deserializeFloat(get(key));
	}

	public Double getDoubleValue(final String key) {
		return numberRedisSerializer.deserializeDouble(get(key));
	}

	/**
	 * 可以指定类型转换, 默认使用的Jackson序列化
	 * 
	 * @param key
	 *            - redis key
	 * @param clazz
	 *            - 需要转化的类型
	 * @return
	 */
	public <T> T getObject(final String key, final Class<T> clazz) {
		return jacksonRedisSerializer.deserialize(get(key), clazz);
	}

	/**
	 * 指定类型获取, 支持泛型
	 * 
	 * @param key
	 *            - redis key
	 * @param typeReference
	 *            - 透传泛型
	 * @return
	 */
	public <T> T getObject(final String key, final TypeReference<T> typeReference) {
		return jacksonRedisSerializer.deserialize(get(key), typeReference);
	}

	/**
	 * 哈希获取
	 */

	public <T> T hgetObject(final String key, final String field, final Class<T> clazz) {
		return jacksonRedisSerializer.deserialize(hget(key, field), clazz);
	}

	public <T> T hgetObject(final String key, final String field,
			final TypeReference<T> typeReference) {
		return jacksonRedisSerializer.deserialize(hget(key, field), typeReference);
	}

	public String hgetString(final String key, final String field) {
		return stringRedisSerializer.deserialize(hget(key, field));
	}

	public Long hgetLong(final String key, final String field) {
		return numberRedisSerializer.deserializeLong(hget(key, field));
	}

	public Integer hgetInteger(final String key, final String field) {
		return numberRedisSerializer.deserializeInteger(hget(key, field));
	}

	public Float hgetFloat(final String key, final String field) {
		return numberRedisSerializer.deserializeFloat(hget(key, field));
	}

	public Double hgetDouble(final String key, final String field) {
		return numberRedisSerializer.deserializeDouble(hget(key, field));
	}

	public Byte hgetByte(final String key, final String field) {
		return numberRedisSerializer.deserializeByte(hget(key, field));
	}

	public byte[] get(String key) {
		return jedisCluster.get(key).getBytes();
	}

	public byte[] hget(String key, String field) {
		return jedisCluster.hget(key, field).getBytes();
	}

	/**
	 * 删除缓存, 先不处理返回值
	 */
	public void delete(final String key) {
		jedisCluster.del(key);
	}

	/**
	 * 不知处PUBSUB
	 */
	@Deprecated
	public void publish(final String channel, final String msg) {
	}

	/**
	 * 不知处PUBSUB
	 */
	@Deprecated
	public void subscribe(final JedisPubSub jedisPubSub, final String... channels) {
	}

	/* set */
	public void setHostAndPorts(HostAndPort[] hostAndPorts) {
		this.hostAndPorts = hostAndPorts;
	}
}
