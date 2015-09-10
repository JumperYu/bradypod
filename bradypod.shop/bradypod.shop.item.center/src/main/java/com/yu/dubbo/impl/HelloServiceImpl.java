package com.yu.dubbo.impl;

import org.springframework.stereotype.Service;

import com.yu.dubbo.HelloService;

@Service("helloService")
public class HelloServiceImpl implements HelloService {

	@Override
	public String hello(String name) {
		return "hello " + name;
	}

}
