package bradypod.framework.agent.core.asm;

import java.lang.reflect.Method;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class CounterMethodAdapter extends MethodVisitor implements Opcodes{

	private String qualifiedName;
	private String methodName;
	
	public CounterMethodAdapter(MethodVisitor mv, String qualifiedName, String methodName) {
		super(Opcodes.ASM5, mv);
		this.qualifiedName = qualifiedName;
		this.methodName = methodName;
	}

	@Override
	public void visitCode() {

		Class<?> clazz = findClass(qualifiedName, Thread.currentThread().getContextClassLoader());
		for (Method method : clazz.getMethods()) {
			if (method.getName().equals(methodName)) {
				int index = 1;
				for(Class<?> type : method.getParameterTypes()) {
					Type asmType = Type.getType(type);
					mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
					mv.visitVarInsn(ASMUtil.getOpCodes(type), index);
					mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", ASMUtil.getDescriptor(type, method, true), false);
					index += asmType.getSize();
				}
			}
		}
		
//		mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//		mv.visitVarInsn(ALOAD, 1);
//		mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
//		
//		mv.visitMethodInsn(INVOKESTATIC, "bradypod/framework/agent/core/asm/TimeCostInterceptor", "begin", "()V", false);
	}

	@Override
	public void visitInsn(int opcode) {
		
//		if (opcode >= Opcodes.IRETURN || opcode <= Opcodes.RETURN) {
//			mv.visitMethodInsn(Opcodes.INVOKESTATIC, "bradypod/framework/agent/core/asm/TimeCostInterceptor", "end",
//					"()V", false);
//		}
		
		mv.visitInsn(opcode);
	}
	
	private static Class<?> findClass(String qualifiedName, ClassLoader classLoader) {
		Class<?> clazz = null;
		try {
			clazz = classLoader.loadClass(qualifiedName);
		} catch (ClassNotFoundException e) {
			// TODO
		}
		return clazz;
	}
	
}