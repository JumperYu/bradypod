package com.bradypod.admin.authority.retention;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 菜单定义
 *
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2015年12月29日 上午10:29:53
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface Menu {

	/**
	 * 菜单名称
	 */
	String name();

	/**
	 * 父菜单名称
	 */
	String parent() default "";

	/**
	 * 排列顺序
	 */
	int sequence() default 0;
}
