package com.example.office_web.utils;

import com.example.office_web.consts.RedisKeyConsts;
import com.example.office_web.entity.User;
import com.example.office_web.shiro.WetchatSession;

import java.util.Map;

public class UserUtils {



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


        return WetchatSession.openIdThreaadLocal.get();

    }
}
