package com.bradypod.ex02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

public class Request implements HttpServletRequest {

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
	public Object getAttribute(String name) {
		return null;
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		return null;
	}

	@Override
	public String getCharacterEncoding() {
		return null;
	}

	@Override
	public void setCharacterEncoding(String env)
			throws UnsupportedEncodingException {

	}

	@Override
	public int getContentLength() {

		return 0;
	}

	@Override
	public String getContentType() {

		return null;
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {

		return null;
	}

	@Override
	public String getParameter(String name) {

		return null;
	}

	@Override
	public Enumeration<String> getParameterNames() {

		return null;
	}

	@Override
	public String[] getParameterValues(String name) {

		return null;
	}

	@Override
	public Map<String, String[]> getParameterMap() {

		return null;
	}

	@Override
	public String getProtocol() {

		return null;
	}

	@Override
	public String getScheme() {

		return null;
	}

	@Override
	public String getServerName() {

		return null;
	}

	@Override
	public int getServerPort() {

		return 0;
	}

	@Override
	public BufferedReader getReader() throws IOException {

		return null;
	}

	@Override
	public String getRemoteAddr() {

		return null;
	}

	@Override
	public String getRemoteHost() {

		return null;
	}

	@Override
	public void setAttribute(String name, Object o) {

	}

	@Override
	public void removeAttribute(String name) {

	}

	@Override
	public Locale getLocale() {

		return null;
	}

	@Override
	public Enumeration<Locale> getLocales() {

		return null;
	}

	@Override
	public boolean isSecure() {

		return false;
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String path) {

		return null;
	}

	@Override
	public String getRealPath(String path) {

		return null;
	}

	@Override
	public int getRemotePort() {

		return 0;
	}

	@Override
	public String getLocalName() {

		return null;
	}

	@Override
	public String getLocalAddr() {

		return null;
	}

	@Override
	public int getLocalPort() {

		return 0;
	}

	@Override
	public ServletContext getServletContext() {

		return null;
	}

	@Override
	public AsyncContext startAsync() throws IllegalStateException {

		return null;
	}

	@Override
	public AsyncContext startAsync(ServletRequest servletRequest,
			ServletResponse servletResponse) throws IllegalStateException {

		return null;
	}

	@Override
	public boolean isAsyncStarted() {

		return false;
	}

	@Override
	public boolean isAsyncSupported() {

		return false;
	}

	@Override
	public AsyncContext getAsyncContext() {

		return null;
	}

	@Override
	public DispatcherType getDispatcherType() {

		return null;
	}

	@Override
	public String getAuthType() {
		
		return null;
	}

	@Override
	public Cookie[] getCookies() {
		
		return null;
	}

	@Override
	public long getDateHeader(String name) {
		
		return 0;
	}

	@Override
	public String getHeader(String name) {
		
		return null;
	}

	@Override
	public Enumeration<String> getHeaders(String name) {
		
		return null;
	}

	@Override
	public Enumeration<String> getHeaderNames() {
		
		return null;
	}

	@Override
	public int getIntHeader(String name) {
		
		return 0;
	}

	@Override
	public String getMethod() {
		
		return null;
	}

	@Override
	public String getPathInfo() {
		
		return null;
	}

	@Override
	public String getPathTranslated() {
		
		return null;
	}

	@Override
	public String getContextPath() {
		
		return null;
	}

	@Override
	public String getQueryString() {
		
		return null;
	}

	@Override
	public String getRemoteUser() {
		
		return null;
	}

	@Override
	public boolean isUserInRole(String role) {
		
		return false;
	}

	@Override
	public Principal getUserPrincipal() {
		
		return null;
	}

	@Override
	public String getRequestedSessionId() {
		
		return null;
	}

	@Override
	public String getRequestURI() {
		
		return null;
	}

	@Override
	public StringBuffer getRequestURL() {
		
		return null;
	}

	@Override
	public String getServletPath() {
		
		return null;
	}

	@Override
	public HttpSession getSession(boolean create) {
		
		return null;
	}

	@Override
	public HttpSession getSession() {
		
		return null;
	}

	@Override
	public boolean isRequestedSessionIdValid() {
		
		return false;
	}

	@Override
	public boolean isRequestedSessionIdFromCookie() {
		
		return false;
	}

	@Override
	public boolean isRequestedSessionIdFromURL() {
		
		return false;
	}

	@Override
	public boolean isRequestedSessionIdFromUrl() {
		
		return false;
	}

	@Override
	public boolean authenticate(HttpServletResponse response)
			throws IOException, ServletException {
		
		return false;
	}

	@Override
	public void login(String username, String password) throws ServletException {
		
		
	}

	@Override
	public void logout() throws ServletException {
		
		
	}

	@Override
	public Collection<Part> getParts() throws IOException, ServletException {
		
		return null;
	}

	@Override
	public Part getPart(String name) throws IOException, ServletException {
		
		return null;
	}

}
