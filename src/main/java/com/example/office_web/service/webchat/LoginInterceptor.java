package com.example.office_web.service.webchat;

import com.alibaba.fastjson.JSON;
import com.example.office_web.consts.RedisKeyConsts;
import com.example.office_web.entity.User;
import com.example.office_web.shiro.WetchatSession;
import com.example.office_web.utils.JedisUtils;
import com.google.gson.Gson;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.util.Map;

public class LoginInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {       //请求进入这个拦截器
        BufferedReader bufferedReader =  request.getReader();
        Gson gson = new Gson();
        WetchatSession wetchatSession = gson.fromJson(bufferedReader, WetchatSession.class);
        Map map = JedisUtils.getObjectMap(RedisKeyConsts.USER_INFO_MAP_KEY);
        if(map == null){
            return false;
        }

        if(map.get(wetchatSession.getOpenId()) == null){
            return false;
        }

        WetchatSession.openIdThreaadLocal.set((User) map.get(wetchatSession.getOpenId()));
        return true;        //有的话就继续操作
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        WetchatSession.openIdThreaadLocal.remove();
    }


}
