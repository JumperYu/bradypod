package bradypod.framework.agent.core.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class CounterClassAdapter extends ClassVisitor {

	private String qualifiedName;
	private String methodName;
	
	@Deprecated
	public CounterClassAdapter(ClassVisitor cv, String methodName) {
		super(Opcodes.ASM5, cv);
		this.methodName = methodName;
	}
	
	public CounterClassAdapter(ClassVisitor cv, String qualifiedName ,String methodName) {
		super(Opcodes.ASM5, cv);
		this.qualifiedName = qualifiedName;
		this.methodName = methodName;
	}

	// visit outerClass annotation visit attribute innerClass field method

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
		if (methodVisitor != null && methodName.equals(name)) {
			methodVisitor = new CounterMethodAdapter(methodVisitor, qualifiedName, methodName);
			return methodVisitor;
		}
		return methodVisitor;
	}

}