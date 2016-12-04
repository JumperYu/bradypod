package bradypod.framework.agent.core.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ProgrammerClassAdapter extends ClassVisitor {

	public ProgrammerClassAdapter(ClassVisitor cv) {
		super(Opcodes.ASM4);
		super.cv = cv;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
		if (methodVisitor != null && "doCoding".equals(name)) {
			methodVisitor = new DoCodingMethodAdapter(methodVisitor);
			return methodVisitor;
		}
		return methodVisitor;
	}

}