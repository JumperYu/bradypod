package com.bradypod.shop.item.center.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bradypod.shop.item.center.po.Dictionary;
import com.yu.util.validate.BeanValidators;

@Controller
public class JsonController {

	@RequestMapping(value = "/json", method = RequestMethod.GET)
	@ResponseBody
	public String json(Dictionary dictionary) {

		BeanValidators.validateWithException(dictionary);

		return "haha";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public List<String> list(){
		List<String> list = new ArrayList<>();
		list.add("1");
		list.add("1");
		list.add("1");
		list.add("1");
		return list;
	}
}
