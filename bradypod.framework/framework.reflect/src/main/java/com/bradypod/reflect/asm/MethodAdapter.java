package com.bradypod.reflect.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MethodAdapter extends MethodVisitor implements Opcodes {

	public MethodAdapter(int api, MethodVisitor mv) {
		super(api, mv);
	}

	@Override
	public void visitCode() {
		super.visitCode();
		this.visitMethodInsn(INVOKESTATIC,
				"com/bradypod/reflect/asm/AopInteceptor", "before", "()V",
				false);
	}

	@Override
	public void visitInsn(int opcode) {
		if (opcode >= IRETURN && opcode <= RETURN)// 在返回之前安插after 代码。
			this.visitMethodInsn(INVOKESTATIC,
					"com/bradypod/reflect/asm/AopInteceptor", "after", "()V",
					false);
		super.visitInsn(opcode);
	}
}
