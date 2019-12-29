package com.example.office_web.shiro.cache;

import com.example.office_web.utils.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacherFactory {

    protected static Logger logger = LoggerFactory.getLogger(CacherFactory.class);


    /** @desc 缓存工具 */
    public static Cacher cacher;

//    static {
//        logger.info("======开始初始化cacher=======");
//        cacher =  SpringContextHolder.getBean(RedisCacher.class);
//    }


    public static void setCacher(Cacher cacher){
        cacher = cacher;
    }

}
