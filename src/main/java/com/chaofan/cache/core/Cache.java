package com.chaofan.cache.core;

import com.chaofan.cache.core.api.ICache;
import com.chaofan.cache.core.api.ICacheEvict;
import com.chaofan.cache.core.api.ICacheEvictContext;
import com.chaofan.cache.core.api.ICacheExpire;
import com.chaofan.cache.exception.CacheRuntimeException;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @author 李超凡
 * @since 2022/5/28 20:45
 */
public class Cache<K, V> implements ICache<K, V> {

    /**
     * 缓存容器
     */
    private final Map<K,V> map;

    /**
     * 缓存大小限制
     */
    private final int sizeLimit;

    /**
     * 驱逐策略
     */
    private final ICacheEvict<K,V> evict;

    /**
     * 过期策略
     */
    private ICacheExpire<K,V> expire;

    public Cache(Map<K, V> map, int sizeLimit, ICacheEvict<K, V> evict) {
        this.map = map;
        this.sizeLimit = sizeLimit;
        this.evict = evict;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    @SuppressWarnings("unchecked")
    public V get(Object key) {
        this.expire.refreshExpire(Collections.singletonList((K)key));
        return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        ICacheEvictContext<K, V> context = new CacheEvictContext<>();
        context.setKey(key);
        context.setSize(sizeLimit);
        context.setCache(this);

        evict.evict(context);

        if (isExceeded()) {
            throw new CacheRuntimeException("Cache size limit exceeded");
        }

        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }

    private boolean isExceeded() {
        final int currentSize = this.size();
        return currentSize >= this.sizeLimit;
    }

    @Override
    public ICache<K, V> expire(K key, long timeInMills) {
        long expireTime = System.currentTimeMillis() + timeInMills;
        return this.expireAt(key, expireTime);
    }

    @Override
    public ICache<K, V> expireAt(K key, long timeInMills) {
        this.expire.expire(key, timeInMills);
        return this;
    }
}
