package com.chaofan.cache.core.api;

import java.util.Map;

/**
 * 缓存接口
 * @author 李超凡
 * @since 2022/5/28 20:43
 */
public interface ICache<K, V> extends Map<K, V> {

    /**
     * 缓存过期时间
     * @param key key
     * @param timeInMills 超时时间，单位毫秒
     * @return this
     */
    ICache<K, V> expire(final K key, final long timeInMills);

    /**
     * 指定时间过期
     * @param key key
     * @param timeInMills 时间戳
     * @return this
     */
    ICache<K, V> expireAt(final K key, final long timeInMills);
}
