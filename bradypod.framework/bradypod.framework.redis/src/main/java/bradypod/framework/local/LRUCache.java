package bradypod.framework.local;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 最简单的LRU实现方式
 *
 * @param <K>
 * @param <V>
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private int cacheSize;

    public LRUCache(int cacheSize) {
        super(16, 0.75f, true);
        this.cacheSize = cacheSize;
    }

    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() >= cacheSize;
    }
}