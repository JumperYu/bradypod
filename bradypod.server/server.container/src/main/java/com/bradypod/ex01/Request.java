package com.bradypod.ex01;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Request {

	SocketChannel channel;
	ByteBuffer buffer;
	URI uri;
	String host;
	int port;

	static final int CAPACITY = 1024;

	public Request(SocketChannel channel) {
		this.channel = channel;
		this.buffer = ByteBuffer.allocate(CAPACITY);
	}

	public void parse() throws IOException, URISyntaxException {
		for (;;) {
			if (buffer.remaining() < CAPACITY) {
				ByteBuffer newBuffer = ByteBuffer
						.allocate(buffer.capacity() * 2);
				newBuffer.put(buffer);
				buffer = newBuffer;
			}
			int readLen = channel.read(buffer);
			if (readLen <= 0 || Request.isComplete(buffer)) {
				break;
			}
		}
		buffer.flip();
		CharBuffer cb = ascii.decode(buffer);
		Matcher m = requestPattern.matcher(cb);
		if (!m.matches())
			return;
		// set prop
		this.uri = new URI(m.group(2));
		this.host = m.group(4);
	}

	static Charset ascii = Charset.forName("US-ASCII");

	static Pattern requestPattern = Pattern.compile(
			"\\A([A-Z]+) +([^ ]+) +HTTP/([0-9\\.]+)$"
					+ ".*^Host: ([^ ]+)$.*\r\n\r\n\\z", Pattern.MULTILINE
					| Pattern.DOTALL);

	static boolean isComplete(ByteBuffer bb) {
		int p = bb.position() - 4;
		if (p < 0)
			return false;
		return (((bb.get(p + 0) == '\r') && (bb.get(p + 1) == '\n')
				&& (bb.get(p + 2) == '\r') && (bb.get(p + 3) == '\n')));
	}

	public URI getUri() {
		return uri;
	}

	@Override
	public String toString() {
		return uri != null ? uri.toString() : "bad request!";
	}
}
