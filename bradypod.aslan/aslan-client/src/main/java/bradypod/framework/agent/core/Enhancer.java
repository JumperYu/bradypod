package bradypod.framework.agent.core;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import bradypod.framework.agent.core.asm.AdviceWeaver;
import bradypod.framework.agent.core.asm.Printer;

public class Enhancer implements ClassFileTransformer {

	private String classPattern;
	private String methodPattern;

	public Enhancer(String classPattern, String methodPattern) {
		this.classPattern = classPattern.replaceAll("\\.", "/");
		this.methodPattern = methodPattern;
	}

	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

		if (isIgnore(className)) {
			return null;
		}

		ClassReader classReader = new ClassReader(classfileBuffer);
		ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		ClassVisitor classVisitor = new AdviceWeaver(classWriter, methodPattern, false);
		classReader.accept(classVisitor, 0);

		byte[] classData = classWriter.toByteArray();

		return classData;
	}

	private boolean isIgnore(String className) {
		return !className.equals(classPattern) || className.equals("bradypod/framework/agent/core/Enhancer");
	}
	
	public void addPrinter(Printer printer) {
		AdviceWeaver.reg(classPattern, printer);
	}
}
