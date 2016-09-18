package bradypod.framework.redis;

import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;

import com.bradypod.util.thread.ThreadPool;
import com.bradypod.util.thread.ThreadWorker;

import bradypod.framework.local.LocalCacheStore;

public class TestLocalCache {

	LocalCacheStore localCacheStore = new LocalCacheStore();

	final ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

	/**
	 * 时间有问题
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testPut() throws InterruptedException {
		localCacheStore.put("test", "test", 200);
	}

	@Test
	public void testMap() {
		System.out.println(map.put("A", "B")); // 返回之前这个key的value, null
		System.out.println(map.put("A", "C")); // 返回之前这个key的value, B
		System.out.println(map.putIfAbsent("A", "D")); // 如果已经存在则不会重复, 返回的为之前的值
	}

	@Test
	public void testTime() {
		System.out.println(System.currentTimeMillis());
		System.out.println(System.currentTimeMillis());
		System.out.println(System.currentTimeMillis());
	}

}
