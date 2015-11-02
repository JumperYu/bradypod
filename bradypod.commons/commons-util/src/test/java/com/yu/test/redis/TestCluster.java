package com.yu.test.redis;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCluster.Reset;

public class TestCluster {

	private static Jedis node1;
	private static Jedis node2;
	private static Jedis node3;
	private String localHost = "127.0.0.1";

	private HostAndPort nodeInfo1 = new HostAndPort("101.200.196.51", 30001);
	private HostAndPort nodeInfo2 = new HostAndPort("101.200.196.51", 30002);
	private HostAndPort nodeInfo3 = new HostAndPort("101.200.196.51", 30003);

	private JedisCluster jedisCluster;

	// @Before
	public void init() throws InterruptedException {
		node1 = new Jedis(nodeInfo1.getHost(), nodeInfo1.getPort());
		node1.connect();
		node1.flushAll();

		node2 = new Jedis(nodeInfo2.getHost(), nodeInfo2.getPort());
		node2.connect();
		node2.flushAll();

		node3 = new Jedis(nodeInfo3.getHost(), nodeInfo3.getPort());
		node3.connect();
		node3.flushAll();

		// ---- configure cluster

		// add nodes to cluster
		node1.clusterMeet(localHost, nodeInfo2.getPort());
		node1.clusterMeet(localHost, nodeInfo3.getPort());

		// split available slots across the three nodes
		int slotsPerNode = JedisCluster.HASHSLOTS / 3;
		int[] node1Slots = new int[slotsPerNode];
		int[] node2Slots = new int[slotsPerNode + 1];
		int[] node3Slots = new int[slotsPerNode];
		for (int i = 0, slot1 = 0, slot2 = 0, slot3 = 0; i < JedisCluster.HASHSLOTS; i++) {
			if (i < slotsPerNode) {
				node1Slots[slot1++] = i;
			} else if (i > slotsPerNode * 2) {
				node3Slots[slot3++] = i;
			} else {
				node2Slots[slot2++] = i;
			}
		}

		node1.clusterAddSlots(node1Slots);
		node2.clusterAddSlots(node2Slots);
		node3.clusterAddSlots(node3Slots);

		waitForClusterReady(node1, node2, node3);
	}

	@Before
	public void init2() {
		Set<HostAndPort> clusters = new HashSet<>();
		clusters.add(nodeInfo1);
		clusters.add(nodeInfo2);
		clusters.add(nodeInfo3);
		jedisCluster = new JedisCluster(clusters);
	}

	@Test
	public void testIt() {
		while (true) {
			System.out.println(jedisCluster.set("test-2", "haha"));
			try {
				TimeUnit.MILLISECONDS.sleep(300L);
			} catch (InterruptedException e) {
				
			}
		}
	}

	@AfterClass
	public static void cleanUp() {
		node1.flushDB();
		node2.flushDB();
		node3.flushDB();
		node1.clusterReset(Reset.SOFT);
		node2.clusterReset(Reset.SOFT);
		node3.clusterReset(Reset.SOFT);
	}

	@After
	public void tearDown() throws InterruptedException {
		cleanUp();
	}

	public static void waitForClusterReady(Jedis... nodes) throws InterruptedException {
		boolean clusterOk = false;
		while (!clusterOk) {
			boolean isOk = true;
			for (Jedis node : nodes) {
				if (!node.clusterInfo().split("\n")[0].contains("ok")) {
					isOk = false;
					break;
				}
			}

			if (isOk) {
				clusterOk = true;
			}

			Thread.sleep(50);
		}
	}

}
