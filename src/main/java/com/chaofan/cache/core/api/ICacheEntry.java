package com.chaofan.cache.core.api;

/**
 * 缓存项接口
 *
 * @author 李超凡
 * @since 2022/5/28 20:49
 */
public interface ICacheEntry<K, V> {

    /**
     * 获取缓存的key
     */
    K key();

    /**
     * 返回缓存的值
     */
    V value();
}
