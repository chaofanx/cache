package com.chaofan.cache.support.persist;

import com.chaofan.cache.core.api.ICache;
import com.chaofan.cache.core.api.ICachePersist;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * 内部缓存持久化类
 *
 * @author 李超凡
 * @since 2022/5/31 12:36
 */
public class InnerCachePersist<K, V> {

    private static final Logger log = Logger.getLogger("com.chaofan.cache.support.persist.InnerCachePersist");

    private final ICache<K, V> cache;

    private final ICachePersist<K, V> persist;

    private static final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    public InnerCachePersist(ICache<K, V> cache, ICachePersist<K, V> persist) {
        this.cache = cache;
        this.persist = persist;

        this.init();
    }

    private void init() {
        service.scheduleAtFixedRate(() -> {
            log.info("开始缓存持久化信息");
            persist.persist(cache);
            log.info("完成缓存持久化信息");
        }, 0, 10, TimeUnit.MINUTES);
    }



}
