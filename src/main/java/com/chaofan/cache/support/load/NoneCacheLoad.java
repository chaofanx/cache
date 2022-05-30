package com.chaofan.cache.support.load;

import com.chaofan.cache.core.api.ICache;
import com.chaofan.cache.core.api.ICacheLoad;

/**
 * 无加载策略
 *
 * @author 李超凡
 * @since 2022/5/29 23:13
 */
public class NoneCacheLoad<K, V> implements ICacheLoad<K, V> {
    @Override
    public void load(ICache<K, V> cache) {
        // nothing
    }
}
