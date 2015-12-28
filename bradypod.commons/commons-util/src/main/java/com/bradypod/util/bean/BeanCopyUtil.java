package com.bradypod.util.bean;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 
 * <p>
 * Project:ttwg.commons
 * <p>
 * <p>
 * Module:utils
 * <p>
 * <p>
 * Description: 对象之间的拷贝:
 * 
 * @see ttwg.commons.utils.BeanCopyUtil.java 数组对象之间的拷贝:
 * @see ttwg.commons.collections.CollectionUtil.java <p>
 *
 * @author 善财童子<zengxm@ttwg168.net>
 * @date 2015年10月30日 上午9:28:30
 */
public class BeanCopyUtil {

	/**
	 * 拷贝对象, 当源数据为空时不抛出异常
	 * 
	 * @param source
	 *            - 源数据
	 * @param targetClass
	 *            - 目标类型
	 * @return - 目标对象
	 */
	public static <E, T> T copyProperties(E source, Class<T> targetClass) {
		// tips: 如果源数据为空, 直接返回空
		if (source == null) {
			return null;
		}// --> end if
		T target = null;
		try {
			// 确保具有空的构造函数
			target = targetClass.newInstance();
			// 使用commons-beanutils
			BeanUtils.copyProperties(target, source);
		} catch (Exception ex) {
			throw new RuntimeException("bean copy util copy error, to check your entity at first",
					ex);
		}// --> end try-catch
		return target;
	}

}
