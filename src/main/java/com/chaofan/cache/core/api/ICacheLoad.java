package com.chaofan.cache.core.api;

/**
 * @author 李超凡
 * @since 2022/5/29 23:07
 */
public interface ICacheLoad<K, V> {
    void load(final ICache<K, V> cache);
}
