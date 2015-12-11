package com.bradypod.shop.item.center.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bradypod.shop.item.center.po.ItemInfo;
import com.bradypod.shop.item.center.service.ItemInfoService;

@Controller
public class JsonController {

	@RequestMapping(value = "/ttwg/trade/pay/unionPayNotify.html")
	@ResponseBody
	public String callback(@RequestBody String body) {
		return body;
	}

	@RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String json(String param, HttpEntity<byte[]> requestEntity) throws UnsupportedEncodingException {
		System.out.println(String.format("收到中文:%s", param));
		HttpHeaders httpHeaders = requestEntity.getHeaders();
		Iterator<Map.Entry<String, List<String>>> it = httpHeaders.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, List<String>> entry = it.next();
			System.out.println(String.format("key:%s,value:%s", entry.getKey(), URLDecoder.decode(entry.getValue().toString(), "UTF-8")));
		}
		
		return "haha";
	}

	@RequestMapping(value = "/list")
	@ResponseBody
	public List<String> list(HttpServletResponse response) {
		List<String> list = new ArrayList<>();
		list.add("1");
		list.add("1");
		list.add("1");
		list.add("1");

		// 写入cookie
		/*
		 * response.addHeader("Set-Cookie", "token=" +
		 * UUID.randomUUID().toString().replaceAll("-", "") +
		 * ";Max-Age=3000000;domain=.ttwg168.com;path=/;");
		 */
		Cookie cookie = new Cookie("token", UUID.randomUUID().toString().replaceAll("-", ""));
		cookie.setMaxAge(-1);
		cookie.setDomain("ttwg168.com");
		cookie.setPath("/");
		response.addCookie(cookie);

		return list;
	}

	@RequestMapping(value = "/list1.shtml")
	public void list1(HttpServletResponse response) throws IOException {
		List<String> list = new ArrayList<>();
		list.add("1");
		list.add("1");
		list.add("1");
		list.add("1");

		// 写入cookie
		/*
		 * response.addHeader("Set-Cookie", "token=" +
		 * UUID.randomUUID().toString().replaceAll("-", "") +
		 * ";Max-Age=3000000;domain=.ttwg168.com;path=/;");
		 */
		Cookie cookie = new Cookie("token", UUID.randomUUID().toString().replaceAll("-", ""));
		cookie.setMaxAge(-1);
		cookie.setDomain("ttwg168.com");
		cookie.setPath("/");
		response.addCookie(cookie);
		response.getWriter().println("<script>history.go(-1);</script>");
		
	}
	
	@RequestMapping(value = "haha.json")
	@ResponseBody
	public String haha(HttpServletRequest request) {
		String token = request.getHeader("token");
		return "your token:" + token;
	}

	@RequestMapping(value = "/queryItemInfo", method = RequestMethod.GET)
	@ResponseBody
	public ItemInfo queryItemInfo() {
		ItemInfo itemInfo = new ItemInfo();
		itemInfo.setId(2L);
		return itemInfoService.get(itemInfo);
	}

	// @Resource
	private ItemInfoService itemInfoService;
}
