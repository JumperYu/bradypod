package com.bradypod.shop.item.center.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bradypod.shop.item.center.po.Dictionary;
import com.bradypod.shop.item.center.po.ItemInfo;
import com.bradypod.shop.item.center.service.ItemInfoService;
import com.yu.util.validate.BeanValidators;

@Controller
public class JsonController {

	@RequestMapping(value = "/ttwg/trade/pay/unionPayNotify.html")
	@ResponseBody
	public String callback(@RequestBody String body) {
		return body;
	}

	@RequestMapping(value = "/json", method = RequestMethod.GET)
	@ResponseBody
	public String json(Dictionary dictionary) {

		BeanValidators.validateWithException(dictionary);

		return "haha";
	}

	@RequestMapping(value = "/list.shtml")
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
