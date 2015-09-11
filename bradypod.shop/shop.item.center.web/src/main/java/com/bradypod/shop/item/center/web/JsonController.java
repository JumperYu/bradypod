package com.bradypod.shop.item.center.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bradypod.shop.item.center.po.Dictionary;
import com.yu.util.validate.BeanValidators;

@Controller
public class JsonController {

	@RequestMapping(value = "/json", method = RequestMethod.POST, headers = "content-type=text/plain;charset=UTF-8")
	@ResponseBody
	public String json(@RequestBody Dictionary dictionary) {

		BeanValidators.validateWithException(dictionary);

		return "json";
	}
}
