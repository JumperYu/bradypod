package com.bradypod.reflect.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ClassAdapter extends ClassVisitor implements Opcodes {

	public ClassAdapter(int api, ClassVisitor cv) {
		super(api, cv);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		MethodVisitor mv = super.visitMethod(access, name, desc, signature,
				exceptions);
		// 对test开头的方法进行特殊处理
		if (name.startsWith("code")) {
			mv = new MethodAdapter(this.api, mv);
		}
		return mv;
	}
}
