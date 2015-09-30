package com.yu.identity.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bradypod.common.service.BaseMybatisServiceImpl;
import com.yu.identity.mapper.IdentityMapper;
import com.yu.identity.po.Identity;

/**
 * 业务标识服务
 * 
 * @author zengxm
 * @date 2015-08-24
 *
 */
//@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class IdentityService extends
		BaseMybatisServiceImpl<IdentityMapper, Identity> {

	static Logger log = LoggerFactory.getLogger(IdentityService.class);

	/**
	 * 主键生成
	 * 
	 * @param system
	 *            - 系統
	 * @param subSys
	 *            - 子系統
	 * @param module
	 *            - 模块
	 * @param table
	 *            - 表名
	 * @return - NextValue
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public Long generateId(String system, String subSys, String module,
			String tableName) {
		Identity entity = new Identity();
		entity.setSubSys(subSys);
		entity.setSystem(system);
		entity.setModule(module);
		entity.setTableName(tableName);
		entity = getMapper().get(entity);
		Long generateId = entity.getIdentity();

		entity.setIdentity(generateId + 1);
		getMapper().update(entity);

		return generateId;
	}
}
