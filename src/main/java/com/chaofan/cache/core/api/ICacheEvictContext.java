package com.chaofan.cache.core.api;

/**
 * 缓存清除上下文
 *
 * @author 李超凡
 * @since 2022/5/28 20:51
 */
public interface ICacheEvictContext<K, V> {

    /**
     * 新增缓存
     */
    K key();

    /**
     * 缓存值
     */
    ICache<K, V> cache();

    /**
     * 缓存大小
     */
    int size();

    void setKey(K key);

    void setCache(ICache<K, V> cache);

    void setSize(int size);
}
