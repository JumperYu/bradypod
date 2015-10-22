package com.bradypod.shop.item.center.service.impl;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import com.bradypod.shop.item.center.service.HomeService;

public class HomeServiceImpl extends UnicastRemoteObject implements HomeService {

	public HomeServiceImpl() throws RemoteException {
	}

	private static final long serialVersionUID = 1L;

	@Override
	public String getCategory() throws RemoteException {
		return "categories:[1,2,3]";
	}

	public static void main(String[] args) {

		// 启动远程服务
		try {
			HomeServiceImpl homeServiceImpl = new HomeServiceImpl();
			LocateRegistry.createRegistry(2015);
			String url = "rmi://localhost:2015/HomeService";
			Naming.rebind(url, homeServiceImpl);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
	}
}
