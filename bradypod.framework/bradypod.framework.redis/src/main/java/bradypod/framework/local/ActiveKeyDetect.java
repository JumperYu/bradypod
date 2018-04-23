package bradypod.framework.local;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 热点key排队
 * 
 * @author xiangmin.zxm
 *
 */
public class ActiveKeyDetect {

	/**
	 * 环的当前指针，表示环中当前节点，默认从第0个节点开始
	 */
	private final AtomicInteger RING_POINTER = new AtomicInteger(0);

	/**
	 * 环的长度（节点个数）
	 */
	private final Integer RING_COUNT = 3;

	/**
	 * 是否清理过无用的ring节点信息
	 */
	private boolean isNeedClearBoredRing = false;

	/**
	 * 全局计数器 outer-key:时间片(环) 固定3个环节点：0,1,2 (0,1)-2 (1,2)-0 (2,0)-1
	 * 同时最多有两个环节点处于可用（活动）状态，那第3个节点就处于游离态，游离态的可以清理掉 inner-key:inventoryI	d
	 * inner-value:每个inventoryId 对于的 AtomicInteger
	 * 
	 */
	private Map<Integer, ConcurrentHashMap<String, Counter>> ring = new HashMap<Integer, ConcurrentHashMap<String, Counter>>();

	public ActiveKeyDetect() {
		for (int i = 0; i < RING_COUNT; i++) {
			ConcurrentHashMap<String, Counter> ringNode = new ConcurrentHashMap<String, Counter>();
			ring.put(i, ringNode);
		}
	}

	/**
	 * 获取当前库存记录对应的内存排队计数器
	 * 
	 * @param key
	 * @return
	 */
	public Counter getActiveKeyCounter(String key) {

		Counter currenCounter = new Counter();

		int currentRingIndex = getRingIndex();
		ConcurrentHashMap<String, Counter> currentRingNode = ring.get(currentRingIndex);
		Integer currentPointer = RING_POINTER.get();

		Integer preIndex = getPreRingIndex(currentRingIndex);

		// 获取前面一个ring的节点信息
		ConcurrentHashMap<String, Counter> preRingNode = ring.get(preIndex);

		// 每次进来都需要判断当前应该落在环中那个节点
		if (currentRingIndex != currentPointer) {
			// if表示过了切分环的临界点，需要切换到新的环节点上面去

			Counter preCounter = preRingNode.get(key);

			// 表示前面一个队里里面没有本记录的计数器
			if (preCounter == null) {
				// 返回当前最新的计数器
				preCounter = currentRingNode.putIfAbsent(key, currenCounter);
				if (preCounter != null) {
					// 表示put的时候有重复的
					currenCounter = preCounter;
				}
			} else {
				currenCounter = currentRingNode.putIfAbsent(key, preCounter);
				if (currenCounter == null) {
					// 表示currentRingNode 不存在key=inventoryId 的值，则返回新put进去的值
					currenCounter = preCounter;
				}
			}

			boolean isUpdateRingSucc = RING_POINTER.compareAndSet(currentPointer, currentRingIndex);

			// 如果成功发生了切换，且无用的ring节点信息没有被清理掉，则清理上无用的ring节点信息
			if (isUpdateRingSucc && !isNeedClearBoredRing) {
				isNeedClearBoredRing = true;
			}

		} else {
			Counter cur = currentRingNode.get(key);
			if (cur == null) {
				// 当前ring node里面没有，则先去前一个ring node里面找
				Counter pre = preRingNode.get(key);
				if (pre != null) {
					// 如果找到，则直接把前一个ring node里面的counter对象放置到当前ring node里面去
					currenCounter = currentRingNode.putIfAbsent(key, pre);
					if (currenCounter == null) {
						currenCounter = pre;
					}
				} else {
					// 如果找不到，则new 一个新的放到当前ring node里面去
					Counter curCounter = currentRingNode.putIfAbsent(key, currenCounter);
					if (curCounter != null) {
						currenCounter = curCounter;
					}
				}
			} else {
				// 存在，直接返回对应的Counter对象
				currenCounter = cur;
			}

			// 需要删除
			if (isNeedClearBoredRing) {
				Integer boredRingIndex = getBoredRingIndex(currentPointer);
				ring.get(boredRingIndex).clear();
				isNeedClearBoredRing = false;
			}
		}

		return currenCounter;
	}

	/**
	 * 在获取通行证成功后，不论执行失败或成功，都需要调用这个方法(finally) 计数器减1
	 * 
	 * @param counter
	 */
	public void decrementCounterAfterPass(Counter counter) {
		if (counter != null) {
			counter.getRunningQueue().decrementAndGet();
		}
	}

	private Integer getRingIndex() {
		long currentTime = System.currentTimeMillis();
		long currentMinTime = TimeUnit.MINUTES.convert(currentTime, TimeUnit.MILLISECONDS);
		return (int) (currentMinTime % RING_COUNT);
	}

	/**
	 * 返回当前节点的前一个节点index
	 * 
	 * @param currentPointer
	 * @return
	 */
	private int getPreRingIndex(int currentPointer) {
		if (currentPointer == 0) {
			return RING_COUNT - 1;
		}

		return currentPointer - 1;
	}

	/**
	 * 返回空闲的节点信息，既当前节点后面一个节点，需要被清理掉的无聊节点
	 * 
	 * @param currentPointer
	 * @return
	 */
	private int getBoredRingIndex(int currentPointer) {
		if (currentPointer == RING_COUNT - 1) {
			return 0;
		}

		return currentPointer + 1;
	}

	public class Counter {

		/**
		 * 等待队列 计数器（每个请求进来先incrementAndGet，再判断是否超过最大等待数量，getPass方法介绍后，
		 * 在decrementAndGet）
		 */
		private AtomicInteger threads = new AtomicInteger(0);

		/**
		 * 正在执行队列计数器
		 */
		private AtomicInteger runningQueue = new AtomicInteger(0);

		/**
		 * 正在等待的数量（和waitingQueue区别：waitedQueue表示真正在等待循环的数据）
		 */
		private AtomicInteger waitingdQueue = new AtomicInteger(0);

		public Counter() {

		}

		public AtomicInteger getThreads() {
			return threads;
		}

		public void setThreads(AtomicInteger threads) {
			this.threads = threads;
		}

		public AtomicInteger getRunningQueue() {
			return runningQueue;
		}

		public void setRunningQueue(AtomicInteger runningQueue) {
			this.runningQueue = runningQueue;
		}

		public AtomicInteger getWaitingdQueue() {
			return waitingdQueue;
		}

		public void setWaitingdQueue(AtomicInteger waitingdQueue) {
			this.waitingdQueue = waitingdQueue;
		}

	}
}