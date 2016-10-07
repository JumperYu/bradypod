package com.bradypod.reflect.asm;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

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
}
