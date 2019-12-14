package com.example.office_web.web;


import com.alibaba.fastjson.JSON;
import com.example.office_web.entity.User;
import com.example.office_web.service.impl.UserServiceImpl;
import com.example.office_web.service.webchat.WechatService;
import com.example.office_web.shiro.WetchatSession;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ywk
 * @since 2019-05-14
 */
@RestController
@RequestMapping("/user")
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
            wechatService.login(user);
            return ajaxSucess("login ok");
        } catch (Exception e) {
            logger.error("获取用户登陆异常", e);
            return ajaxFail("fail");
        }
    }


    /**
     * 这个就是访问时，cookie应该带信息 。缓存管理？？？？
     *
     * @param user
     * @return
     */
    @RequiresRoles("admin")
    @RequestMapping("/testRole")
    public String testRole( User user){
        try {

            return ajaxSucess("admin ok");
        } catch (Exception e) {
            return ajaxFail("fail");
        }
    }


}
