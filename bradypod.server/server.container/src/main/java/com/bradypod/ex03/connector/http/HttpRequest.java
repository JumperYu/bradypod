package com.bradypod.ex03.connector.http;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
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

public class HttpRequest implements HttpServletRequest {

	InputStream input;

	String uri; // 请求路径

	String method; // 请求方法: GET/POST/PUT/DELETE

	String protocol; // 协议

	String status; // 状态

	String queryString; // 查询内容

	Map<String, List<String>> headers = new HashMap<>(); // 请求头

	public HttpRequest(InputStream input) {
		this.input = input;
	}

	public String getRequestURI() {
		return uri;
	}

	/**
	 * 解析
	 */
	public void parse() throws IOException {
		// 读取头信息
		byte[] buffer = new byte[1024];
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int read = -1;
		while ((read = input.read(buffer)) > 0) {
			// 写入字节数组
			out.write(buffer, 0, read);
			// 判断是否读完, 很重要
			if (isComplete(buffer, read)) {
				break;
			}
		} // --> end while
		String headContent = out.toString("UTF-8");

		// System.out.println(headContent);

		// 正则匹配
		Matcher m = requestPattern.matcher(headContent);
		if (!m.matches())
			return;

		// 设置URI
		this.method = m.group(1);
		this.uri = m.group(2);
		this.protocol = m.group(3);

		// 关闭输入输出流
		out.close();
	}

	/**
	 * 判断是否取完了所有头信息
	 * 
	 * @param buffer
	 *            - 字节内容
	 * @param read
	 *            - 已存入的字节长度
	 * @return boolean
	 */
	static boolean isComplete(byte[] buffer, int read) {
		if (buffer == null || read < 4) {
			return false;
		}
		return buffer[read - 4] == '\r' && buffer[read - 3] == '\n' && buffer[read - 2] == '\r'
				&& buffer[read - 1] == '\n';
	}

	static Pattern requestPattern = Pattern.compile(
			"\\A([A-Z]+) +([^ ]+) +HTTP/([0-9\\.]+)$" + ".*^Host: ([^ ]+)$.*\r\n\r\n\\z",
			Pattern.MULTILINE | Pattern.DOTALL);

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
	public void setCharacterEncoding(String env) throws UnsupportedEncodingException {

	}

	@Override
	public int getContentLength() {
		// TODO 先写死
		return 1024;
	}

	@Override
	public String getContentType() {
		// TODO 先写死
		return "text/html";
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

		return this.protocol;
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
	public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse)
			throws IllegalStateException {

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

		return queryString;
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
	public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {

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
