package com.yu.test.redis;

import java.util.concurrent.TimeUnit;

import com.bradypod.util.redis.RedisFactory;
import com.bradypod.util.redis.RedisTemplate;
import com.bradypod.util.redis.queue.JobConsumer;
import com.bradypod.util.redis.queue.JobProducer;

/**
 * 测试选举
 *
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015年11月9日 上午11:13:44
 */
public class TestQueue {

	static final String HOST = "112.124.126.31";
	static final int PORT = 6379;

	public static void main(String[] args) throws InterruptedException {
		String jobKey = "job:test";
		JobProducer jobProducer = new JobProducer(jobKey);
		RedisTemplate redisTemplate = new RedisTemplate(); // 创建连接
		redisTemplate.setRedisFactory(new RedisFactory(HOST, PORT));
		jobProducer.setRedisTemplate(redisTemplate);
		jobProducer.queue("test-1");

		JobConsumer jobConsumer = new JobConsumer(jobKey);
		jobConsumer.setRedisTemplate(redisTemplate);
		jobConsumer.startConsumer();

		TimeUnit.SECONDS.sleep(10);
		jobConsumer.stopConsumer();
	}

}
