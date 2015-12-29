package com.bradypod.common.util;

import com.yu.user.po.Account;

/**
 * 当前线程的副本
 *
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2015年12月29日 下午1:57:46
 */
public class LoginUserContext {

	private static ThreadLocal<Account> localContext = new ThreadLocal<Account>();

	/**
	 * 获取当前登陆用户
	 */
	public static Account getLoginAccount() {
		Account account = localContext.get();
		return account;
	}

	/**
	 * 设置登陆用户
	 */
	public static void setLoginAccount(Account account) {
		localContext.set(account);
	}
}
