package com.bradypod.util.reflect;

import ognl.Ognl;
import ognl.OgnlException;

/**
 * Ognl工具类
 *
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015年9月22日 下午4:23:37
 */
public class OgnlUtil {

	/**
	 * 通过属性名, 调用get方法
	 * 
	 * @param tree
	 *            - 属性名
	 * @param root
	 *            - 对象
	 * @return - Object类型
	 */
	public static Object getValue(String exp, Object root) {
		try {
			return Ognl.getValue(exp, root);
		} catch (OgnlException e) {
			throw new RuntimeException("OgnlUtil.getValue error", e);
		}
	}

}
