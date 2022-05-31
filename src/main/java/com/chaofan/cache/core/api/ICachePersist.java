package com.chaofan.cache.core.api;

/**
 * @author 李超凡
 * @since 2022/5/30 10:14
 */
public interface ICachePersist<K, V> {

    /**
     * 持久化操作
     */
    void persist(final ICache<K, V> cache);
}
