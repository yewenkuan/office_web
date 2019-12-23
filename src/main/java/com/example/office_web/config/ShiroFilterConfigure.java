package com.example.office_web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.FilterRegistration;

@Configuration
public class ShiroFilterConfigure {

    protected Logger logger = LoggerFactory.getLogger(ShiroFilterConfigure.class);


    /**
     * shiroFilterRegister这个名字不能和shiroFilter一样
     * @return
     */
    @Bean
    public FilterRegistrationBean shiroFilterRegister(){
         FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
         DelegatingFilterProxy delegatingFilterProxy = new DelegatingFilterProxy();
         delegatingFilterProxy.setTargetFilterLifecycle(true);

         filterRegistration.addUrlPatterns("/*");
         filterRegistration.setName("shiroFilter");
         filterRegistration.setFilter(delegatingFilterProxy);
        logger.info("shiroFilter过滤器成功添加");
        return filterRegistration;
     }
}
