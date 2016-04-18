package com.bradypod.ex03;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import com.bradypod.ex03.connector.http.HttpRequest;
import com.bradypod.ex03.connector.http.HttpResponse;

/**
 * 资源处理器
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年3月23日
 */
public class ResourceProcessor {

	static final String WEB_ROOT = "D://";
	static final int BUFFER_SIZE = 1024;

	public void process(HttpRequest request, HttpResponse response) throws IOException {
		byte[] bytes = new byte[BUFFER_SIZE];
		FileInputStream fis = null;
		OutputStream output = response.getStream();
		try {
			File file = new File(WEB_ROOT, request.getRequestURI());
			fis = new FileInputStream(file);
			int ch = -1;
			while ((ch = fis.read(bytes)) > 0) {
				output.write(bytes, 0, ch);
			}
		} catch (FileNotFoundException e) {
			String errorMessage = "HTTP/1.1 404 File Not Found\r\n" + "Content-Type: text/html\r\n"
					+ "Content-Length: 23\r\n" + "\r\n" + "<h1>File Not Found</h1>";
			output.write(errorMessage.getBytes());
		} finally {
			if (fis != null)
				fis.close();
		}
	}

	public void addHeader() {
		
	}
}
