package com.bradypod.framework.config.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.taobao.diamond.utils.StringUtils;

public class HttpUtil {

	public static String request(String path, String method, Map<String, String> headers, String content) {
		String ret = "";
		HttpURLConnection conn = null;
		try {
			URL url = new URL(path);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod(method); // set GET/POST has problem
			conn.setUseCaches(false);
			conn.setInstanceFollowRedirects(true);
			conn.setRequestProperty("Content-Type", "text/plain;charset=utf-8");
			conn.setReadTimeout(DEFAULT_READ_TIMEOUT);
			conn.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT);

			if (headers != null) {
				for (String key : headers.keySet()) {
					conn.setRequestProperty(key, headers.get(key));
				} // -->> end of for
			} // -->> end of if
			conn.connect();
			if (StringUtils.isNotEmpty(content)) {
				DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
				dos.writeBytes(content.toString());
				dos.flush();
				dos.close();
			}
			int code = conn.getResponseCode();
			if (code == HttpURLConnection.HTTP_OK) {
				InputStream input = conn.getInputStream();
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				byte[] buf = new byte[512];
				int len = -1;
				while ((len = input.read(buf)) > 0) {
					output.write(buf, 0, len);
				}
				ret = output.toString();
			} else {
				// TODO
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return ret.toString();
	}

	static final int DEFAULT_READ_TIMEOUT = 30000;

	static final int DEFAULT_CONNECT_TIMEOUT = 0;

	public static void main(String[] args) {
		String result = request("http://120.24.232.118/", "GET", null, null);
		System.out.println(result);
	}
}
