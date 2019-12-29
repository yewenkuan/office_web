package com.example.office_web.shiro.cache;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

public class OfficeWebCacheManager implements CacheManager {






    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        Cacher<K, V> cacher = new RedisCacher<>();
        CacherFactory.cacher = cacher;
        return new ShiroCache<K, V>();
    }
}
