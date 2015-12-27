package com.bradypod.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 微信回调接口
 *
 * @author zengxm
 * @date 2015年12月27日
 * 
 */
@WebServlet("/customservice/transmit")
public class WeixinCallBack extends HttpServlet {

	public WeixinCallBack() {
		logger.setLevel(Level.INFO);
	}
	
	/**
	 * 微信第一次配置回调地址的时候， 需要提供的接口请求
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		HashMap<String, String[]> params = new HashMap<String, String[]>(
				req.getParameterMap());

		String[] paramStr = params.get("echostr");
		String echo = "";
		if (paramStr != null && paramStr.length > 0) {
			// --> 验证消息有效性
			if (validate(params)) {
				echo = paramStr[0];
				logger.info("validate success " + echo);
			} else {
				echo = "validate error";
				logger.info("validate error");
			}// --> end if-else
		} else {
			echo = "echo string is null";
			logger.info("echo string is null");
		}// --> end if-else

		resp.setContentType("text/plain");
		resp.setCharacterEncoding("utf-8");

		PrintWriter out = resp.getWriter();
		out.write(echo);
		out.flush();
		out.close();
	}

	/**
	 * 微信配置完回调地址后, 均采用POST请求
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String xmlData = readFromBody(req.getInputStream(),
				req.getContentLength());

		logger.info(xmlData);
		
		String echo = "";

		try {
			if (xmlData != null && !xmlData.equals("")) {

				Document document = DocumentHelper.parseText(xmlData)
						.getDocument();

				Document retDoc = DocumentHelper.createDocument();
				Element root = document.getRootElement();
				Element retRoot = retDoc.addElement("xml");

				String FromUserName = root.elementText("FromUserName");
				String ToUserName = root.elementText("ToUserName");

				retRoot.addElement("ToUserName").setText(FromUserName);
				retRoot.addElement("FromUserName").setText(ToUserName);
				retRoot.addElement("CreateTime").setText(
						System.currentTimeMillis() / 1000 + "");
				retRoot.addElement("MsgType").setText(
						"transfer_customer_service");

				echo = retDoc.asXML();
			}
		} catch (DocumentException e) {
			// TODO
			logger.info(e.getMessage());
		}
		
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("application/xml");
		
		PrintWriter out = resp.getWriter();
		out.write(echo);
		out.flush();
		out.close();
	}

	/**
	 * 验证签名
	 * 
	 * @param params
	 *            请求参数
	 * @return <code>true</code>
	 */
	private static boolean validate(Map<String, String[]> params) {

		printMap(params);

		String signature = params.get("signature")[0];
		String timestamp = params.get("timestamp")[0];
		String nonce = params.get("nonce")[0];

		String[] arry = new String[] { timestamp, nonce, TOKEN };
		Arrays.sort(arry);

		StringBuffer sb = new StringBuffer();
		for (String s : arry) {
			sb.append(s);
		}

		logger.info("before validate " + sb.toString());

		String shahex = DigestUtils.sha1Hex(sb.toString());
		if (shahex.equals(signature)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 从post请求内容里面读取数据
	 * 
	 * @param in
	 *            输入
	 * @param size
	 *            输入大小
	 * @return <code>string</code>
	 */
	private static String readFromBody(InputStream in, int size) {
		String xml = "";
		byte[] buffer = new byte[size];
		byte[] xmldataByte = new byte[size];
		int count = 0;
		int rbyte = 0;
		try {
			while (count < size) {
				rbyte = in.read(buffer);

				for (int i = 0; i < rbyte; i++) {
					xmldataByte[count + i] = buffer[i];
				}
				count += rbyte;
			}
			in.close();
			xml = new String(xmldataByte, "utf-8");
		} catch (IOException e) {
			// TODO do something
			logger.info(e.getMessage());
		}
		return xml;
	}

	/**
	 * 打印参数
	 * 
	 * @param params
	 */
	private static void printMap(Map<String, String[]> params) {
		if (params == null || params.size() == 0) {
			logger.info("params is empty");
		}
		Iterator<Entry<String, String[]>> iterator = params.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Entry<String, String[]> entry = iterator.next();
			logger.info(String.format("key:%s values:%s", entry.getKey(),
					printArray(entry.getValue())));
		}
	}
	
	/**
	 * 打印数组
	 * 
	 * @param vals
	 * @return
	 */
	private static String printArray(String[] vals) {
		StringBuffer sb = new StringBuffer("[");
		for (String val : vals) {
			sb.append(val + " ");
		}
		sb.substring(sb.length() - 1, sb.length());
		sb.append("]");
		return sb.toString();
	}

	static final Logger logger = Logger.getGlobal();

	private static final String TOKEN = "ttwg168";

	private static final long serialVersionUID = 1L;

}
