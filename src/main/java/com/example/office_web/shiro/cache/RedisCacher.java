package com.example.office_web.shiro.cache;

import com.example.office_web.utils.JedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class RedisCacher<K,V> implements Cacher<K, V> {

    protected static Logger logger = LoggerFactory.getLogger(RedisCacher.class);


    protected  ShiroJedisUtils shiroJedisUtils;


    public ShiroJedisUtils getShiroJedisUtils(){
        if(shiroJedisUtils == null){
            shiroJedisUtils = new ShiroJedisUtils<K, V>();
        }

        return shiroJedisUtils;
    }

    @Override
    public void set(K key, V value) {
        getShiroJedisUtils().setObjectMap(key, value, 0);
    }

    @Override
    public V get(K key) {
        return (V)getShiroJedisUtils().getObjectMap(key);

    }

    @Override
    public void delete(K key) {
        getShiroJedisUtils().del(key);
    }

    @Override
    public Set<String> keys(String string) {
        return null;
    }

    @Override
    public Set<?> getAllValue(String keyPrefix) {
        return null;
    }

    @Override
    public Long dbSize() {
        return null;
    }
}
