package com.example.office_web.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.example.office_web.entity.User;
import com.example.office_web.mapper.UserMapper;
import com.example.office_web.service.IUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ywk
 * @since 2019-05-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> selectUser(){
       Page page = new Page(1,1);
        List<User> list = userMapper.selectPage(page, null);
        System.out.println(list);
        return list;
    }


    public List<User> getPage(){
        Pagination page = new Page<>(1,1);
        List<User> list = userMapper.getPage(page);
        System.out.println(list);
        return list;

    }


    public void insertUser(User user){
        userMapper.insertUser(user);
    }

    public void updateUser(User user){
        userMapper.updateUser(user);
    }


    public User getUserByAccount(String account){
       return userMapper.getUserByAccount(account);
    }


    public User getUserByOpenId(String openId){
        return userMapper.getUserByOpenId(openId);
    }
}
