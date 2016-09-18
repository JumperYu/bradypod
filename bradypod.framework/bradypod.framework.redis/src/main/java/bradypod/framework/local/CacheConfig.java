package bradypod.framework.local;

public class CacheConfig {

	private static final CacheConfig instance = new CacheConfig();

	private long cacheTime = 1000;

	private int maxCapacity = 100;

	private int distCodeMappingCapacity = 500;

	/**
	 * 竞争锁 等待超时时间 ms 毫秒
	 */
	private int tryLockTimeout = 500;

	/**
	 * 排队减库存并发执行最大线程数
	 */
	private int maxParallelNum4AID = 30;

	/**
	 * 最小查询排队， 如果等待数量大于此值，put local tair
	 */
	private int minWaitingNum4AID = 30;

	private int putLocalCacheThreadNum = 15;

	private int minMaxHitNum = 5;

	/**
	 * 是否需要进行活跃商品检测
	 */
	private boolean activeInventoryDetect = true;

	private boolean singleNewInventoryDetect = false;

	private boolean multNewInventoryDetect = false;

	private boolean editInventoryDetect = true;

	private int editConcurrentcyNo = 3;

	private boolean counteHitRate = false;

	private int countNum = 500;

	/**
	 * 一个环所占用的时间
	 */
	private int aringSecends = 10;

	/** 初始化标志 */
	public volatile static boolean init = false;

	private boolean openLocalCache = false;

	private int counterNum = 1000;

	public void reloadMemoryCacheConfig(String configInfo) {
	}

	public static CacheConfig getInstance() {
		return instance;
	}

	public long getCacheTime() {
		return cacheTime;
	}

	public void setCacheTime(long cacheTime) {
		this.cacheTime = cacheTime;
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public boolean isActiveInventoryDetect() {
		return activeInventoryDetect;
	}

	public void setActiveInventoryDetect(boolean activeInventoryDetect) {
		this.activeInventoryDetect = activeInventoryDetect;
	}

	public boolean isCounteHitRate() {
		return counteHitRate;
	}

	public void setCounteHitRate(boolean counteHitRate) {
		this.counteHitRate = counteHitRate;
	}

	public int getCountNum() {
		return countNum;
	}

	public void setCountNum(int countNum) {
		this.countNum = countNum;
	}

	public int getMaxParallelNum4AID() {
		return maxParallelNum4AID;
	}

	public void setMaxParallelNum4AID(int maxParallelNum4AID) {
		this.maxParallelNum4AID = maxParallelNum4AID;
	}

	public int getTryLockTimeout() {
		return tryLockTimeout;
	}

	public void setTryLockTimeout(int tryLockTimeout) {
		this.tryLockTimeout = tryLockTimeout;
	}

	public int getMinWaitingNum4AID() {
		return minWaitingNum4AID;
	}

	public void setMinWaitingNum4AID(int minWaitingNum4AID) {
		this.minWaitingNum4AID = minWaitingNum4AID;
	}

	public int getMinMaxHitNum() {
		return minMaxHitNum;
	}

	public void setMinMaxHitNum(int minMaxHitNum) {
		this.minMaxHitNum = minMaxHitNum;
	}

	public int getAringSecends() {
		return aringSecends;
	}

	public void setAringSecends(int aringSecends) {
		this.aringSecends = aringSecends;
	}

	public boolean isSingleNewInventoryDetect() {
		return singleNewInventoryDetect;
	}

	public void setSingleNewInventoryDetect(boolean singleNewInventoryDetect) {
		this.singleNewInventoryDetect = singleNewInventoryDetect;
	}

	public boolean isMultNewInventoryDetect() {
		return multNewInventoryDetect;
	}

	public void setMultNewInventoryDetect(boolean multNewInventoryDetect) {
		this.multNewInventoryDetect = multNewInventoryDetect;
	}

	public boolean isEditInventoryDetect() {
		return editInventoryDetect;
	}

	public void setEditInventoryDetect(boolean editInventoryDetect) {
		this.editInventoryDetect = editInventoryDetect;
	}

	public int getEditConcurrentcyNo() {
		return editConcurrentcyNo;
	}

	public void setEditConcurrentcyNo(int editConcurrentcyNo) {
		this.editConcurrentcyNo = editConcurrentcyNo;
	}

	public int getPutLocalCacheThreadNum() {
		return putLocalCacheThreadNum;
	}

	public void setPutLocalCacheThreadNum(int putLocalCacheThreadNum) {
		this.putLocalCacheThreadNum = putLocalCacheThreadNum;
	}

	public boolean isOpenLocalCache() {
		return this.openLocalCache;
	}

	public void setOpenLocalCache(boolean openLocalCache) {
		this.openLocalCache = openLocalCache;
	}

	public long getCounterNum() {
		return this.counterNum;
	}

	public void setCounterNum(int counterNum) {
		this.counterNum = counterNum;
	}

	public int getDistCodeMappingCapacity() {
		return distCodeMappingCapacity;
	}

	public void setDistCodeMappingCapacity(int distCodeMappingCapacity) {
		this.distCodeMappingCapacity = distCodeMappingCapacity;
	}

}
