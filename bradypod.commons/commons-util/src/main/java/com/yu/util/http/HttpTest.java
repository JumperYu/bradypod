package com.yu.util.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class HttpTest {

	@Test
	public void testPost() {
//		getMsg();
		// reg();
//		 login();
	}

	static void login() {
		String messagePath = "http://user.ttwg168.com/front/user/supplierlogin.html";
//		String messagePath = "http://user.ttwg168.com/front/user/login.html";
		Map<String, String> header = new HashMap<String, String>();
		header.put("client_type", "3");
		Map<String, Object> msgContent = new HashMap<String, Object>();
		msgContent.put("mobileNum", "18666085086");
		msgContent.put("loginPwd", "123123");
		msgContent.put("picVfCode", "2247");
		request(messagePath, "POST", header, JSON.toJSONString(msgContent));
	}

	static void getMsg() {
		// 获取短信
		String messagePath = "http://user.ttwg168.com/front/user/getvfcode.html";
		Map<String, Object> msgContent = new HashMap<String, Object>();
		msgContent.put("mobileNum", "18680547506");
		msgContent.put("type", Integer.valueOf(1));
		msgContent.put("userType", Integer.valueOf(1));
		request(messagePath, "POST", null, JSON.toJSONString(msgContent));
	}

	static void reg() {
		String regPath = "http://user.ttwg168.com/front/user/reg.html";
		Map<String, Object> regContent = new HashMap<String, Object>();
		regContent.put("mobileNum", "18675857854");
		regContent.put("verifyCode", "315960");
		request(regPath, "POST", null, JSON.toJSONString(regContent));
	}
	
	@Test
	public void testHeader() throws UnsupportedEncodingException{
		Map<String, String> headers = new HashMap<>();
		headers.put("xxx", "xxx1");
		headers.put("yyy", "yyy");
		headers.put("aaa", "aaa");
		headers.put("bbb", URLEncoder.encode("我是中文我是中文我是中文我是中文我是中文我是中文我是中文", "UTF-8"));
//		headers.put("bbb", "我是中文我是中文我是中文我是中文我是中文我是中文我是中文");
		request("http://192.168.1.111/json.html?param=中文", "GET", headers, null);
//		request("http://192.168.2.212:3004/front/homeitem/list.html?pageNO=1&pageSize=10", "GET", headers, null);
//		request("http://192.168.1.201:3604/front/homeitem/list.html?pageNO=1&pageSize=10", "GET", headers, null);
	}
	
	@Test
	public void testHttpHeader(){
		
	}
	
	@Test
	public void testAdminLogin(){
		request("http://admin.ttwg168.com", "POST", null, "userName=admin&loginPwd=jonson@ttwg");
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
			conn.setRequestProperty("Content-Type", "text/plain;charset=utf-8");
			conn.setReadTimeout(2000);
			
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
			System.out.format("response code:%d%n", code);
			if (code == HttpURLConnection.HTTP_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						conn.getInputStream(), "UTF-8"));
				String lines = "";
				while ((lines = reader.readLine()) != null) {
					ret += lines;
				}
				reader.close();
				Map<String, List<String>> respHeaders = conn.getHeaderFields();
				for (String key : respHeaders.keySet()) {
					System.out.println(key + ":" + respHeaders.get(key));
				}//--> end for
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
