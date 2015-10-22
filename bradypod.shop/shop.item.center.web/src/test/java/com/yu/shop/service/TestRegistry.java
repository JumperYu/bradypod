package com.yu.shop.service;

import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class TestRegistry {
	
	public static void main(String[] args) throws AccessException, RemoteException {
		
		for(String l : LocateRegistry.getRegistry(1991).list()){
			System.out.println(l);
		}
		
	}
	
}
