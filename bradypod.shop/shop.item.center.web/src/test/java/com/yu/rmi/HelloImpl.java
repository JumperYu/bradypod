package com.yu.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class HelloImpl implements Hello {

	/*
	 * Constructs a HelloImpl remote object.
	 */
	public HelloImpl() throws RemoteException {
	}

	/*
	 * Returns the string "Hello World!".
	 */
	public String sayHello() throws RemoteException {
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

			LocateRegistry.createRegistry(1024).rebind("Hello",
					UnicastRemoteObject.exportObject(obj, 0, csf, ssf));

			System.out.println("HelloImpl bound in registry");

		} catch (Exception e) {
			System.out.println("HelloImpl exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
