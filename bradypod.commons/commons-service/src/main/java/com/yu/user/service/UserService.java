package com.yu.user.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.bradypod.common.service.BaseMybatisServiceImpl;
import com.yu.user.mapper.AccountMapper;
import com.yu.user.po.Account;

/**
 * 
 * 用户模块业务层
 *
 * @author zengxm
 * @date 2015年4月30日
 *
 */
//@Service
//@Transactional
public class UserService extends BaseMybatisServiceImpl<AccountMapper, Account> {

	/**
	 * 验证账号是否存在
	 * 
	 * @param passport
	 *            账号
	 * @param password
	 *            地址
	 * @return
	 */
	public boolean validateAccount(Account account) {
		if (StringUtils.isEmpty(account.getPassport())
				|| StringUtils.isEmpty(account.getPassword()))
			return false;

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("passport", account.getPassport());
		params.put("password", account.getPassword());
		/*
		 * if (getMapper().isExists(params) != 1) return false;
		 */

		return true;
	}

}
