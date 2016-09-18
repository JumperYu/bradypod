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

import com.alibaba.fastjson.JSONObject;

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

	private AtomicInteger queryCount = new AtomicInteger(0);

	private AtomicInteger hitCount = new AtomicInteger(0);

	/**
	 * 本地缓存数量计数器
	 */
	private AtomicInteger counter = new AtomicInteger(0);

	private static final Logger logger = LoggerFactory.getLogger(LocalCacheStore.class);

	/**
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
			logger.warn("SimpleMemoryCacheStore removePolicy error", t);
		}
	}

	public void put(Object key, Object value, long cacheTime) {
		if (counter.get() > cacheConfig.getMaxCapacity()) {
			LoggerFactory.getLogger(getClass()).warn("SimpleMemoryCacheStore has {}", counter.get());
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
		if (counter.get() > cacheConfig.getMaxCapacity() * 2) {
			logger.warn("SimpleMemoryCacheStore remove all, count={}", counter.get());
			removePolicy(false);
		}
		CacheElement cacheElement = storeMap.put(key, new CacheElement(value, cacheTime));
		if (cacheElement == null) {
			counter.incrementAndGet();
			logger.warn("SimpleMemoryCacheStore put it, key={},count={}", key, counter.get());
		}
	}

	private void removeLocal(Object key) {
		if (storeMap.remove(key) != null) {
			counter.decrementAndGet();
			logger.warn("SimpleMemoryCacheStore remove it, key={}, count={}", JSONObject.toJSONString(key),
					counter.get());
		}
	}

	public Object get(Object key) {
		Object value = innerGet(key);
		if (cacheConfig.isCounteHitRate()) {
			int queryNum = queryCount.incrementAndGet();
			int hitNum = hitCount.get();
			if (value != null) {
				hitNum = hitCount.incrementAndGet();
			}
			if (queryNum % cacheConfig.getCountNum() == 0) {
				logger.warn("get querycount={},hitCount={}", queryNum, hitNum);
			}
		}
		return value;
	}

	private Object innerGet(Object key) {
		CacheElement cacheElement = storeMap.get(key);
		// 数据已经过去
		if (cacheElement == null) {
			return null;
		}
		long curTime = System.currentTimeMillis();
		if (curTime - cacheElement.getExpiredTime() > 0) {
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
			logger.warn("DiscardPolicy rejectedExecution");
		}
	}
}
