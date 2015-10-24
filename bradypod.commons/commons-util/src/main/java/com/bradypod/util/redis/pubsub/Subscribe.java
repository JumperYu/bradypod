package com.bradypod.util.redis.pubsub;

import com.yu.util.redis.RedisUtil;

import redis.clients.jedis.Client;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * redis订阅
 *
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015年10月24日 下午4:26:19
 */
public class Subscribe extends JedisPubSub {

	@Override
	public void onMessage(String channel, String message) {

		System.out.println(String.format("channel:%s, message:%s", channel, message));

	}

	@Override
	public void proceed(Client client, String... channels) {
		try {
			super.proceed(client, channels);
		} catch (Exception e) {	
			System.out.println("----------------");
		}
	}

	public static void main(String[] args) {
		Jedis jedis = RedisUtil.createJedis("192.168.1.201", 7004);
		jedis.subscribe(new Subscribe(), "topic-1", "topic-2");
	}
}
