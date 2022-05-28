package com.chaofan.cache.core.api;

/**
 * @author 李超凡
 * @since 2022/5/28 20:49
 */
public interface ICacheEvict<K, V> {
    /**
     * 清除缓存
     */
    ICacheEntry<K, V> evict(ICacheEvictContext<K, V> context);

    /**
     * 更新缓存key
     */
    void updateKey(final K key);

    /**
     * 移除缓存
     */
    void removeKey(final K key);
}
