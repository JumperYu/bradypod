package com.bradypod.reflect.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

public class JavassistTest {

	public static void main(String[] args) throws Exception {
		ClassPool pool = ClassPool.getDefault();
		// 创建Programmer类
		CtClass cc = pool.makeClass("Programmer");
		// 定义code方法
		CtMethod method = CtNewMethod.make("public void code(){}", cc);
		// 插入方法代码
		method.insertBefore("String code = \"哈哈\";\n System.out.println(\"I'm a Programmer,Just Coding.....\" + code);");
		cc.addMethod(method);
		// 保存生成的字节码
		cc.writeFile("E://");
	}

}
