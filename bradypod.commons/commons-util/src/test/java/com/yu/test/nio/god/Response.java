package com.yu.test.nio.god;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class Response {

	private String content;

	public Response(String content) {
		this.content = content;
	}

	private ByteBuffer head = null;

	public void send(SocketChannel channel) throws IOException {
		prepare();
		if (head != null && head.hasRemaining()) {
			channel.write(head);
		}
		if (content != null && content.length() > 0) {
			channel.write(ByteBuffer.wrap(content.getBytes()));
		}
	}

	private static String CRLF = "\r\n";
	private static Charset ascii = Charset.forName("US-ASCII");

	private void prepare() {
		head = setHeaders();
	}

	private ByteBuffer setHeaders() {
		CharBuffer cb = CharBuffer.allocate(1024);
		for (;;) {
			try {
				cb.put("HTTP/1.0 ").put("200").put(CRLF);
				cb.put("Server: niossl/0.1").put(CRLF);
				cb.put("Content-type: ").put("text/plain").put(CRLF);
				cb.put("Content-length: ")
						.put(String.valueOf(content.length())).put(CRLF);
				cb.put(CRLF);
				break;
			} catch (BufferOverflowException x) {
				assert (cb.capacity() < (1 << 16));
				cb = CharBuffer.allocate(cb.capacity() * 2);
				continue;
			}
		}
		cb.flip();
		return ascii.encode(cb);
	}
}
