package com.chaofan.cache.support.expire;

import com.chaofan.cache.core.api.ICache;
import com.chaofan.cache.core.api.ICacheExpire;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 缓存过期实现
 * 轮询检查过期时间
 *
 * @author 李超凡
 * @since 2022/5/29 15:24
 */
public class SingleCacheExpire<K, V> implements ICacheExpire<K, V> {

    /**
     * 维护所有key
     */
    private final Map<K, Long> expireMap = new HashMap<>();

    /**
     * 清理key限制
     */
    public static final int LIMIT = 100;

    private final ICache<K, V> cache;

    /**
     * 单线程轮询
     */
    private static final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    public SingleCacheExpire(ICache<K, V> cache) {
        this.cache = cache;
        this.init();
    }

    private void init() {
        service.scheduleAtFixedRate(new ExpireThread(), 100, 100, TimeUnit.MILLISECONDS);
    }

    @Override
    public void expire(K key, long expireAt) {
        expireMap.put(key, expireAt);
    }

    @Override
    public void refreshExpire(Collection<K> keyList) {
        if(keyList.isEmpty()) {
            return;
        }

        // 判断大小，小的作为外循环。一般都是过期的 keys 比较小。
        if(keyList.size() <= expireMap.size()) {
            for(K key : keyList) {
                Long expireAt = expireMap.get(key);
                expireKey(key, expireAt);
            }
        } else {
            for(Map.Entry<K, Long> entry : expireMap.entrySet()) {
                this.expireKey(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public Long expireTime(K key) {
        return expireMap.get(key);
    }

    private void expireKey(final K key, final Long expireAt) {
        if(expireAt == null) {
            return;
        }

        long currentTime = System.currentTimeMillis();
        if(currentTime >= expireAt) {
            expireMap.remove(key);
            // 再移除缓存，后续可以通过惰性删除做补偿
            V removeValue = cache.remove(key);

            // 执行淘汰监听器
            // ICacheRemoveListenerContext<K,V> removeListenerContext = CacheRemoveListenerContext.<K,V>newInstance().key(key).value(removeValue).type(CacheRemoveType.EXPIRE.code());
            // for(ICacheRemoveListener<K,V> listener : cache.removeListeners()) {
            //     listener.listen(removeListenerContext);
            // }
        }
    }

    /**
     * 清理线程
     */
    private class ExpireThread implements Runnable {

        @Override
        public void run() {
            //为空则跳过
            if(expireMap.isEmpty()) return;

            //获取 key 进行处理
            int count = 0;
            for(Map.Entry<K, Long> entry : expireMap.entrySet()) {
                // 清理指定数量
                if(count >= LIMIT) {
                    return;
                }
                expireKey(entry);
                count++;
            }
        }

        /**
         * 清理Key
         */
        private void expireKey(Map.Entry<K, Long> entry) {
            final K key = entry.getKey();
            final Long expireAt = entry.getValue();
            // 删除的逻辑处理
            long currentTime = System.currentTimeMillis();
            if(currentTime >= expireAt) {
                expireMap.remove(key);
                // 再移除缓存，后续可以通过惰性删除做补偿
                cache.remove(key);
            }
        }
    }
}
