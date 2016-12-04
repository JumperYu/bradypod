package bradypod.framework.agent.core.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class DoCodingMethodAdapter extends MethodVisitor {

	public DoCodingMethodAdapter(MethodVisitor mv) {
		super(Opcodes.ASM5, mv);
	}

	@Override
	public void visitCode() {
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "bradypod/framework/agent/core/asm/SecurityChecker", "checkSecurity", "()V");
	}

}
