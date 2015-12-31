package com.bradypod.util.array;

import com.yu.util.validate.AssertUtil;

/**
 * 数组工具
 *
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2015年12月31日 下午1:46:12
 */
public class ArrayUtil {

	/**
	 * 数组转换为字符串
	 * 
	 * @param array
	 *            数组
	 * @param concat
	 *            连接符
	 * @return	String
	 */
	public static String join(String[] array, String concat) {
		// 非空
		AssertUtil.notNull(array);
		StringBuffer sb = new StringBuffer();
		for (int i = 0, len = array.length; i < len; i++) {
			sb.append(array[i]);
			if (i != len - 1) {
				sb.append(concat);
			}
		}
		return sb.toString();
	}

}
