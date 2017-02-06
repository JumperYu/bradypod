package bradypod.framework.agent.core;

import static java.nio.channels.SelectionKey.OP_READ;

import java.io.Closeable;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

public class GaServer {

	private static final int BUFFER_SIZE = 4 * 1024;
	private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	private static final byte CTRL_D = 0x04;
	private static final byte CTRL_X = 0x18;
//	private static final byte EOT = 0x04;
	private static final int EOF = -1;
	private static final int CR = 0x0D; //回车
	private static final int NL = 0x0A; //换行

	private final AtomicBoolean isBindRef = new AtomicBoolean(false);
	private final int javaPid;
	private final Thread jvmShutdownHooker = new Thread("ga-shutdown-hooker") {

		@Override
		public void run() {
			GaServer.this._destroy();
		}
	};
	
	private final SessionManager sessionManager;
	private final DefaultCommandHandler commandHandler;

	private final ExecutorService executorService = Executors
			.newCachedThreadPool(new ThreadFactory() {
				@Override
				public Thread newThread(Runnable r) {
					final Thread t = new Thread(r, "ga-command-execute-daemon");
					t.setDaemon(true);
					return t;
				}
			});
	
	private GaServer(int javaPid, Instrumentation inst) {
		this.javaPid = javaPid;
		this.sessionManager = new DefaultSessionManager();
		this.commandHandler = new DefaultCommandHandler(this, inst);
		Runtime.getRuntime().addShutdownHook(jvmShutdownHooker);
	}

	/**
	 * 判断服务端是否已经启动
	 *
	 * @return true:服务端已经启动;false:服务端关闭
	 */
	public boolean isBind() {
		return isBindRef.get();
	}

	private ServerSocketChannel serverSocketChannel = null;
	private Selector selector = null;

	/**
	 * 启动Greys服务端
	 *
	 * @param configure
	 *            配置信息
	 * @throws IOException
	 *             服务器启动失败
	 */
	public void bind(Configure configure) throws IOException {
		if (!isBindRef.compareAndSet(false, true)) {
			throw new IllegalStateException("already bind");
		}

		try {

			serverSocketChannel = ServerSocketChannel.open();
			selector = Selector.open();

			serverSocketChannel.configureBlocking(false);
			serverSocketChannel.socket().setSoTimeout(
					configure.getConnectTimeout());
			serverSocketChannel.socket().setReuseAddress(true);
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

			// 服务器挂载端口
			serverSocketChannel.socket().bind(
					getInetSocketAddress(configure.getTargetIp(),
							configure.getTargetPort()), 24);

			LogUtil.log("ga-server listening on network={};port={};timeout={};");

			activeSelectorDaemon(selector, configure);

		} catch (IOException e) {
			unbind();
			throw e;
		}

	}

	/*
	 * 获取绑定网络地址信息<br/> 这里做个小修正,如果targetIp为127.0.0.1(本地环回口)，则需要绑定所有网卡
	 * 否则外部无法访问，只能通过127.0.0.1来进行了
	 */
	private InetSocketAddress getInetSocketAddress(String targetIp,
			int targetPort) {
		return new InetSocketAddress(targetIp, targetPort);
	}

	private void activeSelectorDaemon(final Selector selector,
			final Configure configure) {

		final ByteBuffer byteBuffer = ByteBuffer.allocate(BUFFER_SIZE);

		final Thread gaServerSelectorDaemon = new Thread("ga-selector-daemon") {
			@Override
			public void run() {

				while (!isInterrupted() && isBind()) {

					try {

						while (selector.isOpen() && selector.select() > 0) {
							final Iterator<SelectionKey> it = selector
									.selectedKeys().iterator();
							while (it.hasNext()) {
								final SelectionKey key = it.next();
								it.remove();

								// do ssc accept
								if (key.isValid() && key.isAcceptable()) {
									doAccept(key, selector, configure);
								}

								// do sc read
								if (key.isValid() && key.isReadable()) {
									doRead(byteBuffer, key);
								}

							}
						}

					} catch (IOException e) {
						// logger.warn("selector failed.", e);
					} catch (ClosedSelectorException e) {
						// logger.debug("selector closed.", e);
					}

				}

			}
		};
		gaServerSelectorDaemon.setDaemon(true);
		gaServerSelectorDaemon.start();
	}

	private void doAccept(SelectionKey key, Selector selector,
			Configure configure) throws IOException {
		final ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key
				.channel();
		acceptSocketChannel(selector, serverSocketChannel, configure);
	}

	private SocketChannel acceptSocketChannel(Selector selector,
			ServerSocketChannel serverSocketChannel, Configure configure)
			throws IOException {
		final SocketChannel socketChannel = serverSocketChannel.accept();
		socketChannel.configureBlocking(false);
		socketChannel.socket().setSoTimeout(configure.getConnectTimeout());
		socketChannel.socket().setTcpNoDelay(true);

		final Session session = sessionManager.newSession(javaPid,
				socketChannel, DEFAULT_CHARSET);
		socketChannel.register(selector, OP_READ, new GaAttachment(BUFFER_SIZE,
				session));
		LogUtil.log("accept new connection, client={}@session[{}]"
				+ session.getSessionId());

		LogUtil.log("accept new connection, client={}@session[]");

		// 输出欢迎内容
		writeToSocketChannel(socketChannel, DEFAULT_CHARSET,
				"Welcome to use bradypod's agent" + System.lineSeparator());

		// Logo结束之后输出传输中止符
//		 writeToSocketChannel(socketChannel, ByteBuffer.wrap(new byte[] { EOT
//		 }));

		return socketChannel;
	}

