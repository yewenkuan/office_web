package com.example.office_web.web;


import com.alibaba.fastjson.JSON;
import com.example.office_web.entity.User;
import com.example.office_web.service.impl.UserServiceImpl;
import com.example.office_web.service.webchat.WechatService;
import com.example.office_web.shiro.WetchatSession;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ywk
 * @since 2019-05-14
 */
@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private WechatService wechatService;

    @RequestMapping("/test")
    public void test(){
        userService.selectUser();
    }

    @RequestMapping("/test1")
    public void test1(){
        userService.getPage();
    }


    /**
     *
     * @param
     * @return
     */
    @PostMapping
    @RequestMapping("/auth")
    public String auth(@RequestBody WetchatSession wetchatSession){
        try {
            User user = wechatService.auth(wetchatSession.getCode());
            if(user == null){
                return ajaxFail("fail");
            }else {
                return ajaxSucess(user);
            }
        } catch (Exception e) {
            logger.error("获取用户登陆异常", e);
            return ajaxFail("fail");
        }
    }


    /**
     * 记得补上@Qequestbody
     * @param user
     * @return
     */
    @PostMapping
    @RequestMapping("/login")
    public String login( @RequestBody User user){
        try {
//            user.setOpenId("1222");
//            user.setSessionKey("dsfiwk9i");
            String sessionId =  wechatService.login(user);
            return ajaxSucess(sessionId);
        } catch (Exception e) {
            logger.error("获取用户登陆异常", e);
            return ajaxFail("fail");
        }
    }



    /**
     * 记得补上@Qequestbody
     * @param user
     * @return
     */
    @PostMapping
    @RequestMapping("/loginPc")
    public String loginPc(@RequestBody User user){
        try {
//            user.setAccount("ywk");
//            user.setPwd("1234");
            String sessionId =  wechatService.loginPc(user);
            if(StringUtils.isNotBlank(sessionId)){
                return ajaxSucess(sessionId);
            }else {
                return  ajaxFail("fail");
            }

        } catch (Exception e) {
            logger.error("获取用户登陆异常", e);
            return ajaxFail("fail");
        }
    }





    /**
     * 记得补上@Qequestbody
     * @param user
     * @return
     */
    @GetMapping
    @RequestMapping("/testLoginPc")
    public String testLoginPc( User user){
        try {
            user.setAccount("ddb");
            user.setPwd("ddbpwd");
            String sessionId =  wechatService.loginPc(user);
            if(StringUtils.isNotBlank(sessionId)){
                return ajaxSucess(sessionId);
            }else {
                return  ajaxFail("fail");
            }

        } catch (Exception e) {
            logger.error("获取用户登陆异常", e);
            return ajaxFail("fail");
        }
    }


    /**
     * 这个就是访问时，cookie应该带信息 。缓存管理？？？？
     * 添加了RequiresRoles 注解的话，shiro也会进行登录验证
     * @param user
     * @return
     */

    @RequiresPermissions("mm:fick")
    @RequestMapping("/testRole")
    public String testRole( User user){
        try {

            return ajaxSucess("admin ok");
        } catch (Exception e) {
            return ajaxFail("fail");
        }
    }


}
