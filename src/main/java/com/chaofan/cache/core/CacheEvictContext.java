package com.chaofan.cache.core;

import com.chaofan.cache.core.api.ICache;
import com.chaofan.cache.core.api.ICacheEvictContext;

/**
 * @author 李超凡
 * @since 2022/5/28 20:59
 */
public class CacheEvictContext<K, V> implements ICacheEvictContext<K, V> {

    /**
     * 新增的key
     */
    private K key;

    /**
     * cache实现
     */
    private ICache<K, V> cache;

    /**
     * 最大缓存数量
     */
    private int size;

    @Override
    public K key() {
        return key;
    }

    @Override
    public ICache<K, V> cache() {
        return cache;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void setKey(K key) {
        this.key = key;
    }

    @Override
    public void setCache(ICache<K, V> cache) {
        this.cache = cache;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }
}
