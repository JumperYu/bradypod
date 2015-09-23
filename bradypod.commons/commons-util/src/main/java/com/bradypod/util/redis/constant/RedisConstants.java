package com.bradypod.util.redis.constant;

/**
 * Redis的部分常量
 *
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015年9月21日 上午11:20:16
 */
public class RedisConstants {
	/**
	 * 返回成功状态码
	 */
	public static final String OK_CODE = "OK";

	/**
	 * 全部成功状态码
	 */
	public static final String OK_MULTI_CODE = "+OK";

	/**
	 * 缓存的超时时间，单位为秒。 2 * 60 * 60 秒 = 2 小时
	 */
	public static final int CACHE_TIMEOUT = 2 * 60 * 60;

}
