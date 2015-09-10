package com.yu.test.pool;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.Test;

import com.yu.util.thread.ThreadPool;
import com.yu.util.thread.ThreadWorker;

public class TestPool {

	@Test
	public void testGenericKeyPool() throws Exception {
		// 创建一个对象池
		GenericKeyedObjectPool<String, String> pool = new GenericKeyedObjectPool<String, String>(
				new BaseKeyedPooledObjectFactory<String, String>() {

					@Override
					public String create(String key) throws Exception {
						return new String(key + "," + "value");
					}

					@Override
					public PooledObject<String> wrap(String value) {
						return new DefaultPooledObject<String>(value);
					}
				});

		// 添加对象到池，重复的不会重复入池
		pool.addObject("a");
		pool.addObject("a");
		pool.addObject("b");
		pool.addObject("x");

		// 清除最早的对象
		// pool.clearOldest();

		// 获取并输出对象
		System.out.println(pool.borrowObject("a"));
		System.out.println(pool.borrowObject("b"));
		System.out.println(pool.borrowObject("c"));
		System.out.println(pool.borrowObject("c"));
		System.out.println(pool.borrowObject("a"));

		// 输出池状态
		System.out.println(pool.getMaxTotal());
		System.out.println(pool.getMaxIdlePerKey());
	}

	@Test
	public void testGenericPool() throws Exception {
		final GenericObjectPool<String> pool = new GenericObjectPool<String>(
				new BasePooledObjectFactory<String>() {

					@Override
					public String create() throws Exception {
						return new String("" + new Random().nextInt());
					}

					@Override
					public PooledObject<String> wrap(String obj) {
						return new DefaultPooledObject<String>(obj);
					}
				});
		pool.setMaxTotal(20);
		pool.setMaxIdle(20);

		int count = 100000;
		// 手动添加对象
		final CountDownLatch latch = new CountDownLatch(count);
		for (int i = 0; i < count; i++) {
			ExecutorService service = Executors.newFixedThreadPool(6);
			service.execute(new Runnable() {
				@Override
				public void run() {
					String str = "";
					try {
						// TimeUnit.MILLISECONDS.sleep(300);
						System.out.println("active:" + pool.getNumActive()
								+ ";idle:" + pool.getNumIdle() + ";wait:"
								+ pool.getNumWaiters());
						str = pool.borrowObject();
						System.out.println(str);
						// TimeUnit.MICROSECONDS.sleep(1000);
						latch.countDown();
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						pool.returnObject(str);
					}
				}
			});
		}
		latch.await();
		pool.close();
	}

	@Test
	public void testConnection() {

		int threads = 100;
		ThreadPool threadPool = new ThreadPool(threads);
		threadPool.executeThread(new ThreadWorker() {

			@Override
			public void execute() {
				int soTimeout = 2000;
				int connectionTimeout = 3000;
				try (Socket socket = new Socket()) {
					socket.setReuseAddress(true);
					socket.setKeepAlive(true); // Will monitor the TCP
												// connection is
					// valid
					socket.setTcpNoDelay(true); // Socket buffer Whetherclosed,
												// to
					// ensure timely delivery of data
					socket.setSoLinger(true, 0); // Control calls close ()
													// method,
					// the underlying socket is closed
					// immediately
					socket.connect(new InetSocketAddress("redis.bradypod.com",
							6379), connectionTimeout);
					socket.setSoTimeout(soTimeout);

					System.out.println(socket.getKeepAlive());
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
