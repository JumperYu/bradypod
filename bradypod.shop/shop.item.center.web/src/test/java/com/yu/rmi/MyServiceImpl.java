package com.yu.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class MyServiceImpl extends UnicastRemoteObject implements MyService {

	protected MyServiceImpl() throws RemoteException {
	}

	private static final long serialVersionUID = 1L;

	@Override
	public String say() throws RemoteException {
		return "hello rmi";
	}

	public static void main(String[] args) throws RemoteException,
			MalformedURLException {
		// 发布服务
		try {
			MyServiceImpl serviceImpl = new MyServiceImpl();
			LocateRegistry.createRegistry(2001);
			Naming.rebind("rmi://192.168.64.1:2001/MyService", serviceImpl);
			System.err.println("Read to start");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

}