	private void doRead(final ByteBuffer byteBuffer, SelectionKey key) {
		final GaAttachment attachment = (GaAttachment) key.attachment();
		final SocketChannel socketChannel = (SocketChannel) key.channel();
		final Session session = attachment.getSession();
		try {

			// 若读到EOF，则说明SocketChannel已经关闭
			if (EOF == socketChannel.read(byteBuffer)) {
				LogUtil.log("client={}@session[{}] was closed."
						+ session.getSessionId());
				closeSocketChannel(key, socketChannel);
				session.destroy();
				if (session.isLocked()) {
					session.unLock();
				}
				return;
			}

			// decode for line
			byteBuffer.flip();
			while (byteBuffer.hasRemaining()) {
				switch (attachment.getLineDecodeState()) {
				case READ_CHAR: {
					final byte data = byteBuffer.get();

					if (NL == data) {
						// switch 语句继续
						attachment.setLineDecodeState(LineDecodeState.READ_EOL);
					} else if (CTRL_D == data || CTRL_X == data) {
						// 遇到中止命令(CTRL_D)，则标记会话为不可写，让后台任务停下
						session.unLock();
						break;
					} else {
						// 普通byte则持续放入到缓存中
						if (CR != data) {
							attachment.put(data);
						}
						break;
					}

				}
				case READ_EOL: {
					final String line = attachment.clearAndGetLine(session
							.getCharset());

					executorService.execute(new Runnable() {
						@Override
						public void run() {

							// 会话只有未锁定的时候才能响应命令
							if (session.tryLock()) {
								try {

									// 命令执行
									commandHandler
											.executeCommand(line, session);

									// 命令结束之后需要传输EOT告诉client命令传输已经完结，可以展示提示符
									// socketChannel.write(ByteBuffer
									// .wrap(new byte[] { EOT }));

								} catch (Exception e) {
									LogUtil.log("network communicate failed, session[{}] will be close."
											+ session.getSessionId());
									session.destroy();
								} finally {
									session.unLock();
								}
							} else {
								LogUtil.log("session[{}] was locked, ignore this command."
										+ session.getSessionId());
							}
						}
					});

					attachment.setLineDecodeState(LineDecodeState.READ_CHAR);
					break;
				}
				}
			}// while for line decode

			byteBuffer.clear();

		}

		// 处理
		catch (IOException e) {
			LogUtil.log("read/write data failed, session[] will be close.");
			e.printStackTrace();
			closeSocketChannel(key, socketChannel);
		}
	}

	private void writeToSocketChannel(SocketChannel socketChannel,
			Charset charset, String message) throws IOException {
		writeToSocketChannel(socketChannel,
				ByteBuffer.wrap(message.getBytes(charset)));
	}

	private void writeToSocketChannel(SocketChannel socketChannel,
			ByteBuffer buffer) throws IOException {
		while (buffer.hasRemaining()) {
			socketChannel.write(buffer);
		}
	}

	private void closeSocketChannel(SelectionKey key,
			SocketChannel socketChannel) {
		closeQuietly(socketChannel);
		key.cancel();
	}

	/**
	 * 关闭Greys服务端
	 */
	public void unbind() {

		closeQuietly(serverSocketChannel);
		closeQuietly(selector);

		if (!isBindRef.compareAndSet(true, false)) {
			throw new IllegalStateException("already unbind");
		}
	}

	private void _destroy() {
		if (isBind()) {
			unbind();
		}

		executorService.shutdown();

		LogUtil.log("ga-server destroy completed.");
	}

	public void destroy() {
		Runtime.getRuntime().removeShutdownHook(jvmShutdownHooker);
		_destroy();
	}

	private static volatile GaServer gaServer;

	/**
	 * 单例
	 *
	 * @param instrumentation
	 *            JVM增强
	 * @return GaServer单例
	 */
	public static GaServer getInstance(final int javaPid,
			final Instrumentation instrumentation) {
		if (null == gaServer) {
			synchronized (GaServer.class) {
				if (null == gaServer) {
					gaServer = new GaServer(javaPid, instrumentation);
				}
			}
		}
		return gaServer;
	}

	public static void closeQuietly(Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (IOException ioe) {
			// ignore
		}
	}

}

class GaAttachment {

	private final int bufferSize;
	private final Session session;

	private LineDecodeState lineDecodeState;
	private ByteBuffer lineByteBuffer;

	GaAttachment(int bufferSize, Session session) {
		this.lineByteBuffer = ByteBuffer.allocate(bufferSize);
		this.bufferSize = bufferSize;
		this.lineDecodeState = LineDecodeState.READ_CHAR;
		this.session = session;
	}

	public LineDecodeState getLineDecodeState() {
		return lineDecodeState;
	}

	public void setLineDecodeState(LineDecodeState lineDecodeState) {
		this.lineDecodeState = lineDecodeState;
	}

	public void put(byte data) {
		if (lineByteBuffer.hasRemaining()) {
			lineByteBuffer.put(data);
		} else {
			final ByteBuffer newLineByteBuffer = ByteBuffer
					.allocate(lineByteBuffer.capacity() + bufferSize);
			lineByteBuffer.flip();
			newLineByteBuffer.put(lineByteBuffer);
			newLineByteBuffer.put(data);
			this.lineByteBuffer = newLineByteBuffer;
		}
	}

	public String clearAndGetLine(Charset charset) {
		lineByteBuffer.flip();
		final byte[] dataArray = new byte[lineByteBuffer.limit()];
		lineByteBuffer.get(dataArray);
		final String line = new String(dataArray, charset);
		lineByteBuffer.clear();
		return line;
	}

	public Session getSession() {
		return session;
	}

}

/**
 * 行解码
 */
enum LineDecodeState {

	// 读字符
	READ_CHAR,

	// 读换行
	READ_EOL
}
