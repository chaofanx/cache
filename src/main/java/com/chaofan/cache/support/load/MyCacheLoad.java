package com.chaofan.cache.support.load;

import com.chaofan.cache.core.api.ICache;
import com.chaofan.cache.core.api.ICacheLoad;

/**
 * @author 李超凡
 * @since 2022/5/29 23:11
 */
public class MyCacheLoad implements ICacheLoad<String, String> {
    @Override
    public void load(ICache<String, String> cache) {
        cache.put("1", "1");
        cache.put("2", "2");
    }
}
