package com.chaofan.cache.support.persist;

import com.alibaba.fastjson.JSON;
import com.chaofan.cache.core.api.ICache;
import com.chaofan.cache.core.api.ICachePersist;
import com.chaofan.cache.support.model.RDBPersistEntry;
import com.chaofan.cache.util.FileUtil;

import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Set;

/**
 * @author 李超凡
 * @since 2022/5/30 10:16
 */
public class JsonCachePersist<K, V> implements ICachePersist<K, V> {

    private final String path;

    public JsonCachePersist(String path) {
        this.path = path;
    }

    @Override
    public void persist(ICache<K, V> cache) {
        Set<Map.Entry<K,V>> entrySet = cache.entrySet();

        // 创建文件
        FileUtil.createFile(path);
        // 清空文件
        FileUtil.clear(path);

        for(Map.Entry<K,V> entry : entrySet) {
            K key = entry.getKey();
            Long expireTime = cache.expire().expireTime(key);
            RDBPersistEntry<K,V> persistEntry = new RDBPersistEntry<>();
            persistEntry.setKey(key);
            persistEntry.setValue(entry.getValue());
            persistEntry.setExpire(expireTime);

            String line = JSON.toJSONString(persistEntry);
            FileUtil.write(path, line, StandardOpenOption.APPEND);
        }
    }
}
