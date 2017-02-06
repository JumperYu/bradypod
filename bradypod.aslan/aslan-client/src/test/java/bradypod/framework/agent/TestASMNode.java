package bradypod.framework.agent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import bradypod.framework.agent.core.asm.CounterClassAdapter;

public class TestASMNode implements Opcodes {

	@SuppressWarnings("unchecked")
	@Test
	public void test01() throws FileNotFoundException {
		ClassNode classNode = new ClassNode();
		classNode.version = Opcodes.V1_8;
		classNode.access = Opcodes.ACC_PUBLIC;
		classNode.name = "bytecode/TreeMethodGenClass";
		classNode.superName = "java/lang/Object";
		classNode.fields.add(new FieldNode(Opcodes.ACC_PRIVATE, "espresso", "I", null, null));
		// public void addEspresso(int espresso) 方法生命
		MethodNode mn = new MethodNode(Opcodes.ACC_PUBLIC, "addEspresso", "(I)V", null, null);
		classNode.methods.add(mn);
		InsnList il = mn.instructions;

		// 增加指令
		il.add(new FieldInsnNode(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
		il.add(new VarInsnNode(Opcodes.ALOAD, 1));
		il.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false));

		il.add(new VarInsnNode(Opcodes.ILOAD, 1));
		il.add(new InsnNode(Opcodes.ICONST_1));

		LabelNode label = new LabelNode();
		// if (espresso > 0) 跳转通过LabelNode标记跳转地址
		il.add(new JumpInsnNode(Opcodes.IF_ICMPLE, label));
		il.add(new VarInsnNode(Opcodes.ALOAD, 0));
		il.add(new VarInsnNode(Opcodes.ILOAD, 1));
		// this.espresso = var1;
		il.add(new FieldInsnNode(Opcodes.PUTFIELD, "bytecode/TreeMethodGenClass", "espresso", "I"));
		LabelNode end = new LabelNode();
		il.add(new JumpInsnNode(Opcodes.GOTO, end));
		// label 后紧跟着下一个指令地址
		il.add(label);
		// java7之后对stack map frame 的处理
		il.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));
		// throw new IllegalArgumentException();
		il.add(new TypeInsnNode(Opcodes.NEW, "java/lang/IllegalArgumentException"));
		il.add(new InsnNode(Opcodes.DUP));
		il.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "java/lang/IllegalArgumentException", "<init>", "()V", false));
		il.add(new InsnNode(Opcodes.ATHROW));
		il.add(end);
		// stack map 的第二次偏移记录
		il.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));
		il.add(new InsnNode(Opcodes.RETURN));
		// 局部变量表和操作数栈大小的处理
		mn.maxStack = 2;
		mn.maxLocals = 2;
		mn.visitEnd();
		// 打印查看class的生成结果
		ClassWriter cw = new ClassWriter(Opcodes.ASM5);
		classNode.accept(cw);
		File file = new File("D://TreeMethodGenClass.class");
		FileOutputStream fout = new FileOutputStream(file);
		try {
			fout.write(cw.toByteArray());
			fout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test02() throws IOException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException {
		ClassReader cr = new ClassReader("com.bradypod.reflect.jdk.Programmer");
		ClassNode cn = new ClassNode();
		cr.accept(cn, 0);
		for (Object obj : cn.methods) {
			MethodNode md = (MethodNode) obj;
			if ("<init>".endsWith(md.name) || "<clinit>".equals(md.name)) {
				continue;
			}
			InsnList insns = md.instructions;
			InsnList il = new InsnList();

			il.add(new FieldInsnNode(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
			// il.add(new LdcInsnNode("Enter method-> " + cn.name + "." +
			// md.name));
			il.add(new VarInsnNode(LLOAD, 2));
			// il.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
			// "java/io/PrintStream", "println", "(Ljava/lang/String;)V",
			// false));
			il.add(new MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(J)V", false));

			insns.insert(il);
			md.maxStack += 3;

		}
		ClassWriter cw = new ClassWriter(0);
		cn.accept(cw);

		File file = new File("D://com/bradypod/reflect/jdk/Programmer.class");
		FileOutputStream fout = new FileOutputStream(file);
		try {
			fout.write(cw.toByteArray());
			fout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Class<?> programClass = Class.forName("com.bradypod.reflect.jdk.Programmer", true, new MyClassLoader());
		Object programObj = programClass.newInstance();
		programClass.getMethod("doCoding", String.class, long.class, TimeUnit.class).invoke(programObj, "hehhe", 5,
				TimeUnit.SECONDS);
	}

	@Test
	public void test03() throws IOException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException {
		String className = "com.bradypod.reflect.jdk.Programmer";

		ClassReader classReader = new ClassReader(className);
		ClassWriter classWriter = new ClassWriter(classReader, 0);
		ClassVisitor classVisitor = new CounterClassAdapter(classWriter, className, "doCoding");
		classReader.accept(classVisitor, 0);

		byte[] classData = classWriter.toByteArray();

		File file = new File("D://com/bradypod/reflect/jdk/Programmer.class");
		FileOutputStream fout = new FileOutputStream(file);
		try {
			fout.write(classData);
			fout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Class<?> programClass = Class.forName("com.bradypod.reflect.jdk.Programmer", true, new MyClassLoader());
		Object programObj = programClass.newInstance();
		programClass.getMethod("doCoding", String.class, long.class, TimeUnit.class).invoke(programObj, "hehhe", 5,
				TimeUnit.SECONDS);
	}

}

class MyClassLoader extends ClassLoader {

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		final Class<?> loadedClass = findLoadedClass(name);
		if (loadedClass != null) {
			return loadedClass;
		}
		try {
			if ("com.bradypod.reflect.jdk.Programmer".equals(name)) {
				Class<?> aClass = findClass(name);
				resolveClass(aClass);
				return aClass;
			} else {
				return super.loadClass(name);
			}
		} catch (Exception e) {
			return super.loadClass(name);
		}
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] classBytes = null;
		try {
			classBytes = Files.readAllBytes(Paths.get("D://com/bradypod/reflect/jdk/Programmer.class"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Class<?> clazz = defineClass("com.bradypod.reflect.jdk.Programmer", classBytes, 0, classBytes.length);
		return clazz;
	}
}