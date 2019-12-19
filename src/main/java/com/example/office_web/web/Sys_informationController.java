package com.example.office_web.web;


import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.example.office_web.entity.Sys_information;
import com.example.office_web.service.impl.Sys_informationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
@RequestMapping("/sys_information")
public class Sys_informationController extends BaseController{


    @Autowired
    private Sys_informationServiceImpl sys_informationService;

    /**
     * 获取轮播图列表
     * @return
     */
    @PostMapping(value = "getShufflingFigureList")
    public String getShufflingFigureList(){
        try {
            List<Sys_information> list = sys_informationService.getShufflingFigureList();
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




}
