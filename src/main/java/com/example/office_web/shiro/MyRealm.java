package com.example.office_web.shiro;

import com.example.office_web.consts.RedisKeyConsts;
import com.example.office_web.entity.Role;
import com.example.office_web.entity.User;
import com.example.office_web.service.impl.RoleService;
import com.example.office_web.service.impl.UserServiceImpl;
import com.example.office_web.utils.JedisUtils;
import com.example.office_web.utils.SpringContextHolder;
import com.example.office_web.utils.application.Global;
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
    @Autowired
    private RoleService roleService;

    private static final Integer EXPIRES = 86400;
    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("===========权限验证进来=======");
        // 获取身份信息
        User user = (User) principalCollection.getPrimaryPrincipal();//这个是读取到new SimpleAuthenticationInfo(user,openId, "myRealm");的user

        List<String> shiroRoleList = new ArrayList<>();
        List<String> shiroPemissionList = new ArrayList<>();

        List<Role> roleList = roleService.getUserAndRoleByOpenId(user.getOpenId());
        roleList.forEach(x -> {
            shiroRoleList.add(x.getRoleEnName());
            x.getPermissionList().forEach(y -> {
                shiroPemissionList.add(y.getPermissionEnName());
            });
        });


        //将权限信息封闭为AuthorizationInfo
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        simpleAuthorizationInfo.addRoles(shiroRoleList);
        simpleAuthorizationInfo.addStringPermissions(shiroPemissionList);


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



        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user,user.getPwd(), Global.getConfig("shiroReam"));//这里的password 要和UsernamePasswordToken token = new UsernamePasswordToken(user.getOpenId(), user.getOpenId());一样才会验证通过，这里只是对用户名做验证，密码验证给父级

        if(StringUtils.isNotBlank(pwd) && pwd.equals(user.getPwd())){
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(false);//session不存在则创建session
            if(session == null){
                session =  subject.getSession(true);
            }

//            user.setSessionId(session.getId().toString());
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

        WetchatSession wetchatSession = new WetchatSession();
        wetchatSession.setId(session.getId());
        wetchatSession.setOpenId(openId);

        //这个是为了用户关闭浏览器或者会话id改变时，重新在数据库中读取权限数据, 因为ShiroCache的get是以user序列化后作为key的,序列化如果属性一致则序列化后的内容一致
        //所以一次会话的sessionId不会变，读取缓存就好，不同会话不读以前会话的缓存
//        user.setSessionId(session.getId().toString());
        wetchatSession.setLastAccessTime(new Date());
        Map<String, Object> map = new HashMap<>();
        map.put(RedisKeyConsts.USER_INFO_PREFIX+session.getId().toString(), wetchatSession);
        System.out.println("验证登录================");
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user,openId, Global.getConfig("shiroReam"));//这里的password 要和UsernamePasswordToken token = new UsernamePasswordToken(user.getOpenId(), user.getOpenId());一样才会验证通过，这里只是对用户名做验证，密码验证给父级
        //下面这两行必须设置，不然第二次访问时，还是没登陆状态
        wetchatSession.setAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY, simpleAuthenticationInfo.getPrincipals());
        wetchatSession.setAttribute(DefaultSubjectContext.AUTHENTICATED_SESSION_KEY, true);
        JedisUtils.setObjectMap(RedisKeyConsts.USER_INFO_MAP_KEY, map, EXPIRES);
        return simpleAuthenticationInfo;
    }
}
