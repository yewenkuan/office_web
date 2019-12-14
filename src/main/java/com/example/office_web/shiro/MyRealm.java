package com.example.office_web.shiro;

import com.example.office_web.entity.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.ArrayList;
import java.util.List;


public class MyRealm extends AuthorizingRealm {

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
        String openId = (String) authenticationToken.getPrincipal();
        User user = new User();
        user.setOpenId(openId);
        return new SimpleAuthenticationInfo(user,openId, "myRealm");//这里的password 要和UsernamePasswordToken token = new UsernamePasswordToken(user.getOpenId(), user.getOpenId());一样才会验证通过，这里只是对用户名做验证，密码验证给父级
    }
}
