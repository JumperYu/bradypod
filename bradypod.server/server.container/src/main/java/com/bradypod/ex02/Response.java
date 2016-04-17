package com.bradypod.ex02;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class Response implements HttpServletResponse {

	SocketChannel channel;
	Request request;

	public Response(SocketChannel channel) {
		this.channel = channel;
	}

	public void send() throws IOException {
		// ByteBuffer.wrap("Welcome to my container.".getBytes())
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		FileChannel.open(Paths.get("E://index.txt"), StandardOpenOption.READ)
				.read(buffer);
		buffer.flip();
		channel.write(buffer);
	}

	public void setRequest(Request request) {
		this.request = request;
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

		return null;
	}

	@Override
	public void setCharacterEncoding(String charset) {

	}

	@Override
	public void setContentLength(int len) {

	}

	@Override
	public void setContentType(String type) {

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
		
		return 0;
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
