package bradypod.framework.agent.core;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import bradypod.framework.agent.core.asm.Printer;
import bradypod.framework.agent.core.cmd.Command;

public class DefaultCommandHandler {

	// private final GaServer gaServer;
	private final Instrumentation inst;

	public DefaultCommandHandler(GaServer gaServer, Instrumentation inst) {
		// this.gaServer = gaServer;
		this.inst = inst;
	}

	public void executeCommand(final String line, final Session session) throws IOException {

		Command command = Commands.getInstanst().newCommand(line);

		execute(session, command);
	}

	/*
	 * 执行命令
	 */
	private void execute(final Session session, Command command) throws IOException {

		final Printer printer = new Printer() {
			@Override
			public void println(String msg) {
				try {
					write(session.getSocketChannel(), ">" + msg, session.getCharset());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void println(int msg) {
				println(String.valueOf(msg));
			}

			@Override
			public void println(Exception exception) {
				println(exception.getMessage());
			}
		};

		EnhanceAction action = (EnhanceAction) command.action();
		// Enhancer enhancer = 
		action.enhance(session, inst, printer);

		//
		// final String line = "";
		//
		// if (line.equals("threads")) {
		// // 触手
		// session.touch();
		// // 通道
		// final SocketChannel socketChannel = session.getSocketChannel();
		// // 绘制表格
		// Table table = new Table();
		// // 添加表头
		// table.setHeader("tId", "tName", "cpuTime", "tState", "stackTrace");
		// final ThreadMXBean threadMXBean =
		// ManagementFactory.getThreadMXBean();
		// for (ThreadInfo tInfo :
		// threadMXBean.getThreadInfo(threadMXBean.getAllThreadIds(),
		// Integer.MAX_VALUE)) {
		// final long tId = tInfo.getThreadId();
		// final String tName = tInfo.getThreadName();
		// final long cpuTime = threadMXBean.getThreadCpuTime(tId);
		// final String tStateStr = tInfo.getThreadState().toString();
		// // final StackTraceElement[] stackTrace = tInfo.getStackTrace();
		// // for (StackTraceElement stackTraceElement : stackTrace)
		// // System.out.println(stackTraceElement);
		// // 添加内容
		// table.addBody("" + tId, tName, "" + cpuTime, tStateStr, "stack");
		// }
		// // 输出到客户端
		// write(socketChannel, table.toString(), session.getCharset());
		// } else if (line.contains("count")) {
		//
		// String[] cmds = line.split(" ");
		//
		// String className = cmds[1]; // "com.bradypod.reflect.jdk.Programmer";
		// String methodName = cmds[2]; // "doCoding";
		//
		// ClassReader classReader = new ClassReader(className);
		// ClassWriter classWriter = new
		// ClassWriter(ClassWriter.COMPUTE_FRAMES);
		// ClassVisitor classVisitor = new AdviceWeaver(classWriter,
		// methodName);
		// classReader.accept(classVisitor, 0);
		//
		// byte[] classData = classWriter.toByteArray();
		//
		// try {
		// inst.redefineClasses(new ClassDefinition(Class.forName(className),
		// classData));
		// } catch (ClassNotFoundException e) {
		// e.printStackTrace();
		// } catch (UnmodifiableClassException e) {
		// e.printStackTrace();
		// }
		// } else {
		// session.touch();
		// write(session.getSocketChannel(), "you say:" + line,
		// session.getCharset());
		// System.out.println("receive cmd: " + line);
		// }
	}

	/**
	 * 输出到网络
	 */
	private void write(SocketChannel socketChannel, String message, Charset charset) throws IOException {
		write(socketChannel, charset.encode(message));
	}

	private void write(SocketChannel socketChannel, ByteBuffer buffer) throws IOException {
		while (buffer.hasRemaining() && socketChannel.isConnected()) {
			if (-1 == socketChannel.write(buffer)) {
				// socket broken
				throw new IOException("write EOF");
			}
		}
	}

}
