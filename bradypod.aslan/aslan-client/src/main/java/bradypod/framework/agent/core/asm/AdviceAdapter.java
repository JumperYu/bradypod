package bradypod.framework.agent.core.asm;

import java.lang.reflect.Method;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class AdviceAdapter extends MethodVisitor implements Opcodes {

	private String descriptor;
	private String targetMethod;

	private Method adviceMethod;

	public AdviceAdapter(MethodVisitor mv, String descriptor, String targetMethod) {
		super(Opcodes.ASM5, mv);
		this.descriptor = descriptor;
		this.targetMethod = targetMethod;
	}

	@Override
	public void visitCode() {

		Class<?> clazz = findClass(descriptor, Thread.currentThread().getContextClassLoader());
		for (Method method : clazz.getMethods()) {
			if (method.getName().equals(targetMethod)) {
				// advice method
				adviceMethod = method;
				// 0为this指令
				int slot = 1;
				// 打印入参
				for (Class<?> type : method.getParameterTypes()) {
					Type paramType = Type.getType(type);
					// System.out.println(xLOAD);
					mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
					mv.visitVarInsn(ASMUtil.getXLOAD(type), slot);
					mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
							ASMUtil.getDescriptor(type, true), false);
					// 计算opcode在frame中slot的大小, 64位的long&double占2位
					slot += paramType.getSize();
				} // --> end for
			} // --> 打印所有入参请求和返回值
		} // --> end for
	}

	@Override
	public void visitInsn(int opcode) {
		if (opcode >= IRETURN && opcode <= ARETURN) {
			if (opcode == LRETURN || opcode == DRETURN) {
				mv.visitInsn(DUP2);
			} else {
				mv.visitInsn(DUP);
				mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/bradypod/reflect/jdk/Printer", "print",
						Type.getMethodDescriptor(Type.VOID_TYPE, Type.getType(adviceMethod.getReturnType())), false);
			}
		}
		super.visitInsn(opcode);
	}

	private static Class<?> findClass(String descriptor, ClassLoader classLoader) {
		Class<?> clazz = null;
		try {
			clazz = classLoader.loadClass(descriptor.replaceAll("/", "\\."));
		} catch (ClassNotFoundException e) {
			// TODO
		}
		return clazz;
	}

}