package com.bradypod.util.redis.elector;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bradypod.util.redis.RedisFactory;
import com.bradypod.util.redis.RedisTemplate;
import com.bradypod.util.thread.ScheduledThreadPool;
import com.bradypod.util.thread.ThreadWorker;

/**
 * 通过redis+schedule实现master选举
 * 
 *
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015年11月7日 下午5:42:22
 */
public class RedisElector {

	static final int MAX_ELECTORS = 2; // 投票人数
	static final int PERIOD = 2; // 投票间隔

	static final String HOST = "112.124.126.31";
	static final int PORT = 6379;

	static final String MASTER_KEY = "Master";
	static final int EXPIRE_TIME = 60;

	private ScheduledThreadPool scheduledThreadPool;
	private RedisTemplate redisTemplate;

	private String hostId;
	private AtomicBoolean isMaster;

	public RedisElector() {
		scheduledThreadPool = new ScheduledThreadPool(MAX_ELECTORS); // 创建定时任务

		redisTemplate = new RedisTemplate(); // 创建连接
		redisTemplate.setRedisFactory(new RedisFactory(HOST, PORT));

		hostId = generateHostId(); // 获取当前host
		isMaster = new AtomicBoolean(false);
	}

	public void start() {
		scheduledThreadPool.executeAtFixedRate(new ThreadWorker() {

			@Override
			public void execute() {
				try {
					// 获取当前的
					String masterHost = redisTemplate.getStringValue(MASTER_KEY);
					logger.debug("redis key {} is {}", MASTER_KEY, masterHost);
					if (masterHost == null) {
						String ret = redisTemplate.set(MASTER_KEY, generateHostId(), "NX", "EX",
								EXPIRE_TIME);
						if (ret != null && ret.equalsIgnoreCase("OK")) {
							logger.debug("redis key {} is set to master {}", MASTER_KEY, masterHost);
							isMaster.set(true);
						}
					}

					if (hostId.equals(masterHost)) {
						// expire at seconds
						isMaster.set(true);
					} else {
						isMaster.set(false);
					}
				} catch (Throwable throwable) {
					logger.error("schedule error", throwable);
				}
			}
		}, 0, PERIOD);
	}

	/**
	 * 生成host id的方法，可在子类重载.
	 */
	protected String generateHostId() {
		String host = "localhost";
		try {
			host = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			logger.warn("can not get hostName, use localhost as default.", e);
		}
		host = host + "-" + new SecureRandom().nextInt(10000);

		return host;
	}

	public void stop() {

	}

	public boolean isMaster() {
		return isMaster;
	}

	static final Logger logger = LoggerFactory.getLogger(RedisElector.class);
}
