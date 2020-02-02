package com.example.office_web.service.impl;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.example.office_web.entity.Sys_customer_message;
import com.example.office_web.entity.User;
import com.example.office_web.mapper.Sys_customer_messageMapper;
import com.example.office_web.service.ISys_customer_messageService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.office_web.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ywk
 * @since 2020-02-01
 */
@Service
public class Sys_customer_messageServiceImpl extends ServiceImpl<Sys_customer_messageMapper, Sys_customer_message> implements ISys_customer_messageService {


    /**
     * 這这里不确定多参数是否会分页,经过测试，多参数也可以
     * @param queryCondition
     * @param userId
     * @param page
     * @return
     */
   public List<Sys_customer_message> getToReplyList(String queryCondition, String userId, Pagination page){
       List<Sys_customer_message> list = baseMapper.getToReplyList(queryCondition, userId, page);
       list.forEach(x -> {
           if(StringUtils.isNotBlank(x.getPicUrl())){
              x.setPicUrlList(Arrays.asList(x.getPicUrl().split(",")));
           }
       });
       return list;
   }


    public List<Sys_customer_message> getMyQuestionList(String queryCondition,Integer state,  Pagination page){
           User user = UserUtils.getUser();
           return baseMapper.getMyQuestionList(user.getId(), queryCondition, state, page);
    }


    public void updateStoreMessageState(String messageId){
        baseMapper.updateStoreMessageState(messageId, 1);
    }




    public void updateCustomMessageState(String messageId){
       baseMapper.updateCustomMessageState(messageId, 1);
    }


}
