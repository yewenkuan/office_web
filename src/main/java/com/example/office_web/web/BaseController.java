package com.example.office_web.web;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
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
