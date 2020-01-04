package com.example.office_web.service.impl;

import com.example.office_web.shiro.cache.OfficeWebCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleService {



    @Autowired
    private OfficeWebCacheManager officeWebCacheManager;


    /**
     * 删除用户在shiro的缓存
     * 因为用户是setSessionId的，所以删除时要不要重写user的equal和hashcode方法了，让他不和sessionId挂钩，如果重写了，那么关闭浏览器时，用户再次登陆，序列化后会不会判断成一样的。
     * 因为有了角色改变后，shiro缓存会刷新的功能，其实关闭和不关闭浏览器时，同一个用户都可以获取他自己的缓存，这是正常的
     * @param userName
     */
    public void removeUserAuthorization(String userName) {
        Cache<SimplePrincipalCollection, Object> cache = officeWebCacheManager.getCache("authorizationCache");
        if (cache == null) {
            return;
        }

        cache.remove(new SimplePrincipalCollection(userName, "myReam"));

    }
}
