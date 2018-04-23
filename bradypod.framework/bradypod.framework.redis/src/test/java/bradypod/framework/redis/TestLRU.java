package bradypod.framework.redis;

import bradypod.framework.local.LRUCache;
import org.junit.Test;

public class TestLRU {

    @Test
    public void testLRU() {
        LRUCache<String, String> cache = new LRUCache<>(4);

        cache.put("A", "B");
        cache.put("B", "B");
        cache.put("C", "B");

        System.out.println(cache);

        cache.put("D", "B");
        cache.put("E", "B");

        System.out.println(cache);
    }
}
