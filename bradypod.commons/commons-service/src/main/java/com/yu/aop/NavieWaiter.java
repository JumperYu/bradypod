package com.yu.aop;

import org.springframework.stereotype.Service;

@Service
public class NavieWaiter implements Waiter {

	@Override
	@RedisCache(key = "name", expire = 120)
	public String greet(String name) {
		System.out.println("execute");
		return "Hello" + name;
	}

	@Override
	public void serverTo(String name) {
		System.out.println(String.format("server to %s", name));
	}

}
