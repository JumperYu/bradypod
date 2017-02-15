package bradypod.framework.agent.core.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 采用子类继承的方式修改字节码, 做到动态无痕迹.
 * 
 * @author	xiangmin.zxm
 * @date	2017/02/15
 */
public class AdviceWeaver extends ClassVisitor implements Opcodes {

	private String targetMethod;
	
	private String superName;

	private static final String SUB_CLASS_NAME_EXT = "$Enhanced";
	
	/**
	 * 指定需要拦截的方法
	 * 
	 * @param cv			-- ClassWriter
	 * @param targetMethod	-- 目标方法
	 */
	public AdviceWeaver(ClassVisitor cv, String targetMethod) {
		super(Opcodes.ASM5, cv);
		this.targetMethod = targetMethod;
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		// 修改为特殊子类继承原有类
		if ((access & ACC_INTERFACE) != ACC_INTERFACE) {
			superName = name;
			this.superName = superName; // 保留原类的描述 
			name = name.concat(SUB_CLASS_NAME_EXT);
		}
		super.visit(version, access, name, signature, superName, interfaces);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
		if (mv != null) {
			// 动态无痕修改字节码, 新建子类继承目标类
			if (name.equals("<init>")) {
				mv.visitVarInsn(ALOAD, 0);
				mv.visitMethodInsn(INVOKESPECIAL, this.superName, "<init>", "()V", false);
				mv.visitInsn(RETURN);
				mv.visitMaxs(1, 1);
			} else if (name.equals(targetMethod)) {
				mv = new AdviceAdapter(mv, this.superName, targetMethod);
			}
		}
		return mv;
	}
}