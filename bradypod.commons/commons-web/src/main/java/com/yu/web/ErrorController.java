package com.yu.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yu.common.po.Result;

@Controller
public class ErrorController {

	@RequestMapping(value = "/error", produces = "application/json")
	@ResponseBody
	public Result<String> handler(HttpServletRequest request) {
		Result<String> result = new Result<>();
		result.setCode((int) request
				.getAttribute("javax.servlet.error.status_code"));
		result.setMessage((String) request
				.getAttribute("javax.servlet.error.message"));
		return result;
	}
}
