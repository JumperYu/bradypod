package com.yu.test.zk;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

import com.bradypod.util.zk.ZKClient;

public class TestZkLock {

	@Test
	public void testLock() throws Exception {

		ZKClient zkClient = new ZKClient("localhost:2181", 30000);
		zkClient.createPathIfAbsent("/lock/mv", null, false);
		String data = zkClient.getData("/lock/mv");
		System.out.println(data);
	}

	@Test
	public void testZoo() throws IOException, KeeperException, InterruptedException {
		ZooKeeper zooKeeper = new ZooKeeper("localhost:2181", 30000, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
			}
		});
		zooKeeper.create("/lock/mv", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
				CreateMode.PERSISTENT_SEQUENTIAL);
	}

}
