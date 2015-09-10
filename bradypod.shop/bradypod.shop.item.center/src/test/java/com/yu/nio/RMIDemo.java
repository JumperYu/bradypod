package com.yu.nio;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIDemo {

	public static void main(String[] args) throws RemoteException {
		int port = 9527;
		Business business = new BusinessImpl();
		UnicastRemoteObject.exportObject(business, port);
		Registry registry = LocateRegistry.createRegistry(1099);
		registry.rebind("RMIDemo", business);
	}
}
