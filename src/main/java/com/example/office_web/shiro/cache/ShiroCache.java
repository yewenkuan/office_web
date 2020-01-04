package com.example.office_web.shiro.cache;

import com.example.office_web.entity.User;
import com.example.office_web.utils.application.Global;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Set;


public class ShiroCache<K, V> implements Cache<K, V> {

    protected Logger logger = LoggerFactory.getLogger(ShiroCache.class);

    @Override
    public V get(K k) throws CacheException {
        System.out.println("获取缓存的key为:"+k);

//        if(ob instanceof SimpleAuthorizationInfo){
//            SimpleAuthorizationInfo simpleAuthorizationInfo = (SimpleAuthorizationInfo) ob;
//            Set<String> set = simpleAuthorizationInfo.getRoles();
//            for(String s : set){
//                System.out.println("权限为:"+s);
//            }
//
//        }

        if(k instanceof SimplePrincipalCollection){
            String openId = getOpenId((SimplePrincipalCollection) k);
            V ob =  (V)CacherFactory.cacher.get(openId);
            return ob;
        }

        V ob =  (V)CacherFactory.cacher.get(k);
        System.out.println("获取到缓存为:"+ob);
        return ob;

    }


    public String getOpenId(SimplePrincipalCollection k){
        SimplePrincipalCollection simplePrincipalCollection = k;
        String shiroReam = Global.getConfig("shiroReam");
        Method getPrincipalsLazy = null;

        try {
            getPrincipalsLazy = simplePrincipalCollection.getClass().getDeclaredMethod("getPrincipalsLazy", String.class);
            getPrincipalsLazy.setAccessible(true);
            Set set = (Set) getPrincipalsLazy.invoke(simplePrincipalCollection, shiroReam);
            if(set != null && set.size() > 0){
                User user = (User) set.iterator().next();
                String openId = user.getOpenId();
                System.out.println("获取到了openId=======");
                return openId;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }



    @Override
    public V put(K k, V v) throws CacheException {
        logger.info("设置权限缓存===，key为：{}， value为：{}", k, v);
        if(k instanceof SimplePrincipalCollection){
            String openId = getOpenId((SimplePrincipalCollection) k);
            CacherFactory.cacher.set(openId, v);
            return null;
        }
         CacherFactory.cacher.set(k, v);
        return null;
    }

    @Override
    public V remove(K k) throws CacheException {
        CacherFactory.cacher.delete(k);
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
