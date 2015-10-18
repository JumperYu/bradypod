package com.yu.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class HelloImpl implements Hello {

	/*
	 * Constructs a HelloImpl remote object.
	 */
	public HelloImpl() {
	}

	/*
	 * Returns the string "Hello World!".
	 */
	public String sayHello() {
		return "Hello World!";
	}

	public static void main(String args[]) {

		/*
		 * Create and install a security manager
		 */
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		byte pattern = (byte) 0xAC;
		try {
			/*
			 * Create remote object and export it to use custom socket
			 * factories.
			 */
			HelloImpl obj = new HelloImpl();
			RMIClientSocketFactory csf = new XorClientSocketFactory(pattern);
			RMIServerSocketFactory ssf = new XorServerSocketFactory(pattern);
			Hello stub = (Hello) UnicastRemoteObject.exportObject(obj, 0, csf,
					ssf);

			/*
			 * Create a registry and bind stub in registry.
			 */
			LocateRegistry.createRegistry(2001);
			Registry registry = LocateRegistry.getRegistry(2001);
			registry.rebind("Hello", stub);
			System.out.println("HelloImpl bound in registry");

		} catch (Exception e) {
			System.out.println("HelloImpl exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
