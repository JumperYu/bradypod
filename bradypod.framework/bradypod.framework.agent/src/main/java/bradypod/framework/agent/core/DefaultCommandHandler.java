package bradypod.framework.agent.core;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import bradypod.framework.agent.core.ui.Table;

public class DefaultCommandHandler {

	private final GaServer gaServer;
	private final Instrumentation inst;

	public DefaultCommandHandler(GaServer gaServer, Instrumentation inst) {
		this.gaServer = gaServer;
		this.inst = inst;
	}

	public void executeCommand(final String line, final Session session)
			throws IOException {
		System.out.println(line);
		execute(session, "threads");
	}

	/*
	 * 执行命令
	 */
	private void execute(final Session session, final String command)
			throws IOException {
		// 触手
		session.touch();
		// 通道
		final SocketChannel socketChannel = session.getSocketChannel();
		// 绘制表格
		Table table = new Table();
		// 添加表头
		table.setHeader("tId", "tName", "cpuTime", "tState", "stackTrace");
		final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
		for (ThreadInfo tInfo : threadMXBean.getThreadInfo(
				threadMXBean.getAllThreadIds(), Integer.MAX_VALUE)) {
			final long tId = tInfo.getThreadId();
			final String tName = tInfo.getThreadName();
			final long cpuTime = threadMXBean.getThreadCpuTime(tId);
			final String tStateStr = tInfo.getThreadState().toString();
			// final StackTraceElement[] stackTrace = tInfo.getStackTrace();
			// for (StackTraceElement stackTraceElement : stackTrace)
			// System.out.println(stackTraceElement);
			// 添加内容
			table.addBody("" + tId, tName, "" + cpuTime, tStateStr, "stack");
		}
		// 输出到客户端 
		write(socketChannel, table.toString(), session.getCharset());
	}

	/**
	 * 输出到网络
	 */
	private void write(SocketChannel socketChannel, String message,
			Charset charset) throws IOException {
		write(socketChannel, charset.encode(message));
	}

	private void write(SocketChannel socketChannel, ByteBuffer buffer)
			throws IOException {
		while (buffer.hasRemaining() && socketChannel.isConnected()) {
			if (-1 == socketChannel.write(buffer)) {
				// socket broken
				throw new IOException("write EOF");
			}
		}
	}
}
