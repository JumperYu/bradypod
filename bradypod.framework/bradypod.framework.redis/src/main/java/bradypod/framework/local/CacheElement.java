package bradypod.framework.local;

public class CacheElement {

	public CacheElement(Object value, long cacheTime) {
		this.value = value;
		this.expiredTime = System.currentTimeMillis() + cacheTime;
	}

	public long getExpiredTime() {
		return expiredTime;
	}

	private Object value;

	private long expiredTime;

	public Object getValue() {
		return value;
	}
}
