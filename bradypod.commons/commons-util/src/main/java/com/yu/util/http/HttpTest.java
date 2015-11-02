package com.yu.util.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class HttpTest {

	@Test
	public void testPost() {
//		getMsg();
		// reg();
		login();
	}

	private void login() {
		String messagePath = "http://user.ttwg168.com/front/user/supplierlogin.html";
		Map<String, String> header = new HashMap<String, String>();
		header.put("client_type", "1");
		Map<String, Object> msgContent = new HashMap<String, Object>();
		msgContent.put("mobileNum", "18675857854");
		msgContent.put("loginPwd", "123456");
		request(messagePath, "POST", header, JSON.toJSONString(msgContent));
	}

	static void getMsg() {
		// 获取短信
		String messagePath = "http://user.ttwg168.com/front/user/getvfcode.html";
		Map<String, Object> msgContent = new HashMap<String, Object>();
		msgContent.put("mobileNum", "18675857854");
		msgContent.put("type", Integer.valueOf(2));
		msgContent.put("userType", Integer.valueOf(2));
		request(messagePath, "POST", null, JSON.toJSONString(msgContent));
	}

	static void reg() {
		String regPath = "http://user.ttwg168.com/front/user/reg.html";
		Map<String, Object> regContent = new HashMap<String, Object>();
		regContent.put("mobileNum", "18675857854");
		regContent.put("verifyCode", "315960");
		request(regPath, "POST", null, JSON.toJSONString(regContent));
	}

	static void request(String path, String method, Map<String, String> headers, String content) {
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
			conn.setInstanceFollowRedirects(true);
			conn.setRequestProperty("Content-Type", "text/plain;charset=utf-8"); // -->
																					// default
																					// header
			if (headers != null) {
				for (String key : headers.keySet()) {
					conn.setRequestProperty(key, headers.get(key));
				}// -->> end of for
			}// -->> end of if
			conn.connect();
			if (content != null) {
				DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
				// 要传的参数
				dos.writeBytes(content.toString());
				dos.flush();
				dos.close();
			}
			int code = conn.getResponseCode();
			if (code == HttpURLConnection.HTTP_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						conn.getInputStream(), "UTF-8"));
				String lines = "";
				while ((lines = reader.readLine()) != null) {
					ret += lines;
				}
				reader.close();
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
	}
}
