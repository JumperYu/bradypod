package com.bradypod.util.redis;

import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import com.bradypod.util.redis.invoker.RedisCallback;
import com.bradypod.util.redis.serializer.JacksonRedisSerializer;
import com.bradypod.util.redis.serializer.JdkRedisSerializer;
import com.bradypod.util.redis.serializer.NumberRedisSerializer;
import com.bradypod.util.redis.serializer.StringRedisSerializer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.yu.util.validate.AssertUtil;

/**
 * Redis操作模板
 *
 * @author zengxm
 * @date 2015年9月27日
 *
 */
public class RedisTemplate {

	RedisFactory redisFactory; // 由工场生成redis连接

	// 各种类型的序列化工具
	NumberRedisSerializer numberRedisSerializer = new NumberRedisSerializer();

	StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

	JdkRedisSerializer jdkRedisSerializer = new JdkRedisSerializer();

	JacksonRedisSerializer jacksonRedisSerializer = new JacksonRedisSerializer();

	/* 处理回调 */
	protected <T> T execute(final RedisCallback<T> callback) {
		AssertUtil.assertNotNull(redisFactory, "redis factory not set");
		try (Jedis jedis = redisFactory.getResource()) {
			return callback.execute(jedis);
		} catch (Exception e) {
			logger.error("redis error", e);
		}
		// ignore error
		return null;
	}

	// TODO set方法统一先不处理返回值, 并假设都能成功

	/**
	 * 普通Number类型的序列化
	 * 
	 * @param key
	 * @param value
	 */
	public void set(final String key, final Number value) {
		set(stringRedisSerializer.serialize(key), numberRedisSerializer.serialize(value));
	}

	/**
	 * 字符串的字节转换
	 * 
	 * @param key
	 * @param value
	 */
	public void set(final String key, final String value) {
		set(stringRedisSerializer.serialize(key), stringRedisSerializer.serialize(value));
	}

	/**
	 * jdk序列化对于复杂对象, 比较耗时
	 * 
	 * @param key
	 * @param collection
	 */
	public void set(final String key, final Collection<?> value) {
		set(stringRedisSerializer.serialize(key), jacksonRedisSerializer.serialize(value));
	}

	public void set(final String key, final Map<?, ?> value) {
		set(stringRedisSerializer.serialize(key), jacksonRedisSerializer.serialize(value));
	}

	/**
	 * 普通对象的序列化, jdk较优秀
	 * 
	 * @param key
	 * @param value
	 */
	public void set(final String key, final Object value) {
		// jdkRedisSerializer.serialize(value)
		set(stringRedisSerializer.serialize(key), jacksonRedisSerializer.serialize(value));
	}

	/**
	 * 当存在或者不存的时候设置
	 * 
	 * @param key
	 * @param value
	 * @param nxxx
	 *            - nx:不存在,xx:存在
	 * @param expx
	 *            - ex:秒,px:毫秒
	 * @param time
	 *            - long
	 */
	public String set(final String key, final String value, final String nxxx, final String expx,
			final long time) {
		return execute(new RedisCallback<String>() {

			@Override
			public String execute(Jedis jedis) {
				return jedis.set(key, value, nxxx, expx, time);
			}
		});
	}

	/**
	 * 哈希表操作
	 * 
	 * @param key
	 *            - redis hashmap key
	 * @param field
	 *            - map key
	 * @param value
	 *            - map value
	 */
	public void hset(final String key, final String field, final String value) {
		hset(stringRedisSerializer.serialize(key), stringRedisSerializer.serialize(field),
				stringRedisSerializer.serialize(value));
	}

	public void hset(final String key, final String field, final Number value) {
		hset(stringRedisSerializer.serialize(key), stringRedisSerializer.serialize(field),
				numberRedisSerializer.serialize(value));
	}

	public void hset(final String key, final String field, final Collection<?> value) {
		hset(stringRedisSerializer.serialize(key), stringRedisSerializer.serialize(field),
				jacksonRedisSerializer.serialize(value));
	}

	public void hset(final String key, final String field, final Map<?, ?> value) {
		hset(stringRedisSerializer.serialize(key), stringRedisSerializer.serialize(field),
				jacksonRedisSerializer.serialize(value));
	}

	public void hset(final String key, final String field, final Object value) {
		hset(stringRedisSerializer.serialize(key), stringRedisSerializer.serialize(field),
				jacksonRedisSerializer.serialize(value));
	}

	/**
	 * 字节存储, 节省内存
	 * 
	 * @param key
	 * @param value
	 */
	public void set(final byte[] key, final byte[] value) {
		execute(new RedisCallback<String>() {

			@Override
			public String execute(Jedis jedis) {
				return jedis.set(key, value);
			}
		});
	}

	public void hset(final byte[] key, final byte[] field, final byte[] value) {
		execute(new RedisCallback<Long>() {

			@Override
			public Long execute(Jedis jedis) {
				return jedis.hset(key, field, value);
			}
		});
	}

	// TODO get方法, 需要不断完善

	public String getStringValue(final String key) {
		return stringRedisSerializer.deserialize(get(stringRedisSerializer.serialize(key)));
	}

	// Number类型的序列化
	public Long getLongValue(final String key) {
		return numberRedisSerializer.deserializeLong(get(stringRedisSerializer.serialize(key)));
	}

	public Integer getIntegerValue(final String key) {
		return numberRedisSerializer.deserializeInteger(get(stringRedisSerializer.serialize(key)));
	}

	public Byte getByteValue(final String key) {
		return numberRedisSerializer.deserializeByte(get(stringRedisSerializer.serialize(key)));
	}

	public Float getFloatValue(final String key) {
		return numberRedisSerializer.deserializeFloat(get(stringRedisSerializer.serialize(key)));
	}

