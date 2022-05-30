package com.chaofan.cache.support.load;

import com.chaofan.cache.core.api.ICacheLoad;

/**
 * 加载策略
 *
 * @author 李超凡
 * @since 2022/5/29 23:12
 */
public final class CacheLoads {

    private CacheLoads() {}

    public static <K,V> ICacheLoad<K,V> none() {
        return new NoneCacheLoad<>();
    }
}
