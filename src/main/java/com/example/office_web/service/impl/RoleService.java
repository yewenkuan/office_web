package com.example.office_web.service.impl;

import com.example.office_web.entity.Role;
import com.example.office_web.entity.User;
import com.example.office_web.mapper.UserMapper;
import com.example.office_web.shiro.cache.OfficeWebCacheManager;
import com.example.office_web.shiro.cache.RedisCacher;
import com.example.office_web.utils.SpringContextHolder;
import com.example.office_web.utils.application.Global;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleService {

    protected Logger logger = LoggerFactory.getLogger(RoleService.class);

    @Autowired
    private OfficeWebCacheManager officeWebCacheManager;


    @Autowired
    private UserMapper userMapper;



    /**
     * 删除用户在shiro的缓存
     * 因为有了角色改变后，shiro缓存会刷新的功能，其实关闭和不关闭浏览器时，同一个用户都可以获取他自己的缓存，这是正常的
     * @param openId
     */
    public void removeUserAuthorization(String openId) {
//        String shiroReam = Global.getConfig("shiroReam");
//
//        String authorizationCacheName = Global.getConfig("authorizationCacheName");
//
//        logger.info("获取到的shiroReam为:{}" ,shiroReam);
//        logger.info("获取到的authorizationCacheName为:{}" ,authorizationCacheName);
//
//        Cache<SimplePrincipalCollection, Object> cache = officeWebCacheManager.getCache(authorizationCacheName);
//        if (cache == null) {
//            return;
//        }
//
//        cache.remove(openId);
        if(StringUtils.isBlank(openId)){
            return;
        }
        RedisCacher redisCacher = SpringContextHolder.getBean(RedisCacher.class);
        redisCacher.getShiroJedisUtils().del(openId);

    }


    /**
     * 根据openId 获取 用户的权限
     * @param openId
     * @return
     */
    public List<Role> getUserAndRoleByOpenId(String openId){
        return userMapper.getRolesByOpenId(openId);
    }
}
