package com.example.office_web.utils;

import com.example.office_web.consts.RedisKeyConsts;
import com.example.office_web.entity.User;
import com.example.office_web.service.impl.UserServiceImpl;
import com.example.office_web.shiro.WetchatSession;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.util.Map;

public class UserUtils {

    private static UserServiceImpl userService = SpringContextHolder.getBean(UserServiceImpl.class);


    public static User getUser(){
//        Map map = JedisUtils.getObjectMap(RedisKeyConsts.USER_INFO_MAP_KEY);
//        if(map == null){
//            return null;
//        }
//
//         Object object =  map.get(openId);
//        if(object != null){
//            return (User)object;
//        }
        //        return null;



        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();

        return userService.getUserByOpenId(user.getOpenId());


    }
}
