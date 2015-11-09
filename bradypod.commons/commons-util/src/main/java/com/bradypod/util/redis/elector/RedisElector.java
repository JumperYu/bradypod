package com.bradypod.util.redis.elector;

import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bradypod.util.redis.RedisTemplate;
import com.bradypod.util.sys.JvmUtil;
import com.bradypod.util.thread.ScheduledThreadPool;
import com.bradypod.util.thread.ThreadWorker;
import com.bradypod.util.thread.Threads;
import com.yu.util.validate.AssertUtil;

/**
 * 通过redis+schedule实现master选举
 * 
 * Master选举实现, 基于setNx()与expire()两大API. <br>
 * 与每次使用setNx争夺的分布式锁不同，Master用setNX争夺Master <br>
 * Key成功后，会不断的更新key的expireTime，保持自己的master地位，直到自己倒掉了不能再更新为止。 <br>
 * 其他Slave会定时检查Master, Key是否已过期，如果已过期则重新发起争夺。 <br>
 * 其他服务可以随时调用isMaster()，查询自己是否master, 与MasterElector的内部定时操作是解耦的。 <br>
 * 
 * 在最差情况下，可能有两倍的intervalSecs内集群内没有Master。 <br>
 * 
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015年11月7日 下午5:42:22
 */
public class RedisElector {

	private int electors; // 投票人数
	private int period; // 投票间隔
	private int expireTime; // 获选任期(秒)
	private String masterKey; // 选举职位关键词

	private boolean isHolding = true; // 是否持续占据位置

	static final int DEFAULT_ELECTORS = 2; // 默认参数
	static final int DEFAULT_PERIOD = 2;
	static final int DEFAULT_EXPIRE_TIME = 10;
	static final String DEFAULT_MASTER_KEY = "master";

	private ScheduledThreadPool scheduledThreadPool; // 选举流程
	private RedisTemplate redisTemplate;
	private ElectorListener electorListener;

	private String hostId; // 动态获取当前选举人
	private AtomicBoolean master; // 是否当选

	/**
	 * 默认构造
	 */
	public RedisElector() {
		// set it default
		this(DEFAULT_ELECTORS, DEFAULT_PERIOD, DEFAULT_EXPIRE_TIME, DEFAULT_MASTER_KEY);
	}

	/**
	 * 设置投票人数, 投票间隔
	 * 
	 * @param electors
	 *            - 选举人数
	 * @param period
	 *            - 间隔
	 * @param expireTime
	 *            - 失效时间（秒）
	 * @param masterKey
	 *            - 选举关键词
	 */
	public RedisElector(int electors, int period, int expireTime, String masterKey) {
		// 初始化参数
		this.electors = electors;
		this.period = period;
		this.expireTime = expireTime;
		this.master = new AtomicBoolean(false);
		this.masterKey = masterKey;

		// 创建定时任务
		scheduledThreadPool = new ScheduledThreadPool(this.electors);
	}

	/**
	 * 启动选举
	 */
	public void start() {

		AssertUtil.assertNotNull(redisTemplate, "redis template is required.");

		scheduledThreadPool.executeAtFixedRate(new ThreadWorker() {

			@Override
			public void execute() {
				try {
					// 获取当前的
					String masterHost = redisTemplate.getStringValue(masterKey);
					logger.info("master key:{} is belong to {}", masterKey, masterHost);
					if (masterHost == null) {
						hostId = JvmUtil.getJvmInfo(); // 获取当前host
						// set only not exists key, and expire in times
						String ret = redisTemplate.set(masterKey, hostId, "NX", "EX", expireTime);
						// if OK set master true
						if (ret != null && ret.equalsIgnoreCase("OK")) {
							logger.info("master key:{} is set to host:{}", masterKey, hostId);
							master.set(true);
							masterHost = hostId;
						}// --> end if set master success
					}// --> end if master is null

					if (hostId != null && hostId.equals(masterHost)) {
						// say i am master
						logger.info("i am master now.");
						// expire at seconds
						master.set(true);
						// if is holding reset expire time
						if (isHolding) {
							redisTemplate.expire(masterKey, expireTime);
						}// --> end if holding else ignore

						// on master set success
						electorListener.onMaster(masterHost);
					} else {
						master.set(false);
						// say i am not master
						logger.info("i am not master yet.");
					}// --> end if master or not
				} catch (Throwable throwable) {
					logger.error("schedule error", throwable);
				}// --> end try-catch
			}// --> end execute function
		}, Threads.buildJobFactory("elector:" + masterKey + "-thread-%d"), 0, period);
	}

	/**
	 * 停止选举
	 */
	public void stop() {
		if (master.get()) {
			redisTemplate.delete(DEFAULT_MASTER_KEY);
		}// --> if master delete master_key

		// future.cancel(false);

		scheduledThreadPool.shutdown(); // shutdown pool
	}

	/**
	 * 是否为主人
	 */
	public boolean isMaster() {
		return master.get();
	}

	/* set redis template is important */
	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void setElectorListener(ElectorListener electorListener) {
		this.electorListener = electorListener;
	}

	// 适用于从选举进程, 只做监督而不永久霸占
	public void setHolding(boolean isHolding) {
		this.isHolding = isHolding;
	}

	static final Logger logger = LoggerFactory.getLogger(RedisElector.class);
}
