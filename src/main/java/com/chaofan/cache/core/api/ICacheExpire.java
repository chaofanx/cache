package com.chaofan.cache.core.api;

import java.util.Collection;

/**
 * @author 李超凡
 * @since 2022/5/29 15:20
 */
public interface ICacheExpire<K, V> {

    /**
     * 过期信息
     */
    void expire(final K key, final long expireAt);

    /**
     * 需要处理的key集合
     */
    void refreshExpire(final Collection<K> keyList);

    /**
     * 待过期的key过期时间
     * 不存在，则返回 null
     */
    Long expireTime(final K key);
}
