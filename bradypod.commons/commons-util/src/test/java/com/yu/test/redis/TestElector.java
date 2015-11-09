package com.yu.test.redis;

import com.bradypod.util.redis.RedisFactory;
import com.bradypod.util.redis.RedisTemplate;
import com.bradypod.util.redis.elector.ElectorListener;
import com.bradypod.util.redis.elector.RedisElector;

/**
 * 测试选举
 *
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015年11月9日 上午11:13:44
 */
public class TestElector {

	static final String HOST = "112.124.126.31";
	static final int PORT = 6379;

	public static void main(String[] args) {
		RedisElector redisElector = new RedisElector(1, 2, 10, "king");
		RedisTemplate redisTemplate = new RedisTemplate(); // 创建连接
		redisTemplate.setRedisFactory(new RedisFactory(HOST, PORT));
		redisElector.setRedisTemplate(redisTemplate);
		redisElector.setElectorListener(new ElectorListener() {
			
			@Override
			public void onMaster(String masterKey) {
				
			}
		});
		redisElector.start();
	}

}
