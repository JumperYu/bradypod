package com.bradypod.common.util;

/**
 * 转换unicode
 *
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015年10月17日 下午1:49:24
 */
public class Encoder {

	public static String toUnicode(String input) {

		StringBuilder builder = new StringBuilder();
		char[] chars = input.toCharArray();

		for (char ch : chars) {

			if (ch < 256) {
				builder.append(ch);
			} else {
				builder.append("\\u" + Integer.toHexString(ch & 0xffff));
			}

		}

		return builder.toString();

	}

}