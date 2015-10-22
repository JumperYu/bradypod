package com.yu.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class MyServiceImpl extends UnicastRemoteObject implements MyService {

	private static final long serialVersionUID = 1L;

	protected MyServiceImpl() throws RemoteException {
	}

	@Override
	public String say() throws RemoteException {
		return "hello rmi";
	}

	public static void main(String[] args) throws RemoteException, MalformedURLException {
		// 发布服务
		try {
			MyServiceImpl serviceImpl = new MyServiceImpl();
			String host = "192.168.1.116";
			int port = 1991;
			String name = "MyService";
			LocateRegistry.createRegistry(port);
			Naming.rebind(String.format("rmi://%s:%d/%s", host, port, name), serviceImpl);
			
			System.err.println("Read to start");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

}
