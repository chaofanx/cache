package com.chaofan.cache.support.persist;

import com.chaofan.cache.core.api.ICache;
import com.chaofan.cache.core.api.ICachePersist;

/**
 * @author 李超凡
 * @since 2022/5/31 12:58
 */
public class NoneCachePersist<K, V> implements ICachePersist<K, V> {
    @Override
    public void persist(ICache<K, V> cache) {

    }
}
