package com.bradypod.util.redis.mq;

import redis.clients.jedis.Client;
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
}
