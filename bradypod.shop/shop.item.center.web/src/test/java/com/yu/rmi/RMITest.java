package com.yu.rmi;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;

public class RMITest {

	public static void main(String[] args) throws IOException, NotBoundException {
		String host = "localhost";
		int port = 1991;
		String name = "MyService";
		MyService myService = (MyService) Naming.lookup(String.format("rmi://%s:%d/%s", host, port, name));
		String response = myService.say();
		// Hello stub = (Hello) registry.lookup("Hello");
		// String response = stub.sayHello();
		System.out.println("response: " + response);
	}

}
