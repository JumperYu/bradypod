package com.bradypod.blog.admin.web.controller;

import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bradypod.admin.authority.retention.Menu;
import com.bradypod.admin.authority.retention.MenuResource;
import com.bradypod.framework.csv.SimpleCSVWriter;
import com.bradypod.framework.csv.WriterCallback;

/**
 * 首页配置
 *
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2015年12月29日 上午11:32:46
 */
@Controller
@Menu(name = "首页")
public class IndexController {

	@RequestMapping(value = "index")
	public String index() {
		return "index";
	}

	@RequestMapping(value = "index2")
	@MenuResource("页面2")
	public String index2() {
		return "index2";
	}

	@RequestMapping(value = "index3")
	@MenuResource("页面3")
	public String index3() {
		return "index2";
	}

	@RequestMapping(value = "menu")
	@MenuResource("菜单")
	public String menu() {
		return "menu";
	}

	@RequestMapping(value = "download")
	public void download(HttpServletRequest request, HttpServletResponse resp) throws Exception {

		Map<String, Object> headers = getHeaderWihtHttpRequest(request);

		// TODO 需要改成其他名字
		String fileName = "trade.csv";

		ServletOutputStream out = resp.getOutputStream();

		// set download file name
		if (headers.get("user-agent").toString().contains("MSIE")) {
			// IE
			fileName = URLEncoder.encode(fileName, "utf-8");
		} else {
			// 火狐谷歌
			fileName = new String(fileName.getBytes(), "iso-8859-1");
		}

		resp.setContentType("text/csv");
		resp.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

		SimpleCSVWriter.writeBuffer(out, new WriterCallback() {

			@Override
			public void withWriter(SimpleCSVWriter writer) throws Exception {
				writer.append(new Object[]{"123", "123", "123"});
				writer.append(new Object[]{"123", "123", "123"});
				writer.append(new Object[]{"123", "123", "123"});
				writer.append(new Object[]{"123", "123", "123"});
				writer.append(new Object[]{"123", "123", "123"});
				writer.append(new Object[]{"123", "123", "123"});
			}
		});
	}
	
	/**
	 * 打印请求头信息并转成Map返回
	 * 
	 * @param request
	 */
	public static Map<String, Object> getHeaderWihtHttpRequest(
			HttpServletRequest request) {
		Enumeration<String> headers = request.getHeaderNames();
		Map<String, Object> headerMap = new HashMap<String, Object>();
		while (headers.hasMoreElements()) {
			String key = headers.nextElement();
			headerMap.put(key, request.getHeader(key));
		}
		return headerMap;
	}

	@RequestMapping(value = "login")
	public String login() {
		return "login";
	}
}
