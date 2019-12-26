package com.example.office_web.web;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.example.office_web.entity.Sys_information;
import com.example.office_web.service.impl.Sys_informationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * ctr+alt+k 补获异常快捷键
 * @author ywk
 * @since 2019-12-19
 */
@RestController
@RequestMapping("/officeWeb/sys_information")
public class Sys_informationController extends BaseController{


    @Autowired
    private Sys_informationServiceImpl sys_informationService;

    /**
     * 获取轮播图列表
     * @return
     */
    @PostMapping (value = "getShufflingFigureList")
    public String getShufflingFigureList(){
        try {
            List<Sys_information> list = sys_informationService.getShufflingFigureList();
            String ss = ajaxSucess(list);
            //sys_informationService.insertInfoI(UUID.randomUUID().toString().replace("-", ""), "pp\\");
            System.out.println(ss);
            return ajaxSucess(list);
        } catch (Exception e) {
            logger.error("获取轮播图列表异常", e);
            return ajaxFail("获取轮播图列表异常");
        }
    }



    /**
     * 获取资讯列表
     * @return
     */
    @PostMapping(value = "getInfomationList")
    public String getInfomationList(Integer pageSize, Integer pageNo){
        try {
            if(pageSize == null){
                pageSize = 10;
            }

            if(pageNo == null){
                pageNo = 1;
            }
            Pagination page = new Page<>(pageNo,pageSize);
            List<Sys_information> list = sys_informationService.getInfomationList(page);
            return ajaxSucess(list);
        } catch (Exception e) {
            logger.error("获取资讯列表异常", e);
            return ajaxFail("获取资讯列表");
        }
    }


    /**
     * 获取资讯详情
     * @return
     */
    @PostMapping(value = "getInfomationDetail")
    public String getInfomationDetail(String id){
        try {
            Sys_information sysInformation = sys_informationService.getInfomationDetail(id);
            return ajaxSucess(sysInformation);
        } catch (Exception e) {
            logger.error("获取资讯详情异常,id:{}", id, e);
            return ajaxFail("获取资讯详情异常");
        }
    }


    /**
     * 转义字符也是对运行中抽象的值做操作，比如'\\'，那么实际操作的就是\而已，存到mysql也是一个\
     * @param args
     */
    public static void  main(String[] args){
        String d =  "\\d";
        System.out.println(d);
        Map map = new HashMap<>();
        map.put("text", d);
        String s = JSON.toJSONString(map);//JSON.toJSONString会对一个字符'\\'变成两个字符'\\' '\\'
        System.out.println(s);
    }


}
