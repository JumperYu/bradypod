package com.yu.nio;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient {

	public static void main(String[] args) throws RemoteException,
			NotBoundException {
		Registry registry = LocateRegistry.getRegistry(1199);
		String name = "ItemInfoService";// "RMIDemo";
		// ItemInfoService business = (ItemInfoService) registry.lookup(name);
		System.out.println( registry.lookup(name).getClass());
	}

}
