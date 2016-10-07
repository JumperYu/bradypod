package com.bradypod.reflect.asm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

import com.bradypod.reflect.jdk.Programmer;

public class ReturnAdapter extends ClassVisitor {

	private String className;

	public ReturnAdapter(ClassVisitor cv, String className) {
		super(Opcodes.ASM5, cv);
		this.className = className;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		MethodVisitor mv;
		mv = cv.visitMethod(access, name, desc, signature, exceptions);
		mv = new MethodReturnAdapter(Opcodes.ASM5, className, access, name,
				desc, mv);
		return mv;
	}

	public static void main(String[] args) throws IOException {
		String classFile = Programmer.class.getProtectionDomain()
				.getCodeSource().getLocation().getFile().substring(1)
				+ "com/bradypod/reflect/jdk/Programmer.class";// path of the
																// class file
		String className = Programmer.class.getName();// name of the class
		File inFile = new File(classFile);
		FileInputStream in = new FileInputStream(inFile);

		// adapting the class.
		ClassReader cr = new ClassReader(in);
		ClassWriter cw = new ClassWriter(ClassReader.EXPAND_FRAMES);
		ReturnAdapter returnAdapter = new ReturnAdapter(cw, className);
		cr.accept(returnAdapter, 0);
		Programmer programmer = new Programmer();
		programmer.code("123");
	}
}

/**
 * Method Visitor that inserts code right before its return instruction(s),
 * using the onMethodExit(int opcode) method of the AdviceAdapter class, from
 * ASM(.ow2.org).
 * 
 * @author vijay
 *
 */
class MethodReturnAdapter extends AdviceAdapter {
	
	public MethodReturnAdapter(int api, String owner, int access, String name,
			String desc, MethodVisitor mv) {
		super(Opcodes.ASM5, mv, access, name, desc);
	}

	public MethodReturnAdapter(MethodVisitor mv, int access, String name,
			String desc) {
		super(Opcodes.ASM5, mv, access, name, desc);
	}

	@Override
	protected void onMethodEnter() {
		
		System.out.println("enter:" + methodDesc);
	}

	@Override
	protected void onMethodExit(int opcode) {
		System.out.println("exit: " + methodDesc);
	}
}