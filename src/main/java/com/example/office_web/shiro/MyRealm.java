package com.example.office_web.shiro;

import com.example.office_web.consts.RedisKeyConsts;
import com.example.office_web.entity.User;
import com.example.office_web.service.impl.UserServiceImpl;
import com.example.office_web.utils.JedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;


public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserServiceImpl userService;


    private static final Integer EXPIRES = 86400;
    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("===========进来了");
        // 获取身份信息
        User user = (User) principalCollection.getPrimaryPrincipal();//这个是读取到new SimpleAuthenticationInfo(user,openId, "myRealm");的user
        // 根据身份信息从数据库中查询权限数据
        //....这里使用静态数据模拟
        List<String> permissions = new ArrayList<String>();
        permissions.add("admin");
        permissions.add("user.delete");

        //将权限信息封闭为AuthorizationInfo

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //simpleAuthorizationInfo.addRole();  这个应该就可以同时判断根据角色授权和根据资源授权
        /* 注意资源授权的意思是这个role1=user:create,user:update （对user资源有创建的权力）
        [users]
xttblog=123,role1,role2
codedq=123,role1
[roles]
role1=user:create,user:update
role2=user:create,user:delete
         */
        for(String permission:permissions){
            simpleAuthorizationInfo.addStringPermission(permission);
        }

        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String userName = usernamePasswordToken.getUsername();
        if(StringUtils.isBlank(userName)){
            throw new UnknownAccountException("msg:认证失败, 用户名为空，请重试.！！！");
        }
      String[] arr =  userName.split(":");
        if(arr.length == 1){
           return this.loginWetChat(arr[0]);
        }else {
            String pwd = String.valueOf(usernamePasswordToken.getPassword());
           return loginPc(arr[0], pwd);
        }


    }



    public SimpleAuthenticationInfo loginPc(String account, String pwd){
        User user = userService.getUserByAccount(account);
        if(user == null){
            throw new UnknownAccountException("msg:认证失败, 用户名{"+ account+"}不存在，请重试.！！！");
        }



        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user,user.getPwd(), "myRealm");//这里的password 要和UsernamePasswordToken token = new UsernamePasswordToken(user.getOpenId(), user.getOpenId());一样才会验证通过，这里只是对用户名做验证，密码验证给父级

        if(StringUtils.isNotBlank(pwd) && pwd.equals(user.getPwd())){
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(false);//session不存在则创建session
            if(session == null){
                session =  subject.getSession(true);
            }

            System.out.println("sessionId 为："+session.getId());

            WetchatSession wetchatSession = new WetchatSession();
            wetchatSession.setId(session.getId());
            wetchatSession.setOpenId(user.getOpenId());


            wetchatSession.setLastAccessTime(new Date());
            Map<String, Object> map = new HashMap<>();
            map.put(RedisKeyConsts.USER_INFO_PREFIX+session.getId().toString(), wetchatSession);
            System.out.println("验证登录================");
            //下面这两行必须设置，不然第二次访问时，还是没登陆状态
            wetchatSession.setAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY, simpleAuthenticationInfo.getPrincipals());
            wetchatSession.setAttribute(DefaultSubjectContext.AUTHENTICATED_SESSION_KEY, true);
            JedisUtils.setObjectMap(RedisKeyConsts.USER_INFO_MAP_KEY, map, EXPIRES);
        }

        return simpleAuthenticationInfo;

    }




    public SimpleAuthenticationInfo loginWetChat(String openId){
        User user = new User();
        user.setOpenId(openId);
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession(false);//session不存在则创建session
        if(session == null){
            session =  subject.getSession(true);
        }

        System.out.println("sessionId 为："+session.getId());
        WetchatSession wetchatSession = new WetchatSession();
        wetchatSession.setId(session.getId());
        wetchatSession.setOpenId(openId);


        wetchatSession.setLastAccessTime(new Date());
        Map<String, Object> map = new HashMap<>();
        map.put(RedisKeyConsts.USER_INFO_PREFIX+session.getId().toString(), wetchatSession);
        System.out.println("验证登录================");
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user,openId, "myRealm");//这里的password 要和UsernamePasswordToken token = new UsernamePasswordToken(user.getOpenId(), user.getOpenId());一样才会验证通过，这里只是对用户名做验证，密码验证给父级
        //下面这两行必须设置，不然第二次访问时，还是没登陆状态
        wetchatSession.setAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY, simpleAuthenticationInfo.getPrincipals());
        wetchatSession.setAttribute(DefaultSubjectContext.AUTHENTICATED_SESSION_KEY, true);
        JedisUtils.setObjectMap(RedisKeyConsts.USER_INFO_MAP_KEY, map, EXPIRES);
        return simpleAuthenticationInfo;
    }
}
