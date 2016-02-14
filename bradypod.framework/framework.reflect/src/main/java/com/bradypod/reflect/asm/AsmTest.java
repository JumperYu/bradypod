package com.bradypod.reflect.asm;

import java.io.IOException;

import org.junit.Test;
import org.objectweb.asm.ClassReader;

/**
 * 字节码增强之ASM
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年1月27日
 */
public class AsmTest {

	@Test
	public void test01() throws IOException {
		ClassPrinter cp = new ClassPrinter();
		ClassReader cr = new ClassReader("java.lang.Runnable");
		cr.accept(cp, 0);
	}

	public static void log(String msg) {
		System.out.println(msg);
	}
}
