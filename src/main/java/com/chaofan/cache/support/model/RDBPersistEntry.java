package com.chaofan.cache.support.model;

/**
 * 持久化明细
 *
 * @author 李超凡
 * @since 2022/5/31 11:23
 */
public class RDBPersistEntry<K, V> {
    private K key;

    private V value;

    private Long expire;

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public Long getExpire() {
        return expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }
}
