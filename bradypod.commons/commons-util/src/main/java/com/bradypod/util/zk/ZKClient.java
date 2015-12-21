package com.bradypod.util.zk;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZKClient implements Watcher {

	private ZooKeeper zookeeper;

	private CountDownLatch connectedSemaphore = new CountDownLatch(1);

	public ZKClient(String connectString, int sessionTimeout) throws Exception {
		zookeeper = new ZooKeeper(connectString, sessionTimeout, this);
		System.out.println("connecting zk server");
		if (connectedSemaphore.await(10l, TimeUnit.SECONDS)) {
			System.out.println("connect zk server success");
		} else {
			System.out.println("connect zk server error.");
			throw new Exception("connect zk server error.");
		}
	}

	public void close() throws InterruptedException {
		if (zookeeper != null) {
			zookeeper.close();
		}
	}

	/**
	 * 创建如果不存在
	 * 
	 * @param path
	 * @param isPersistent
	 * @throws Exception
	 */
	public void createPathIfAbsent(String path, byte[] data, boolean isPersistent) throws Exception {
		CreateMode createMode = isPersistent ? CreateMode.PERSISTENT : CreateMode.EPHEMERAL;
		path = ZKUtil.normalize(path);
		// 如果不存在
		if (!this.exists(path)) {
			zookeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, createMode);
		}
	}

	/**
	 * 是否存在
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public boolean exists(String path) throws Exception {
		path = ZKUtil.normalize(path);
		Stat stat = zookeeper.exists(path, null);
		return stat != null;
	}

	/**
	 * 返回节点的内容
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public String getData(String path) throws Exception {
		path = ZKUtil.normalize(path);
		try {
			byte[] data = zookeeper.getData(path, null, null);
			return new String(data);
		} catch (KeeperException e) {
			if (e instanceof KeeperException.NoNodeException) {
				throw new Exception("Node does not exist,path is [" + e.getPath() + "].", e);
			} else {
				throw new Exception(e);
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new Exception(e);
		}
	}

	@Override
	public void process(WatchedEvent event) {
		if (event == null)
			return;

		// 连接状态
		Watcher.Event.KeeperState keeperState = event.getState();
		// 事件类型
		Watcher.Event.EventType eventType = event.getType();
		// 受影响的path
		// String path = event.getPath();
		if (Watcher.Event.KeeperState.SyncConnected == keeperState) {
			// 成功连接上ZK服务器
			if (Watcher.Event.EventType.None == eventType) {
				System.out.println("zookeeper connect success");
				connectedSemaphore.countDown();
			}
		}
		// 下面可以做一些重连的工作.
		else if (Watcher.Event.KeeperState.Disconnected == keeperState) {
			System.out.println("zookeeper Disconnected");
		} else if (Watcher.Event.KeeperState.AuthFailed == keeperState) {
			System.out.println("zookeeper AuthFailed");
		} else if (Watcher.Event.KeeperState.Expired == keeperState) {
			System.out.println("zookeeper Expired");
		}
	}
}
