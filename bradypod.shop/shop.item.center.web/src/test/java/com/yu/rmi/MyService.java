package com.yu.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MyService extends Remote{
	
	public String say() throws RemoteException;
	
}
