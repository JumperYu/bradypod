package bradypod.framework.agent.core.asm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 采用子类继承的方式修改字节码, 做到动态无痕迹.
 * 
 * @author xiangmin.zxm
 * @date 2017/02/15
 */
public class AdviceWeaver extends ClassVisitor implements Opcodes {

	private String targetMethod;

	private String descriptor;

	private boolean isReDefinedClass = false;

	private static final String SUB_CLASS_NAME_EXT = "$Enhanced";

	private static final Map<String, Printer> weavers = new ConcurrentHashMap<>();

	/**
	 * 指定需要拦截的方法
	 * 
	 * @param cv
	 *            -- ClassWriter
	 * @param targetMethod
	 *            -- 目标方法
	 */
	public AdviceWeaver(ClassVisitor cv, String targetMethod, boolean isReDefinedClass) {
		super(Opcodes.ASM5, cv);
		this.targetMethod = targetMethod;
		this.isReDefinedClass = isReDefinedClass;
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		this.descriptor = name; // 保留原类的描述
		// 修改子类继承原有类
		if (isReDefinedClass && (access & ACC_INTERFACE) != ACC_INTERFACE) {
			superName = name;
			name = name.concat(SUB_CLASS_NAME_EXT);
		}
		super.visit(version, access, name, signature, superName, interfaces);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
		if (mv != null) {
			// 动态无痕修改字节码, 新建子类继承目标类
			if (isReDefinedClass && name.equals("<init>")) {
				mv.visitVarInsn(ALOAD, 0);
				mv.visitMethodInsn(INVOKESPECIAL, this.descriptor, "<init>", "()V", false);
				mv.visitInsn(RETURN);
				mv.visitMaxs(1, 1);
			} else if (name.equals(targetMethod)) {
				mv = new AdviceAdapter(mv, this.descriptor, targetMethod);
			}
		}
		return mv;
	}

	public static void reg(String classPattern, Printer printer) {
		weavers.put(classPattern, printer);
	}
	
	public static Printer getPrinter(String classPattern) {
		return weavers.get(classPattern);
	}
}