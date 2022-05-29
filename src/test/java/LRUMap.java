import java.io.Serial;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 李超凡
 * @since 2022/5/28 22:38
 */
public class LRUMap<K, V> extends LinkedHashMap<K, V> {
    @Serial
    private static final long serialVersionUID = -5887757744440464326L;
    private final int capacity;

    public LRUMap(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }

    @Override
    public V get(Object key) {
        return super.get(key);
    }

    @Override
    public V put(K key, V value) {
        return super.put(key, value);
    }
}
