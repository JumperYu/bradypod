package bradypod.framework.agent.core.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class CounterMethodAdapter extends MethodVisitor implements Opcodes{

	public CounterMethodAdapter(MethodVisitor mv) {
		super(Opcodes.ASM5, mv);
	}

	@Override
	public void visitParameter(String paramString, int paramInt) {
		System.out.println(paramString + " " + paramInt);
		
		super.visitParameter(paramString, paramInt);
	}
	
	@Override
	public void visitCode() {
		mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
		mv.visitVarInsn(ALOAD, 1);
		mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
		
		mv.visitMethodInsn(INVOKESTATIC, "bradypod/framework/agent/core/asm/TimeCostInterceptor", "begin", "()V");
	}

	@Override
	public void visitInsn(int opcode) {
		
		if (opcode >= Opcodes.IRETURN || opcode <= Opcodes.RETURN) {
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, "bradypod/framework/agent/core/asm/TimeCostInterceptor", "end",
					"()V");
		}
		
		mv.visitInsn(opcode);
	}
}
