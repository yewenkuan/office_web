package com.example.office_web.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;


public class ShiroCache<K, V> implements Cache<K, V> {

    protected Logger logger = LoggerFactory.getLogger(ShiroCache.class);

    @Override
    public V get(K k) throws CacheException {
        return (V)CacherFactory.cacher.get(k);
        //return null;
    }

    @Override
    public V put(K k, V v) throws CacheException {
        logger.info("设置权限缓存===，key为：{}， value为：{}", k, v);
         CacherFactory.cacher.set(k, v);
        return null;
    }

    @Override
    public V remove(K k) throws CacheException {
        return null;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }
}