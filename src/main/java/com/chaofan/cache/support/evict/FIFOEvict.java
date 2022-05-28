package com.chaofan.cache.support.evict;

import com.chaofan.cache.core.api.ICache;
import com.chaofan.cache.core.api.ICacheEntry;
import com.chaofan.cache.core.api.ICacheEvictContext;
import com.chaofan.cache.support.model.CacheEntry;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.SynchronousQueue;

/**
 * 先入先出策略
 *
 * @author 李超凡
 * @since 2022/5/28 21:21
 */
public class FIFOEvict<K, V> extends AbstractEvict<K, V> {

    /**
     * 维护先入先出策略
     */
    private final Queue<K> queue = new LinkedList<>();

    @Override
    protected ICacheEntry<K, V> doEvict(ICacheEvictContext<K, V> context) {
        ICacheEntry<K,V> result = null;
        ICache<K, V> cache = context.cache();
        if (cache.size() >= context.size()) {
            K evictKey = queue.remove();
            V evictValue = cache.remove(evictKey);
            result = CacheEntry.of(evictKey, evictValue);
        }
        final K key = context.key();
        queue.add(key);
        return result;
    }

}
