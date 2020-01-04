package com.example.office_web.web;

import com.example.office_web.config.MysqlConfig;
import com.example.office_web.utils.GenerateUtls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


/**
 * 代码生成器入口
 */
@Controller
@RequestMapping("/api/generate")
public class GenerateController {

    @Autowired
    private MysqlConfig mysqlConfig;


    @ResponseBody
    @RequestMapping(value = "productCode")
    public Map productCode(String tableName){
        Map map = new HashMap();
        try{
            if(StringUtils.isEmpty(tableName)){
                throw  new RuntimeException("表名不能为空");
            }
            String[] tables = tableName.split(":");
            GenerateUtls.genal(mysqlConfig, tables);
            map.put("msg", "成功生成代码");
            return map;
        }catch (Exception e){
            map.put("msg",e.getMessage());
            return map;
        }
    }
}
