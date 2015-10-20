package com.yu.rmi;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMITest {

	public static void main(String[] args) throws IOException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry(2001);
//		MyService stub = (MyService) registry.lookup("MyService");
		Hello stub = (Hello) registry.lookup("Hello");
		String response = stub.sayHello();
		System.out.println("response: " + response);
	}

}
