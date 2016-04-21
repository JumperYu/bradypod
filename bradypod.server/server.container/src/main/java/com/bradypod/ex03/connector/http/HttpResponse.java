package com.bradypod.ex03.connector.http;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class HttpResponse implements HttpServletResponse {

	private OutputStream output;

	private HttpRequest request;

	Map<String, ArrayList<String>> headers = new HashMap<>(); // 响应头

	ArrayList<Cookie> cookies = new ArrayList<>();// cookie

	public HttpResponse(OutputStream output) {
		this.output = output;
	}

	public OutputStream getStream() {
		return output;
	}

	public void setRequest(HttpRequest request) {
		this.request = request;
	}

	public void sendHeaders() throws IOException {
		output.write(String.format("HTTP/%s %d %s\r\n", request.getProtocol(), getStatus(), "OK").getBytes());
		synchronized (headers) {
			Iterator<String> names = headers.keySet().iterator();
			while (names.hasNext()) {
				String name = (String) names.next();
				ArrayList<String> values = headers.get(name);
				Iterator<String> items = values.iterator();
				while (items.hasNext()) {
					String value = (String) items.next();
					output.write(name.getBytes());
					output.write(": ".getBytes());
					output.write(value.getBytes());
					output.write("\r\n".getBytes());
				}
			}
		}
		output.write("\r\n".getBytes());// 输出结束符\r\n
	}

	@Override
	public String getCharacterEncoding() {

		return null;
	}

	@Override
	public String getContentType() {

		return null;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {

		return null;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		PrintWriter writer = new PrintWriter(output, true);
		return writer;
	}

	@Override
	public void setCharacterEncoding(String charset) {

	}

	@Override
	public void setContentLength(int len) {

	}

	@Override
	public void setContentType(String type) {
		setHeader("Content-Type", type);
	}

	@Override
	public void setBufferSize(int size) {

	}

	@Override
	public int getBufferSize() {

		return 0;
	}

	@Override
	public void flushBuffer() throws IOException {

	}

	@Override
	public void resetBuffer() {

	}

	@Override
	public boolean isCommitted() {

		return false;
	}

	@Override
	public void reset() {

	}

	@Override
	public void setLocale(Locale loc) {

	}

	@Override
	public Locale getLocale() {

		return null;
	}

	@Override
	public void addCookie(Cookie cookie) {
		synchronized (cookies) {
			cookies.add(cookie);
		}
	}

	@Override
	public boolean containsHeader(String name) {

		return false;
	}

	@Override
	public String encodeURL(String url) {

		return null;
	}

	@Override
	public String encodeRedirectURL(String url) {

		return null;
	}

	@Override
	public String encodeUrl(String url) {

		return null;
	}

	@Override
	public String encodeRedirectUrl(String url) {

		return null;
	}

	@Override
	public void sendError(int sc, String msg) throws IOException {

	}

	@Override
	public void sendError(int sc) throws IOException {

	}

	@Override
	public void sendRedirect(String location) throws IOException {

	}

	@Override
	public void setDateHeader(String name, long date) {

	}

	@Override
	public void addDateHeader(String name, long date) {

	}

	@Override
	public void setHeader(String name, String value) {

	}

	@Override
	public void addHeader(String name, String value) {
		synchronized (headers) {
			ArrayList<String> values = headers.get(name);
			if (values == null) {
				values = new ArrayList<>();
				headers.put(name, values);
			} // --> end if
			values.add(value);// 一个头可以对应多个值
		} // --> end sync
	}

	@Override
	public void setIntHeader(String name, int value) {

	}

	@Override
	public void addIntHeader(String name, int value) {

	}

	@Override
	public void setStatus(int sc) {

	}

	@Override
	public void setStatus(int sc, String sm) {

	}

	@Override
	public int getStatus() {
		// 先写死
		return 200;
	}

	@Override
	public String getHeader(String name) {

		return null;
	}

	@Override
	public Collection<String> getHeaders(String name) {

		return null;
	}

	@Override
	public Collection<String> getHeaderNames() {

		return null;
	}
}
