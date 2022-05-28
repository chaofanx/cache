package com.chaofan.cache.support.evict;

import com.chaofan.cache.core.api.ICacheEntry;
import com.chaofan.cache.core.api.ICacheEvict;
import com.chaofan.cache.core.api.ICacheEvictContext;

/**
 * 驱逐策略抽象类
 *
 * @author 李超凡
 * @since 2022/5/28 21:22
 */
public abstract class AbstractEvict<K,V> implements ICacheEvict<K,V> {
    @Override
    public ICacheEntry<K,V> evict(ICacheEvictContext<K, V> context) {
        return doEvict(context);
    }

    /**
     * 执行移除
     */
    protected abstract ICacheEntry<K,V> doEvict(ICacheEvictContext<K, V> context);

    @Override
    public void updateKey(K key) {}

    @Override
    public void removeKey(K key) {}
}
