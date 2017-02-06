package bradypod.framework.agent.core.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class CounterClassAdapter extends ClassVisitor {

	private String qualifiedName;
	private String methodName;
	
	@Deprecated
	public CounterClassAdapter(ClassVisitor cv, String methodName) {
		super(Opcodes.ASM5);
		super.cv = cv;
		this.methodName = methodName;
	}
	
	public CounterClassAdapter(ClassVisitor cv, String qualifiedName ,String methodName) {
		super(Opcodes.ASM5);
		super.cv = cv;
		this.qualifiedName = qualifiedName;
		this.methodName = methodName;
	}
	
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
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
		if (methodVisitor != null && methodName.equals(name)) {
			methodVisitor = new CounterMethodAdapter(methodVisitor, qualifiedName, methodName);
			return methodVisitor;
		}
		return methodVisitor;
	}

}