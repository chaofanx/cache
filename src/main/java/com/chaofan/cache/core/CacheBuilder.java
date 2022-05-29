package com.chaofan.cache.core;

import com.chaofan.cache.core.api.ICache;
import com.chaofan.cache.core.api.ICacheEvict;
import com.chaofan.cache.support.evict.FIFOEvict;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 李超凡
 * @since 2022/5/28 21:35
 */
public final class CacheBuilder<K, V> {

    private CacheBuilder() {
    }

    private Map<K, V> map = new HashMap<>();

    private int size = 2;

    private ICacheEvict<K, V> evict = new FIFOEvict<>();

    public ICache<K, V> build() {
        return new Cache<>(map, size, evict);
    }

    public static void main(String[] args) {
        ICache<Object, Object> cache = new CacheBuilder<>().build();
        cache.put("1", "1");
        cache.put("2", "2");
        cache.put("3", "3");
        cache.put("4", "4");
        System.out.println(cache.keySet());
    }
}
