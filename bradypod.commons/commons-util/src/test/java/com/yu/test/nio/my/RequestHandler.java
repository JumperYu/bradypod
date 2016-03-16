package com.yu.test.nio.my;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

public class RequestHandler implements Handler {

	private ChannelIO cio;
	private ByteBuffer rbb = null;

	private boolean requestReceived = false;
	private Request request = null;
	private Reply reply = null;

	private static int created = 0;

	public RequestHandler(ChannelIO cio) {
		this.cio = cio;

		synchronized (RequestHandler.class) {
			created++;
			if ((created % 50) == 0) {
				System.out.println(".");
				created = 0;
			} else {
				System.out.print(".");
			}
		}
	}

	@Override
	public void handle(SelectionKey selectionKey) throws IOException {
		if (request == null) {
			if (!receive(selectionKey)) {
				return;
			}
			rbb.flip();
			if (parse()) {
				build();
			}
			reply.prepare();
			if (send()) {
				// More bytes remain to be written
				selectionKey.interestOps(SelectionKey.OP_WRITE);
			} else {
				// Reply completely written; we're done
				if (cio.shutdown()) {
					cio.close();
					reply.release();
				}
			}
		} else {
			if (!send()) { // Should be rp.send()
				if (cio.shutdown()) {
					cio.close();
					reply.release();
				}
			}
		}
	}

	private boolean receive(SelectionKey sk) throws IOException {
		// ByteBuffer tmp = null;
		if (requestReceived) {
			return true;
		}

		if ((cio.read() < 0) || Request.isComplete(cio.getRequestBuffer())) {
			rbb = cio.getRequestBuffer();
			return (requestReceived = true);
		}
		return false;
	}

	private boolean parse() throws IOException {
		try {
			request = Request.parse(rbb);
			return true;
		} catch (MalformedRequestException x) {
			reply = new Reply(Reply.Code.BAD_REQUEST, new StringContent(x));
		}
		return false;
	}

	private void build() throws IOException {
		Request.Action action = request.action();
		if ((action != Request.Action.GET) && (action != Request.Action.HEAD)) {
			reply = new Reply(Reply.Code.METHOD_NOT_ALLOWED, new StringContent(
					request.toString()));
		}
		reply = new Reply(Reply.Code.OK, new StringContent("Welcome!"), action);
	}

	private boolean send() throws IOException {
		try {
			return reply.send(cio);
		} catch (IOException x) {
			if (x.getMessage().startsWith("Resource temporarily")) {
				System.err.println("## RTA");
				return true;
			}
			throw x;
		}
	}
}
