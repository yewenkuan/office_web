package com.example.office_web.web;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class BaseController {


    /**
     * 成功返回
     * @param content
     * @return
     */
    public String ajaxSucess(Object content){
        Map map = new HashMap();
        map.put("success", true);
        map.put("content", content);
        return JSON.toJSONString(map);
    }

    /**
     * 异常返回
     * @param content
     * @return
     */
    public String ajaxFail(Object content){
        Map map = new HashMap();
        map.put("success", false);
        map.put("content", content);
        return JSON.toJSONString(map);
    }
}
