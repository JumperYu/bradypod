package com.yu.test.nio.my;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * 请求服务
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年3月15日
 */
public class RequestService implements Runnable {

	private ChannelIO cio;

	/**
	 * 初始化
	 */
	public RequestService(ChannelIO cio) {
		this.cio = cio;
	}

	@Override
	public void run() {
		try {
			service();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void service() throws IOException {
		Reply rp = null;
		try {
			ByteBuffer buffer = receive();
			Request request = null;
			try {
				request = Request.parse(buffer);
			} catch (MalformedRequestException e) {
				rp = new Reply(Reply.Code.BAD_REQUEST, new StringContent(e));
			}
			rp = buildReply(request);
			do {
			} while (rp.send(cio)); // Send
			/*
			 * do { } while (!cio.shutdown());
			 */
			cio.close();
			rp.release();
		} catch (IOException e) {
			String m = e.getMessage();
			if (!m.equals("Broken pipe")
					&& !m.equals("Connection reset by peer")) {
				System.err.println("RequestHandler: " + e.toString());
			}

			cio.shutdown();

			try {
				cio.close();
			} catch (IOException e1) {
				// ignore
			}

			if (rp != null) {
				rp.release();
			}
		}
	}

	/**
	 * 接收数据, 并返回缓冲
	 * 
	 * @throws IOException
	 */
	public ByteBuffer receive() throws IOException {
		for (;;) {
			int read = cio.read();
			ByteBuffer bb = cio.getRequestBuffer();
			if ((read < 0) || (Request.isComplete(bb))) {
				bb.flip();
				return bb;
			}
		}
	}

	/**
	 * 创建答复
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	private Reply buildReply(Request request) throws IOException {
		Reply reply = null;
		Request.Action action = request.action();
		if ((action != Request.Action.GET) && (action != Request.Action.HEAD)) {
			reply = new Reply(Reply.Code.METHOD_NOT_ALLOWED, new StringContent(
					request.toString()));
		} else {
			reply = new Reply(Reply.Code.OK, new StringContent("welcome!"),
					action);
		}
		try {
			reply.prepare();
		} catch (IOException x) {
			reply.release();
			reply = new Reply(Reply.Code.NOT_FOUND, new StringContent(x));
			reply.prepare();
		}
		return reply;
	}
}
