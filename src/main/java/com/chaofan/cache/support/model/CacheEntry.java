package com.chaofan.cache.support.model;

import com.chaofan.cache.core.api.ICacheEntry;

/**
 * @author 李超凡
 * @since 2022/5/28 21:31
 */
public class CacheEntry<K, V> implements ICacheEntry<K, V> {
    private final K key;

    private final V value;

    public static <K, V> CacheEntry<K, V> of(K key, V value) {
        return new CacheEntry<>(key, value);
    }
    public CacheEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K key() {
        return key;
    }

    @Override
    public V value() {
        return value;
    }

    @Override
    public String toString() {
        return "CacheEntry{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}
