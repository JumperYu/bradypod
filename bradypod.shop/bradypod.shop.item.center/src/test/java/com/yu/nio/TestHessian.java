package com.yu.nio;

import java.net.MalformedURLException;

import com.caucho.hessian.client.HessianProxyFactory;

public class TestHessian {

	public static void main(String[] args) throws MalformedURLException, ClassNotFoundException {
		HessianProxyFactory factory = new HessianProxyFactory();
		String url = ("http://localhost/hessianService");
		HessianService service = (HessianService) factory.create(
				HessianService.class, url);
		MyEntity entity = new MyEntity();
		entity.setId(2);
		entity.setName("wyl");
		service.insert(entity);

		System.out.println(service.get(2).getName());
	}

}
