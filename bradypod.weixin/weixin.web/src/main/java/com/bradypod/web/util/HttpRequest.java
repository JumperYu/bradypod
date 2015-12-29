package com.bradypod.web.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * http请求
 *
 * @author zengxm
 * @date 2015年12月27日
 *
 */
public class HttpRequest {

	/**
	 * http请求
	 * 
	 * @param path
	 *            url
	 * @param method
	 *            GET/POST
	 * @param headers
	 *            null or map
	 * @param content
	 *            null or string
	 */
	public static String request(String path, String method,
			Map<String, String> headers, String content) {
		String ret = "";
		HttpURLConnection conn = null;
		try {
			URL url = new URL(path);
			if (path.startsWith("https")) {// -->> Begin https init
				// ignoreSSL();
			}// -->> End of https init
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod(method); // set GET/POST has problem
			conn.setUseCaches(false);
			conn.setInstanceFollowRedirects(false);
			conn.setReadTimeout(2000);

			if (headers != null) {
				for (String key : headers.keySet()) {
					conn.setRequestProperty(key, headers.get(key));
				}// -->> end of for
			}// -->> end of if
			conn.connect();
			if (content != null && method.equals("POST")) {
				OutputStreamWriter out = new OutputStreamWriter(
						conn.getOutputStream(), "UTF-8"); // utf-8编码
				out.append(content);
				out.flush();
				out.close();
			}
			int code = conn.getResponseCode();
			System.out.format("response code:%d%n", code);
			if (code == HttpURLConnection.HTTP_OK) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(conn.getInputStream(), "UTF-8"));
				String lines = "";
				while ((lines = reader.readLine()) != null) {
					ret += lines;
				}
				reader.close();
				Map<String, List<String>> respHeaders = conn.getHeaderFields();
				for (String key : respHeaders.keySet()) {
					System.out.println(key + ":" + respHeaders.get(key));
				}// --> end for
			} else {
				System.out.println("request error");
			}
		} catch (Exception e) {
			ret = "";
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		System.out.println("request result=" + ret);

		return ret;
	}

}
