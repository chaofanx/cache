package com.chaofan.cache.support.persist;

import com.chaofan.cache.core.api.ICachePersist;

/**
 * 缓存持久化
 *
 * @author 李超凡
 * @since 2022/5/31 12:57
 */
public class CachePersists {

    public static <K, V>ICachePersist<K, V> none() {
        return new NoneCachePersist<>();
    }

    public static <K, V>ICachePersist<K, V> json(String path) {
        return new JsonCachePersist<>(path);
    }
}
