package com.yu.test.concurrent;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.time.StopWatch;

import com.bradypod.util.thread.ThreadPool;
import com.bradypod.util.thread.ThreadWorker;

/**
 * HashMap是线程不安全的，在多线程的情况下， HashMap容易造成cpu过高 <br>
 * HashTable是线程安全的， 但是其内部是使用synchronized实现， 效率太慢 <br>
 * segments最好事2的N次方，最大是65535
 *
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015年11月7日 下午1:43:15
 */
public class TestHash {

	public static void main(String[] args) throws InterruptedException {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
//		new TestHash().testHashMap();
//		new TestHash().testHashTable();
		new TestHash().testConcurrentHashMap();
		stopWatch.stop();
		System.out.println(THREADS + " threads take times:" + stopWatch.getTime());
	}

	final HashMap<String, String> hashMap = new HashMap<>(2);
	final Hashtable<String, String> hashTable = new Hashtable<>(8192); 
	final ConcurrentHashMap<String, String> concurrentMap = new ConcurrentHashMap<>(8096, 0.75f);

	static final int THREADS = 10000;
	static ThreadPool pool = new ThreadPool(THREADS);

	// 发起N个线程, 进行hashmap的插入
	// 上W并发是不可能扛得住的
	public void testHashMap() throws InterruptedException {

		pool.executeThread(new ThreadWorker() {

			@Override
			public void execute() {
				hashMap.put(UUID.randomUUID().toString(), UUID.randomUUID().toString());
			}
		});

	}

	// 发起N个线程, 进行hashtable的插入
	public void testHashTable() throws InterruptedException {
		pool.executeThread(new ThreadWorker() {

			@Override
			public void execute() {
				hashTable.put(UUID.randomUUID().toString(), UUID.randomUUID().toString());
			}
		});

	}

	// 发起N个线程, 进行hashtable的插入
	public void testConcurrentHashMap() throws InterruptedException {
		pool.executeThread(new ThreadWorker() {

			@Override
			public void execute() {
				concurrentMap.put(UUID.randomUUID().toString(), UUID.randomUUID().toString());
			}
		});

	}
}
