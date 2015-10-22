package com.bradypod.shop.item.center.cmd;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ShopRegistry implements Registry {

	/*
	 * 连接信息
	 */
	private String host = "192.168.1.116";
	private int port = 1991;

	private Registry registry;

	public ShopRegistry() {
	}

	public ShopRegistry(String host, int port) {
		this.host = host;
		this.port = port;
		try {
			registry = LocateRegistry.getRegistry(host, port);
		} catch (RemoteException e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}
	}

	@Override
	public Remote lookup(String name) throws RemoteException, NotBoundException, AccessException {
		return registry.lookup(name);
	}

	@Override
	public void bind(String name, Remote obj) throws RemoteException, AlreadyBoundException,
			AccessException {
		registry.bind(name, obj);
	}

	@Override
	public void unbind(String name) throws RemoteException, NotBoundException, AccessException {
		registry.unbind(name);
	}

	@Override
	public void rebind(String name, Remote obj) throws RemoteException, AccessException {
		registry.rebind(name, obj);
	}

	@Override
	public String[] list() throws RemoteException, AccessException {
		return registry.list();
	}

	/* get/set */
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
