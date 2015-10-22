package com.bradypod.shop.item.center.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * RMI + Zookeeper 测试
 *
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015年10月22日 上午11:27:00
 */
public interface HomeService extends Remote {

	public String getCategory() throws RemoteException;

}
