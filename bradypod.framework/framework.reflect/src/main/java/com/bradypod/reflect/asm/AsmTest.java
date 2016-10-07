package com.bradypod.reflect.asm;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import com.bradypod.reflect.jdk.Programmer;

public class AsmTest {

	@Test
	public void testClassParse() throws IOException {
		ClassPrinter cp = new ClassPrinter();
		ClassReader cr = new ClassReader("java.lang.Runnable");
		cr.accept(cp, 0);
	}

	@Test
	public void testClassGenerate() throws FileNotFoundException, IOException {
		ClassWriter cw = new ClassWriter(0);
		cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC + Opcodes.ACC_INTERFACE,
				"pkg/Comparable", null, "java/lang/Object",
				new String[] { "pkg/Mesurable" });
		cw.visitField(
				Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC,
				"LESS", "I", null, new Integer(-1)).visitEnd();
		cw.visitField(
				Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC,
				"EQUAL", "I", null, new Integer(0)).visitEnd();
		cw.visitField(
				Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC,
				"GREATER", "I", null, new Integer(1)).visitEnd();
		cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT, "compareTo",
				"(Ljava/lang/Object;)I", null, null).visitEnd();
		cw.visitEnd();
		byte[] b = cw.toByteArray();
		FileOutputStream out = new FileOutputStream("E://pkg/Comparable.class");
		out.write(b);
		out.close();
	}

	@Test
	public void testReturnAdapter() throws IOException, URISyntaxException,
			InstantiationException, IllegalAccessException,
			NoSuchMethodException, SecurityException, IllegalArgumentException,
			InvocationTargetException, ClassNotFoundException {
		// 读取到原有的字节码值
		byte[] bytes = Files.readAllBytes(Paths.get(Programmer.class
				.getProtectionDomain().getCodeSource().getLocation().getFile()
				.substring(1)
				+ "com/bradypod/reflect/jdk/Programmer.class"));
		ClassReader cr = new ClassReader(bytes);
		ClassWriter cw = new ClassWriter(cr, 0);
		ClassVisitor cv = new ClassAdapter(Opcodes.ASM5, cw);
		cr.accept(cv, ClassReader.SKIP_DEBUG);

		byte[] data = cw.toByteArray();

		// 写到一个文件中去
		Files.write(Paths.get("E://Programmer.class"), data,
				StandardOpenOption.CREATE, StandardOpenOption.WRITE);

		// 直接类加载
		MyClassLoader myClassLoader = new MyClassLoader();
		Class<?> helloClass = myClassLoader.defineClass(
				"com.bradypod.reflect.jdk.Programmer", data);
		Object obj = helloClass.newInstance();
		Method method = helloClass.getMethod("code", String.class);
		method.invoke(obj, "123");

		// 加载应有的类型
		Class<?> stuClass = Thread.currentThread().getContextClassLoader()
				.loadClass("com.bradypod.reflect.jdk.Programmer");
		Object obj2 = stuClass.newInstance();
		Method method1 = stuClass.getMethod("code", String.class);
		method1.invoke(obj2, "132");
	}
}
