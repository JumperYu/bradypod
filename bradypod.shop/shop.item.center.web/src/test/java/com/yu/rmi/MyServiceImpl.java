package com.yu.rmi;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class MyServiceImpl implements MyService {

	protected MyServiceImpl() throws RemoteException {
	}

	@Override
	public String say() throws RemoteException {
		return "hello rmi";
	}

	public static void main(String[] args) throws RemoteException,
			MalformedURLException {
		// 发布服务
		try {
			MyServiceImpl serviceImpl = new MyServiceImpl();
			LocateRegistry.createRegistry(1998).bind("MyService",
					UnicastRemoteObject.exportObject(serviceImpl, 0));
			// Naming.rebind("rmi://192.168.64.1:2001/MyService", serviceImpl);
			System.err.println("Read to start");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

}
