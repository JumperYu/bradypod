package com.yu.nio;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * RMI 接口
 *
 * @author zengxm
 * @date 2015年9月4日
 *
 */
public interface Business extends Remote {

	public String hello() throws RemoteException;

}
