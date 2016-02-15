package com.bradypod.reflect.jdk;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

/**
 * jdk实现动态字节码
 * 
 * @author	zengxm<http://github.com/JumperYu>
 *
 * @date	2016年2月14日
 */
public class JdkProxyTest {

	@Test
	public void testClass() throws IOException, ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		Class<?> clazz = JdkProxyTest.class.getClassLoader().loadClass(
				"com.bradypod.reflect.jdk.Programmer");
		System.out.println(clazz.getName());
		Object object = clazz.newInstance();
		clazz.getMethod("code", String.class).invoke(object, " hello world");
	}

}
