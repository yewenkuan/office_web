package com.example.office_web.shiro.cache;

import java.util.Set;

public interface Cacher<K, V> {

    public void set(K key, V value);

    public V get(K key);

    public void delete(K key);

    public Set<String> keys(String string);

    public Set<?> getAllValue(String keyPrefix);

    public Long dbSize();

}
