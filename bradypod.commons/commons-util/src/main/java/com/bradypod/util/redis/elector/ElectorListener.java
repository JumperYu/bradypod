package com.bradypod.util.redis.elector;

/**
 * 选举回调接口
 *
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015年11月9日 下午5:45:32
 */
public interface ElectorListener {

	public void onMaster(String masterKey);

}
 	 