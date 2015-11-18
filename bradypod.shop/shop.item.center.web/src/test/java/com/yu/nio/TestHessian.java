package com.yu.nio;

import java.net.MalformedURLException;

import com.bradypod.shop.item.center.service.CtgInfoService;
import com.caucho.hessian.client.HessianProxyFactory;

public class TestHessian {

	public static void main(String[] args) throws MalformedURLException, ClassNotFoundException {
		HessianProxyFactory factory = new HessianProxyFactory();
		String url = ("http://localhost/CtgInfoServiceImpl");
		CtgInfoService service = (CtgInfoService) factory.create(CtgInfoService.class, url);

		System.out.println(service.getCtgInfoTree());
	}

}
