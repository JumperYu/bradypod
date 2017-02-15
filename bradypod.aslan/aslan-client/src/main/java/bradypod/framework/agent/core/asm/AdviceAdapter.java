package bradypod.framework.agent.core.asm;

import java.lang.reflect.Method;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class AdviceAdapter extends MethodVisitor implements Opcodes {

	private String descriptor;
	private String targetMethod;

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

				// xSTORE ret = super.method(xLOAD);
				mv.visitVarInsn(ALOAD, 0); // super
				slot = 1;
				for (Class<?> type : method.getParameterTypes()) {
					mv.visitVarInsn(ASMUtil.getXLOAD(type), slot);
					slot += Type.getType(type).getSize();
				}
				mv.visitMethodInsn(INVOKESPECIAL, descriptor, targetMethod, Type.getMethodDescriptor(method), false);
				mv.visitVarInsn(ASMUtil.getXSTORE(method.getReturnType()), slot);

				// System.out.println(ret);
				mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
				mv.visitVarInsn(ASMUtil.getXLOAD(method.getReturnType()), slot);
				mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
						ASMUtil.getDescriptor(method.getReturnType(), true), false);

				// return ret;
				mv.visitVarInsn(ASMUtil.getXLOAD(method.getReturnType()), slot);
				mv.visitInsn(ASMUtil.getXRETURN(method.getReturnType()));

			} // --> 打印所有入参请求和返回值
		} // --> end for
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