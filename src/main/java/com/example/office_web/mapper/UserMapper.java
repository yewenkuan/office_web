package com.example.office_web.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.example.office_web.entity.Role;
import com.example.office_web.entity.User;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ywk
 * @since 2019-05-14
 */

public interface UserMapper extends BaseMapper<User> {

    public List<User> getPage(Pagination page);

    public void insertUser(User user);

    public void updateUser(User user);


    public User getUserByOpenId(@Param("openId") String openId);


    public User getUserByAccount(@Param("account")String account);


    /**
     * 根据openId 获取 用户的权限
     * @param openId
     * @return
     */
    public List<Role> getRolesByOpenId(@Param("openId")String openId);
}
