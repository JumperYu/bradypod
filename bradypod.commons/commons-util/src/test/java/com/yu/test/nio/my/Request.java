package com.yu.test.nio.my;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Request {

	/**
	 * 处理http动作
	 */
	static class Action {

		private String name;

		private Action(String name) {
			this.name = name;
		}

		static Action GET = new Action("GET");
		static Action PUT = new Action("PUT");
		static Action POST = new Action("POST");
		static Action HEAD = new Action("HEAD");

		static Action parse(String action) {
			switch (action) {
			case "GET":
				return GET;
			case "POST":
				return POST;
			case "PUT":
				return PUT;
			case "HEAD":
				return HEAD;
			default:
				throw new IllegalArgumentException(action);
			}
		}

		public String toString() {
			return name;
		}
	}

	private Action action;
	private String version;
	private URI uri;

	Action action() {
		return action;
	}

	String version() {
		return version;
	}

	URI uri() {
		return uri;
	}

	private Request(Action a, String v, URI u) {
		action = a;
		version = v;
		uri = u;
	}

	/* 中文 */
	private static Charset gbk = Charset.forName("gbk");

	/*
	 * The expected message format is first compiled into a pattern, and is then
	 * compared against the inbound character buffer to determine if there is a
	 * match. This convienently tokenizes our request into usable pieces.
	 * 
	 * This uses Matcher "expression capture groups" to tokenize requests like:
	 * 
	 * GET /dir/file HTTP/1.1 Host: hostname
	 * 
	 * into:
	 * 
	 * group[1] = "GET" group[2] = "/dir/file" group[3] = "1.1" group[4] =
	 * "hostname"
	 * 
	 * The text in between the parens are used to captured the regexp text.
	 */
	private static Pattern requestPattern = Pattern.compile(
			"\\A([A-Z]+) +([^ ]+) +HTTP/([0-9\\.]+)$"
					+ ".*^Host: ([^ ]+)$.*\r\n\r\n\\z", Pattern.MULTILINE
					| Pattern.DOTALL);

	public static Request parse(ByteBuffer buffer)
			throws MalformedRequestException {

		CharBuffer cb = gbk.decode(buffer);
		Matcher m = requestPattern.matcher(cb);
		if (!m.matches())
			throw new MalformedRequestException();
		Action a;
		try {
			a = Action.parse(m.group(1));
		} catch (IllegalArgumentException x) {
			throw new MalformedRequestException();
		}
		URI uri;
		try {
			uri = new URI("http://" + m.group(4) + m.group(2));
		} catch (URISyntaxException x) {
			throw new MalformedRequestException();
		}
		return new Request(a, m.group(3), uri);
	}

	static boolean isComplete(ByteBuffer bb) {
		int p = bb.position() - 4;
		if (p < 0)
			return false;
		return (((bb.get(p + 0) == '\r') && (bb.get(p + 1) == '\n')
				&& (bb.get(p + 2) == '\r') && (bb.get(p + 3) == '\n')));
	}
}
