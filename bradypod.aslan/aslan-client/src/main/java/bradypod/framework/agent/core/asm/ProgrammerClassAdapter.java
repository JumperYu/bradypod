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
	public void visit(int version, int access, String name, String signature, String superName,
			String[] interfaces) {
		super.visit(version, access, name, signature, superName, interfaces);
	}
	
	@Override
	public void visitSource(String source, String debug) {
		super.visitSource(source, debug);
	}
	
	// visit outerClass annotation visit attribute innerClass field method
	
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