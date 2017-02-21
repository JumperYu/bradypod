package bradypod.framework.agent;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import bradypod.framework.agent.core.asm.AdviceWeaver;

public class TestASMNode implements Opcodes {

	// ASM无伤修改字节码方式
	@Test
	public void test01() throws Exception {

		final String superClassName = "com.bradypod.reflect.jdk.Programmer";
		final String enhancedExt = "$Enhanced";

		Class<?> programClass = Class.forName(superClassName + enhancedExt, true, new ClassLoader() {

			@Override
			public Class<?> loadClass(String name) throws ClassNotFoundException {
				final Class<?> loadedClass = findLoadedClass(name);
				if (loadedClass != null) {
					return loadedClass;
				}
				try {
					if (name.equals(superClassName + enhancedExt)) {
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
			protected Class<?> findClass(String className) throws ClassNotFoundException {
				if (!className.equals(superClassName + enhancedExt)) {
					return super.findClass(className);
				}
				try {
					ClassReader cr = new ClassReader(superClassName);
					ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES); //
					cr.accept(new ClassVisitor(ASM5, cw) {
						String subClassName = superClassName + enhancedExt;

						@Override
						public void visit(int version, int access, String name, String signature, String superName,
								String[] interfaces) {
							cv.visit(version, access, subClassName.replaceAll("\\.", "/"), signature, name, interfaces);
						};

						@Override
						public MethodVisitor visitMethod(int access, String name, String desc, String signature,
								String[] exceptions) {
							MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
							if (mv != null) {
								if (name.equals("<init>")) {
									mv = new MethodVisitor(ASM5, mv) {
										public void visitCode() {
											mv.visitVarInsn(ALOAD, 0);
											mv.visitMethodInsn(INVOKESPECIAL, superClassName.replaceAll("\\.", "/"),
													"<init>", "()V", false);
											mv.visitInsn(RETURN);
											mv.visitMaxs(1, 1);
										};
									};
								} else if (name.equals("doCoding")) {
									mv = new MethodVisitor(ASM5, mv) {
										public void visitCode() {

											// System.println(word);
											mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out",
													"Ljava/io/PrintStream;");
											mv.visitVarInsn(ALOAD, 1);
											mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
													"(Ljava/lang/String;)V", false);

											// String ret =
											// super.doCoding(word);
											mv.visitVarInsn(ALOAD, 0);
											mv.visitVarInsn(ALOAD, 1);
											mv.visitVarInsn(ALOAD, 2);
											mv.visitMethodInsn(INVOKESPECIAL, "com/bradypod/reflect/jdk/Programmer",
													"doCoding",
													"(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", false);
											mv.visitVarInsn(ASTORE, 3);

											// System.println.out(ret);
											mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out",
													"Ljava/io/PrintStream;");
											mv.visitVarInsn(ALOAD, 3);
											mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
													"(Ljava/lang/String;)V", false);

											// return ret;
											mv.visitVarInsn(ALOAD, 3);
											mv.visitInsn(ARETURN);
										};
									};
								}
							}
							return mv;
						};
					}, 0);

					byte[] byteCode = cw.toByteArray();

					// Files.write(Paths.get("d://Programmer$Enhanced.class"),
					// byteCode, StandardOpenOption.CREATE);

					Class<?> clazz = defineClass(className, byteCode, 0, byteCode.length);
					return clazz;
				} catch (Throwable e) {
					e.printStackTrace();
					throw new ClassNotFoundException();
				}
			}

		});
		Object programObj = programClass.newInstance();
		programClass.getMethod("doCoding", String.class, String.class).invoke(programObj, "hello", "word");
	}

	@Test
	public void test02() throws Exception {
		final String superClassName = "com.bradypod.reflect.jdk.Programmer";
		final String enhancedExt = ""; // "$Enhanced";
		final String methodName = "doCoding";

		Class<?> programClass = Class.forName(superClassName + enhancedExt, true, new ClassLoader() {

			@Override
			public Class<?> loadClass(String name) throws ClassNotFoundException {
				final Class<?> loadedClass = findLoadedClass(name);
				if (loadedClass != null) {
					return loadedClass;
				}
				try {
					if (name.equals(superClassName + enhancedExt)) {
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
			protected Class<?> findClass(String className) throws ClassNotFoundException {
				if (!className.equals(superClassName + enhancedExt)) {
					return super.findClass(className);
				}
				try {
					ClassReader cr = new ClassReader(superClassName);
					ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES); //
					cr.accept(new AdviceWeaver(cw, methodName, false), ASM5);

					byte[] byteCode = cw.toByteArray();

					Class<?> clazz = defineClass(className, byteCode, 0, byteCode.length);
					return clazz;
				} catch (Throwable e) {
					e.printStackTrace();
					throw new ClassNotFoundException();
				}
			}

		});
		Object programObj = programClass.newInstance();
		programClass.getMethod(methodName, String.class, String.class).invoke(programObj, "hello", "world");
	}
}