package bradypod.framework.agent;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import bradypod.framework.agent.core.asm.CounterClassAdapter;

public class TestCommand {

	@Test
	public void testThreadMxBean() {
		final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
		// long totalCpuTime = threadMXBean.getCurrentThreadCpuTime();
		for (ThreadInfo tInfo : threadMXBean.getThreadInfo(
				threadMXBean.getAllThreadIds(), Integer.MAX_VALUE)) {
			final long tId = tInfo.getThreadId();
			final String tName = tInfo.getThreadName();
			final long cpuTime = threadMXBean.getThreadCpuTime(tId);
			final String tStateStr = tInfo.getThreadState().toString();
			final StackTraceElement[] stackTrace = tInfo.getStackTrace();
			System.out
					.format("------threadId:%d,threadName:%s,cpuTime:%d,threadState:%s,stackTrace:%s\n",
							tId, tName, cpuTime, tStateStr, "");
			for (StackTraceElement stackTraceElement : stackTrace)
				System.out.println(stackTraceElement);
		}
	}
	
	@Test
	public void testMethodAdapter() throws IOException {
		
		String className = "com.bradypod.reflect.jdk.Programmer";
		
		ClassReader classReader = new ClassReader(className);
		ClassWriter classWriter = new ClassWriter(classReader, 0);
		ClassVisitor classVisitor = new CounterClassAdapter(classWriter, "doCoding");
		classReader.accept(classVisitor, 0);

		byte[] classData = classWriter.toByteArray();
		
		Files.write(Paths.get("D://Programmer.class"), classData, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
	}
}
