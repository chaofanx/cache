package com.chaofan.cache.core;

import com.chaofan.cache.core.api.ICache;
import com.chaofan.cache.core.api.ICacheEvict;
import com.chaofan.cache.core.api.ICacheLoad;
import com.chaofan.cache.support.evict.FIFOEvict;
import com.chaofan.cache.support.load.CacheLoads;
import com.chaofan.cache.support.load.MyCacheLoad;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author 李超凡
 * @since 2022/5/28 21:35
 */
public final class CacheBuilder<K, V> {

    private Map<K, V> map = new HashMap<>();

    private int size = 2;

    private ICacheEvict<K, V> evict = new FIFOEvict<>();

    private ICacheLoad<K,V> load = CacheLoads.none();

    public ICache<K, V> build() {
        Cache<K, V> cache = new Cache<>();
        cache.setMap(map);
        cache.setSizeLimit(size);
        cache.setEvict(evict);
        cache.setLoad(load);

        cache.init();
        return cache;
    }

    public static void main(String[] args) {
        ICache<String, String> cache = new CacheBuilder<String, String>().load(new MyCacheLoad()).build();
        System.out.println(cache.keySet());
    }

    public CacheBuilder<K, V> map(Map<K, V> map) {
        this.map = Objects.requireNonNull(map);
        return this;
    }

    public CacheBuilder<K, V> size(int size) {
        this.size = size;
        return this;
    }

    public CacheBuilder<K, V> evict(ICacheEvict<K, V> evict) {
        this.evict = Objects.requireNonNull(evict);
        return this;
    }

    public CacheBuilder<K, V> load(ICacheLoad<K, V> load) {
        this.load = Objects.requireNonNull(load);
        return this;
    }
}
