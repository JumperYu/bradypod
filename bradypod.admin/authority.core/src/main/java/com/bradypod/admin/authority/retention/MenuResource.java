package com.bradypod.admin.authority.retention;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 菜单资源
 *
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2015年12月29日 上午10:30:29
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface MenuResource {

	/**
	 * 资源名称
	 * 
	 * @return
	 */
	String value();
}
