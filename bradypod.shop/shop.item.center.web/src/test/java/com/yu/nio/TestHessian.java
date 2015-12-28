package com.yu.nio;

import java.net.MalformedURLException;

import com.bradypod.shop.item.center.po.ItemInfo;
import com.bradypod.shop.item.center.service.CtgInfoService;
import com.bradypod.shop.item.center.service.ItemInfoService;
import com.caucho.hessian.client.HessianProxyFactory;

public class TestHessian {

	public static void main(String[] args) throws MalformedURLException, ClassNotFoundException {
		HessianProxyFactory factory = new HessianProxyFactory();
		String url = ("http://localhost/ItemInfoServiceImpl");
		ItemInfoService service = (ItemInfoService) factory.create(CtgInfoService.class, url);
		ItemInfo itemInfo = new ItemInfo();
		itemInfo.setId(2L);
		System.out.println(service.get(itemInfo));
	}

}
