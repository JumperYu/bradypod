package bradypod.framework.agent.core.asm;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

public class AsmLaunch {

	public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		String className = "com.bradypod.reflect.jdk.Programmer";
		
		ClassReader classReader = new ClassReader(className);
		ClassWriter classWriter = new ClassWriter(classReader, 0);
		ClassVisitor classVisitor = new CounterClassAdapter(classWriter, className, "doCoding");
		classReader.accept(classVisitor, 0);

		byte[] classData = classWriter.toByteArray();
		
//		Files.write(Paths.get("D://Programmer.class"), classData, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		
		Class<?> programClz = new MyClassLoader().defineClassFromClassFile(className, classData);
		Object programObj = programClz.newInstance();
		programClz.getMethod("doCoding", (Class<?>[]) null).invoke(programObj, (Object[]) null);
	}

}

class MyClassLoader extends ClassLoader {
	public Class<?> defineClassFromClassFile(String className, byte[] classFile) throws ClassFormatError {
		return defineClass(className, classFile, 0, classFile.length);
	}
}
