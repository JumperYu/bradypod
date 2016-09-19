package bradypod.framework.local;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 本地缓存
 * 
 * @author xiangmin.zxm
 *
 */
public class LocalCacheStore {

	private ConcurrentHashMap<Object, CacheElement> storeMap = new ConcurrentHashMap<Object, CacheElement>();

	private CacheConfig cacheConfig = CacheConfig.getInstance();

	private ExecutorService executorService = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>(1), Executors.defaultThreadFactory(), new DiscardPolicy());

	/**
	 * 本地缓存数量计数器
	 */
	private AtomicInteger counter = new AtomicInteger(0);

	private static final Logger logger = LoggerFactory.getLogger(LocalCacheStore.class);

	/**
	 * 清除过期缓存
	 * 
	 * @param dependOnTime
	 */
	private void removePolicy(boolean dependOnTime) {
		try {
			Iterator<Entry<Object, CacheElement>> iterator = storeMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<Object, CacheElement> entry = iterator.next();
				boolean remove = false;
				if (dependOnTime == false) {
					remove = true;
				} else {
					if (dependOnTime && System.currentTimeMillis() - entry.getValue().getExpiredTime() > 0) {
						remove = true;
					}
				}
				if (remove) {
					removeLocal(entry.getKey());
				}
			}
		} catch (Throwable t) {
			logger.warn("local cache store removePolicy error", t);
		}
	}

	/**
	 * 更新缓存
	 * 
	 * @param key
	 *            - 键
	 * @param value
	 *            - 值
	 * @param cacheTime
	 *            - 毫秒
	 */
	public void put(Object key, Object value, long cacheTime) {
		// 如果缓存已经大于最大容量则值执行逻辑清除
		if (counter.get() > cacheConfig.getMaxCapacity()) {
			logger.warn("local cache store is full and size is: " + counter.get());
			try {
				executorService.execute(new Runnable() {
					@Override
					public void run() {
						removePolicy(true);
					}
				});
			} catch (Throwable t) {
				logger.warn("executorService execute error", t);
			}
		}
		// 如果缓存已经大于2倍的容量则执行物理清除
		if (counter.get() > cacheConfig.getMaxCapacity() * 2) {
			logger.warn("local cache store is full and size is:" + counter.get());
			removePolicy(false);
		}
		// 执行put操作
		CacheElement cacheElement = storeMap.put(key, new CacheElement(value, cacheTime));
		if (cacheElement == null) {
			counter.incrementAndGet();
			logger.warn("local cache store put key=" + key + " count=" + counter.get());
		}
	}

	private void removeLocal(Object key) {
		if (storeMap.remove(key) != null) {
			counter.decrementAndGet();
			logger.warn("local cache store remove key=" + key + " count=" + counter.get());
		}
	}

	public Object get(Object key) {
		CacheElement cacheElement = storeMap.get(key);
		// 数据已经过去
		if (cacheElement == null) {
			return null;
		}

		if (System.currentTimeMillis() - cacheElement.getExpiredTime() > 0) {
			removeLocal(key);
			return null;
		}
		return cacheElement.getValue();
	}

	/**
	 * A handler for rejected tasks that silently discards the rejected task.
	 */
	public static class DiscardPolicy implements RejectedExecutionHandler {
		/**
		 * Creates a {@code DiscardPolicy}.
		 */
		public DiscardPolicy() {
		}

		/**
		 * Does nothing, which has the effect of discarding task r.
		 * 
		 * @param r
		 *            the runnable task requested to be executed
		 * @param e
		 *            the executor attempting to execute this task
		 */
		public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
			logger.warn("local cache store DiscardPolicy rejectedExecution");
		}
	}
}
