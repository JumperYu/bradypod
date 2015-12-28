package com.bradypod.util.zk;

public class ZooKeeperLock {

	// private String root = "/lock";
	private String connStr = "localhost:2181";
	private int timeout = 30000;

	private ZKClient zkClient; // 动物管理员

	// private String nodeName; // 节点名称

	/**
	 * 创建锁的根目录位置
	 */
	public ZooKeeperLock() {
		try {
			// 创建客户端
			zkClient = new ZKClient(connStr, timeout);
			zkClient.exists("");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加锁
	 */
	public void lock() {
		tryLock();
	}

	/**
	 * 解锁
	 */
	public void unlock() {
		try {

		} catch (Exception e) {
		}
	}

	public boolean tryLock() {
		boolean locked = false;

		// 创建根节点
		try {
		} catch (Exception e) {
		}

		return locked;
	}
}
