package bradypod.framework.agent;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

import org.junit.Test;

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
}
