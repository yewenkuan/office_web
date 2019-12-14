package com.example.office_web.config;

import com.example.office_web.shiro.MyRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {


    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager(@Qualifier("myRealm") MyRealm myRealm){
        DefaultWebSecurityManager ds = new DefaultWebSecurityManager();
        ds.setRealm(myRealm);
        return ds;
    }


    /**
     * 猜想（授权）：china.put("/index","authc"); 这些拦截是全局的，也可以@RequiresRoles("admin")或者@RequiresPermissions("admin:view:*")
     *
     * @param defaultSecurityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultSecurityManager){
        ShiroFilterFactoryBean sf = new ShiroFilterFactoryBean();
        //设置安全管理器
        sf.setSecurityManager(defaultSecurityManager);
//        sf.setLoginUrl("/login");
        sf.setUnauthorizedUrl("/unauthorized");//没有权限，为啥不进来这里
//        sf.setSuccessUrl("/index");
        /**
         * 自定义过滤器
         * anon:
         * authc:
         * user: 只有实现了remberme的操作才能访问
         * perms: 必须得到资源权限才能访问
         * role: 必须得到角色权限的时候才能访问
         */
//        Map<String,String> china = new LinkedHashMap<>();
//        china.put("/index","authc");
//        china.put("/update","authc");
//        china.put("/non","authc");
//        china.put("/toLogin","anon");
//        china.put("/add","perms[user:add]");
//        china.put("/**","anon");
//        sf.setFilterChainDefinitionMap(china);
        return sf;
    }


    @Bean(name = "myRealm")
    public MyRealm getRealm(){
        return new MyRealm();
    }
}
