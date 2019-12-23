package com.example.office_web.web;


import com.example.office_web.entity.Sys_menu;
import com.example.office_web.entity.User;
import com.example.office_web.service.impl.Sys_menuServiceImpl;
import com.example.office_web.utils.UserUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *      SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));

        set global time_zone='+8:00';
 * @author ywk
 * @since 2019-05-26
 */
@RestController
@RequestMapping("/officeWeb/sys_menu")
public class Sys_menuController extends  BaseController{



    @Autowired
    private Sys_menuServiceImpl sys_menuService;

    /**
     * 获取菜单列表接口
     * @return
     */

    @RequestMapping("/test")
    public User getSysMenuList(){
        try{
//                        Subject subject = SecurityUtils.getSubject();
//           User user1 = (User) subject.getPrincipal();
            User user = UserUtils.getUser();
            return user;
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 获取菜单列表接口
     * @return
     */
//    @RequestMapping("/testVue")
//    public Map testVue(){
//        try{
//            List<String>  menuList = new ArrayList<>();
//            menuList.add("菜单1");
//            menuList.add("菜单2");
//            return ajaxSucess(menuList);
//        }catch (Exception e){
//            return ajaxFail("获取菜单接口发生异常");
//        }
//    }
}
