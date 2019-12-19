package com.example.office_web.service.impl;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.example.office_web.entity.Sys_information;
import com.example.office_web.mapper.Sys_informationMapper;
import com.example.office_web.service.ISys_informationService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ywk
 * @since 2019-12-19
 */
@Service
@Transactional
public class Sys_informationServiceImpl extends ServiceImpl<Sys_informationMapper, Sys_information> implements ISys_informationService {

    /**
     * 获取轮播图列表
     * @return
     */
    public List<Sys_information> getShufflingFigureList(){
          return baseMapper.getShufflingFigureList();
    }


    /**
     * 获取资讯列表
     * @return
     */
    public List<Sys_information> getInfomationList(Pagination page){
       return baseMapper.getInfomationList(page);
    }
}
