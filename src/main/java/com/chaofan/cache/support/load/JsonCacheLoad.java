package com.chaofan.cache.support.load;

import com.alibaba.fastjson.JSON;
import com.chaofan.cache.core.api.ICache;
import com.chaofan.cache.core.api.ICacheLoad;
import com.chaofan.cache.support.model.RDBPersistEntry;
import com.chaofan.cache.util.FileUtil;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * @author 李超凡
 * @since 2022/5/31 13:10
 */
public class JsonCacheLoad<K, V> implements ICacheLoad<K, V> {

    private final String path;

    public JsonCacheLoad(String path) {
        this.path = path;
    }

    @Override
    public void load(ICache<K, V> cache) {
        List<String> lines = FileUtil.readAllLines(path, StandardCharsets.UTF_8);
        if (lines.isEmpty()) return;
        for (String line : lines) {
            if (line.isEmpty()) continue;
            RDBPersistEntry<K, V> entry = JSON.parseObject(line, RDBPersistEntry.class);
            cache.put(entry.getKey(), entry.getValue());
            if (Objects.nonNull(entry.getExpire())) {
                cache.expireAt(entry.getKey(), entry.getExpire());
            }
        }
    }
}
