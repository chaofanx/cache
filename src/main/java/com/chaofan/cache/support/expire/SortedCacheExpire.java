package com.chaofan.cache.support.expire;

import com.chaofan.cache.core.api.ICache;
import com.chaofan.cache.core.api.ICacheExpire;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 把过期时间排序，然后轮询
 *
 * @author 李超凡
 * @since 2022/5/29 15:38
 */
public class SortedCacheExpire<K, V> implements ICacheExpire<K, V> {
    /**
     * 单次清理限制
     */
    private static final int LIMIT = 100;

    /**
     * 排序缓存
     */
    private final Map<Long, List<K>> sortMap = new TreeMap<>((o1, o2) -> (int) (o1 - o2));

    /**
     * 过期map
     */
    private final Map<K, Long> expireMap = new HashMap<>();

    private final ICache<K, V> cache;

    private static final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    public SortedCacheExpire(ICache<K, V> cache) {
        this.cache = cache;
        this.init();
    }

    private void init() {
        service.scheduleAtFixedRate(new ExpireThread(), 1, 1, TimeUnit.SECONDS);
    }

    @Override
    public void expire(K key, long expireAt) {
        List<K> keys = sortMap.get(expireAt);
        if(keys == null) {
            keys = new ArrayList<>();
        }
        keys.add(key);

        // 设置对应的信息
        sortMap.put(expireAt, keys);
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
        return null;
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

    private class ExpireThread implements Runnable {
        @Override
        public void run() {
            if (sortMap.isEmpty()) return;

            int count = 0;
            for(Map.Entry<Long, List<K>> entry : sortMap.entrySet()) {
                final Long expireAt = entry.getKey();
                List<K> expireKeys = entry.getValue();

                // 判断队列是否为空
                if(expireKeys.isEmpty()) {
                    sortMap.remove(expireAt);
                    continue;
                }
                if(count >= LIMIT) {
                    return;
                }

                // 删除的逻辑处理
                long currentTime = System.currentTimeMillis();
                if(currentTime >= expireAt) {
                    Iterator<K> iterator = expireKeys.iterator();
                    while (iterator.hasNext()) {
                        K key = iterator.next();
                        // 先移除本身
                        iterator.remove();
                        expireMap.remove(key);

                        // 再移除缓存，后续可以通过惰性删除做补偿
                        cache.remove(key);

                        count++;
                    }
                } else {
                    // 直接跳过，没有过期的信息
                    return;
                }
            }
        }
    }
}
