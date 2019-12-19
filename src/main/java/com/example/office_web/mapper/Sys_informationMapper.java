package com.example.office_web.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.example.office_web.entity.Sys_information;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ywk
 * @since 2019-12-19
 */
public interface Sys_informationMapper extends BaseMapper<Sys_information> {


    List<Sys_information> getShufflingFigureList();


    List<Sys_information> getInfomationList(Pagination page);
}
