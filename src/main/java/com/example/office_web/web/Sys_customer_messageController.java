package com.example.office_web.web;


import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.example.office_web.entity.Sys_customer_message;
import com.example.office_web.entity.Sys_information;
import com.example.office_web.entity.User;
import com.example.office_web.service.impl.Sys_customer_messageServiceImpl;
import com.example.office_web.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ywk
 * @since 2020-02-01
 */
@RestController
@RequestMapping("/api/sys_customer_message")
public class Sys_customer_messageController extends BaseController {


    @Autowired
    private Sys_customer_messageServiceImpl sys_customer_messageService;


    /**
     * 获取待回復消息的列表
     * @return
     */
    @PostMapping(value = "getToReplyList")
    public String getToReplyList(String queryCondition, Integer pageSize, Integer pageNo){
        try {
            if(pageSize == null){
                pageSize = 10;
            }

            if(pageNo == null){
                pageNo = 1;
            }
              User user = UserUtils.getUser();
//            User user = new User();
//            user.setId("09540747-b4ef-4044-98b8-994dff09d4c8");
            Pagination page = new Page<>(pageNo,pageSize);
            List<Sys_customer_message> list = sys_customer_messageService.getToReplyList(queryCondition, user.getId(), page);
            return ajaxSucess(list);
        } catch (Exception e) {
            logger.error("获取待回復消息的列表异常", e);
            return ajaxFail("获取待回復消息的列表异常");
        }
    }


    /**
     * 获取我的提问列表，如果有回复，则把回复的答案一起返回
     * @param state 0:没有回复读问题 1:有回复的问题
     * @return
     */
    @PostMapping(value = "getMyQuestionList")
    public String getMyQuestionList(String queryCondition,Integer state, Integer pageSize, Integer pageNo){
        try {
            if(pageSize == null){
                pageSize = 10;
            }

            if(pageNo == null){
                pageNo = 1;
            }
            User user = UserUtils.getUser();
//            User user = new User();
//            user.setId("09540747-b4ef-4044-98b8-994dff09d4c8");
            Pagination page = new Page<>(pageNo,pageSize);
            List<Sys_customer_message> list = sys_customer_messageService.getMyQuestionList(queryCondition, state, page);
            return ajaxSucess(list);
        } catch (Exception e) {
            logger.error("获取待回復消息的列表异常", e);
            return ajaxFail("获取待回復消息的列表异常");
        }
    }

    /**
     * 查看消息，更改状态(这个是更改商家的未读消息为已读状态)
     * @param messageId 消息i
     * @return
     */
    @PostMapping(value = "updateStoreMessageState")
    public String updateStoreMessageState(String messageId){
        if(StringUtils.isBlank(messageId) ){
            return ajaxFail("messageId为空");
        }

        try {
            sys_customer_messageService.updateStoreMessageState(messageId);
            return ajaxSucess("查看消息，更改状态成功");
        } catch (Exception e) {
            logger.error("查看消息，更改状态异常", e);
            return ajaxFail("查看消息，更改状态异常");
        }


    }


    /**
     * 查看消息，更改状态(这个是更改普通顾客的未读消息为已读状态)
     * @param messageId 回复的， messageId 在查看未读问题列表时，如果此问题有回复，则此id已经返回
     * @return
     */
    @PostMapping(value = "updateCustomMessageState")
    public String updateCustomMessageState(String messageId){
        if(StringUtils.isBlank(messageId) ){
            return ajaxFail("messageId为空");
        }

        try {
            sys_customer_messageService.updateCustomMessageState(messageId);
            return ajaxSucess("查看消息，更改状态成功");
        } catch (Exception e) {
            logger.error("查看消息，更改状态异常", e);
            return ajaxFail("查看消息，更改状态异常");
        }


    }

}
