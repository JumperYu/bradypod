package com.yu.nio;

import java.rmi.RemoteException;

public class BusinessImpl implements Business {

	@Override
	public String hello() throws RemoteException {
		return "hello rmi";
	}

}
