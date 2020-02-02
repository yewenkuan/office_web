package com.example.office_web.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.example.office_web.entity.Sys_customer_message;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.example.office_web.entity.Sys_reply_message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ywk
 * @since 2020-02-01
 */
public interface Sys_customer_messageMapper extends BaseMapper<Sys_customer_message> {

    /**
     * 获取待回复列表
     * @param queryCondition
     * @param userId
     * @param page
     * @return
     */
    public List<Sys_customer_message> getToReplyList(@Param("queryCondition")String queryCondition, @Param("userId")String userId, @Param("page")Pagination page);


    /**
     * 更改sys_customer_message 表中某条消息读状态, (这个是更改商家的未读消息为已读状态)
     * @param messageId
     * @param state
     */
    public void updateStoreMessageState(@Param("messageId")String messageId, @Param("state")Integer state);


    /**
     * 普通用户查看我的提问
     * @param queryCondition
     * @param state
     * @param page
     * @return
     */
    public List<Sys_customer_message> getMyQuestionList(@Param("userId")String userId, @Param("queryCondition")String queryCondition,@Param("state")Integer state,  @Param("page")Pagination page);

    /**
     * 查看消息，更改状态(这个是更改普通顾客的未读消息为已读状态)
     * @param messageId 消息i
     * @return
     */
    public void updateCustomMessageState(@Param("messageId")String messageId, @Param("state")Integer state);

    public void insertQuestion(Sys_customer_message sys_customer_message);


    public void insertReplyMessage(Sys_reply_message Sys_reply_message);
}
