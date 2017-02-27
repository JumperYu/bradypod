package bradypod.framework.agent.core.asm;

import java.lang.reflect.Method;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class AdviceAdapter extends MethodVisitor implements Opcodes {

	private String descriptor; // pattern like "xx/xx/xx"
	private String targetMethod;

	private Method adviceMethod;
	
	// 0为this指令
	private int slot = 1;

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
				// 打印入参
				for (Class<?> type : method.getParameterTypes()) {
					Type paramType = Type.getType(type);

					// System.out.println(xLOAD);
//					mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//					mv.visitVarInsn(ASMUtil.getXLOAD(type), slot);
//					mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
//							ASMUtil.getDescriptor(type, true), false);

					// Printer.println(xLOAD);
					mv.visitLdcInsn(descriptor);
					mv.visitMethodInsn(INVOKESTATIC, "bradypod/framework/agent/core/asm/AdviceWeaver", "getPrinter",
							"(Ljava/lang/String;)Lbradypod/framework/agent/core/asm/Printer;", false);
					mv.visitVarInsn(ASMUtil.getXLOAD(type), slot);
					mv.visitMethodInsn(INVOKEINTERFACE, "bradypod/framework/agent/core/asm/Printer", "println",
							ASMUtil.getDescriptor(type, true), true);

					// 计算opcode在frame中slot的大小, 64位的long&double占2位
					slot += paramType.getSize();
				} // --> end for
			} // --> 打印所有入参请求和返回值
		} // --> end fork
	}

	@Override
	public void visitInsn(int opcode) {
		if (opcode >= IRETURN && opcode <= ARETURN) {
			// figure long and double
			if (opcode == LRETURN || opcode == DRETURN) {
				mv.visitInsn(DUP2);
			} else {
				
				// print return ref
				mv.visitInsn(DUP);
				mv.visitVarInsn(ASMUtil.getXSTORE(adviceMethod.getReturnType()), slot);
				
				mv.visitLdcInsn(descriptor);
				mv.visitMethodInsn(INVOKESTATIC, "bradypod/framework/agent/core/asm/AdviceWeaver", "getPrinter",
						"(Ljava/lang/String;)Lbradypod/framework/agent/core/asm/Printer;", false);
				
				mv.visitVarInsn(ASMUtil.getXLOAD(adviceMethod.getReturnType()), slot);
				
				mv.visitMethodInsn(INVOKEINTERFACE, "bradypod/framework/agent/core/asm/Printer", "println",
						Type.getMethodDescriptor(Type.VOID_TYPE, Type.getType(adviceMethod.getReturnType())), true);
				
				slot += Type.getType(adviceMethod.getReturnType()).getSize();
			}
		} else if (opcode == ATHROW) {
			// print Exception message
			mv.visitInsn(DUP);
			mv.visitVarInsn(ASMUtil.getXSTORE(Exception.class), slot);
			
			mv.visitLdcInsn(descriptor);
			mv.visitMethodInsn(INVOKESTATIC, "bradypod/framework/agent/core/asm/AdviceWeaver", "getPrinter",
					"(Ljava/lang/String;)Lbradypod/framework/agent/core/asm/Printer;", false);
			
			mv.visitVarInsn(ASMUtil.getXLOAD(Exception.class), slot);
			
			mv.visitMethodInsn(INVOKEINTERFACE, "bradypod/framework/agent/core/asm/Printer", "println",
					Type.getMethodDescriptor(Type.VOID_TYPE, Type.getType(Exception.class)), true);
			
			slot += Type.getType(Exception.class).getSize();
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