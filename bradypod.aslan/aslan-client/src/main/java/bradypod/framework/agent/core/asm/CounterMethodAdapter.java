package bradypod.framework.agent.core.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class CounterMethodAdapter extends MethodVisitor {

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
//		mv.visitCode();
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "bradypod/framework/agent/core/asm/TimeCostInterceptor", "begin", "()V");
//		mv.visitFieldInsn(Opcodes.GETSTATIC, "", "timer", "J");
//		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
// 		mv.visitInsn(Opcodes.LSUB);
// 		mv.visitFieldInsn(Opcodes.PUTSTATIC, "", "timer", "J");
	}

	@Override
	public void visitInsn(int opcode) {
		if (opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN) {
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, "bradypod/framework/agent/core/asm/TimeCostInterceptor", "end",
					"()V");
		}
		mv.visitInsn(opcode);
//		if ((opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN) || opcode == Opcodes.ATHROW) {
//	         mv.visitFieldInsn(Opcodes.GETSTATIC, "", "timer", "J");
//	         mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
//	         mv.visitInsn(Opcodes.LADD);
//	         mv.visitFieldInsn(Opcodes.PUTSTATIC, "", "timer", "J");
//	    }
//	    mv.visitInsn(opcode);
	}
}