	public Double getDoubleValue(final String key) {
		return numberRedisSerializer.deserializeDouble(get(stringRedisSerializer.serialize(key)));
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
		return jacksonRedisSerializer.deserialize(get(stringRedisSerializer.serialize(key)), clazz);
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
		return jacksonRedisSerializer.deserialize(get(stringRedisSerializer.serialize(key)),
				typeReference);
	}

	/**
	 * 不要使用这个方法
	 */
	@SuppressWarnings("unchecked")
	@Deprecated
	public <T> T getObject(final String key) {
		return (T) jdkRedisSerializer.deserialize(get(stringRedisSerializer.serialize(key)));
	}

	/**
	 * 哈希获取
	 */

	public <T> T hgetObject(final String key, final String field, final Class<T> clazz) {
		return jacksonRedisSerializer.deserialize(
				hget(stringRedisSerializer.serialize(key), stringRedisSerializer.serialize(field)),
				clazz);
	}

	public <T> T hgetObject(final String key, final String field,
			final TypeReference<T> typeReference) {
		return jacksonRedisSerializer.deserialize(
				hget(stringRedisSerializer.serialize(key), stringRedisSerializer.serialize(field)),
				typeReference);
	}

	/**
	 * 不要使用这个方法了 <br/>
	 * 
	 * 使用 : <br/>
	 * hget(final String key, final String field, final TypeReference<T>
	 * typeReference) <br/>
	 * hget(final String key, final String field, final Class<T> clazz) <br/>
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public <T> T hgetObject(final String key, final String field) {
		return (T) jdkRedisSerializer.deserialize(hget(stringRedisSerializer.serialize(key),
				stringRedisSerializer.serialize(field)));
	}

	public String hgetString(final String key, final String field) {
		return stringRedisSerializer.deserialize(hget(stringRedisSerializer.serialize(key),
				stringRedisSerializer.serialize(field)));
	}

	public Long hgetLong(final String key, final String field) {
		return numberRedisSerializer.deserializeLong(hget(stringRedisSerializer.serialize(key),
				stringRedisSerializer.serialize(field)));
	}

	public Integer hgetInteger(final String key, final String field) {
		return numberRedisSerializer.deserializeInteger(hget(stringRedisSerializer.serialize(key),
				stringRedisSerializer.serialize(field)));
	}

	public Float hgetFloat(final String key, final String field) {
		return numberRedisSerializer.deserializeFloat(hget(stringRedisSerializer.serialize(key),
				stringRedisSerializer.serialize(field)));
	}

	public Double hgetDouble(final String key, final String field) {
		return numberRedisSerializer.deserializeDouble(hget(stringRedisSerializer.serialize(key),
				stringRedisSerializer.serialize(field)));
	}

	public Byte hgetByte(final String key, final String field) {
		return numberRedisSerializer.deserializeByte(hget(stringRedisSerializer.serialize(key),
				stringRedisSerializer.serialize(field)));
	}

	/**
	 * 按字节key获取原始字节类型的值
	 * 
	 * @param key
	 *            - 字节key
	 * @return bytes - 为转换的字节值
	 */
	public byte[] get(final byte[] key) {
		return execute(new RedisCallback<byte[]>() {

			@Override
			public byte[] execute(Jedis jedis) {
				return jedis.get(key);
			}
		});
	}

	public byte[] hget(final byte[] key, final byte[] field) {
		return execute(new RedisCallback<byte[]>() {

			@Override
			public byte[] execute(Jedis jedis) {
				return jedis.hget(key, field);
			}
		});
	}

	/**
	 * 顺序列表, 从左侧头部插入
	 */
	public Long lpush(final String key, final String value) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long execute(Jedis jedis) {
				return jedis.lpush(key, value);
			}
		});
	}

	/**
	 * 顺序列表, 从右侧侧尾部插入
	 */
	public Long rpush(final String key, final String value) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long execute(Jedis jedis) {
				return jedis.rpush(key, value);
			}
		});
	}

	/**
	 * 顺序列表, 从左侧头部弹出
	 */
	public String lpop(final String key) {
		return execute(new RedisCallback<String>() {
			@Override
			public String execute(Jedis jedis) {
				return jedis.lpop(key);
			}
		});
	}

	/**
	 * 顺序列表, 从右侧尾部弹出
	 */
	public String rpop(final String key) {
		return execute(new RedisCallback<String>() {
			@Override
			public String execute(Jedis jedis) {
				return jedis.rpop(key);
			}
		});
	}

	/**
	 * 设置缓存失效时间
	 */
	public Long expire(final String key, final int seconds) {
		return execute(new RedisCallback<Long>() {
			@Override
			public Long execute(Jedis jedis) {
				return jedis.expire(key, seconds);
			}
		});
	}

	/**
	 * 删除缓存, 先不处理返回值
	 */
	public void delete(final String key) {
		execute(new RedisCallback<Long>() {

			@Override
			public Long execute(Jedis jedis) {
				return jedis.del(key);
			}
		});
	}

	/**
	 * 发布消息
	 */
	public void publish(final String channel, final String msg) {
		execute(new RedisCallback<Long>() {
			@Override
			public Long execute(Jedis jedis) {
				return jedis.publish(channel, msg);
			}
		});
	}

	/**
	 * 订阅消息
	 */
	public void subscribe(final JedisPubSub jedisPubSub, final String... channels) {
		execute(new RedisCallback<Void>() {
			@Override
			public Void execute(Jedis jedis) {
				jedis.subscribe(jedisPubSub, channels);
				return null;
			}
		});
	}

	/* set */
	public void setRedisFactory(RedisFactory redisFactory) {
		this.redisFactory = redisFactory;
	}

	private static final Logger logger = LoggerFactory.getLogger(RedisTemplate.class);
}
