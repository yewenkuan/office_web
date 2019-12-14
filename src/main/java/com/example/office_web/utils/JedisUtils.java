package com.example.office_web.utils;

import com.example.office_web.entity.JedisPooleProperties;
import com.example.office_web.web.UserController;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

public class JedisUtils {

    private static final Logger logger = LoggerFactory.getLogger(JedisUtils.class);



    /**
     *
     * @param key
     * @param object
     * @param cacheSeconds 过期时间， 0代表永不过期
     * @return
     */
    public static boolean setObj(String key,Object object, Integer cacheSeconds)
    {
        Jedis jedis = null;
        try {
            jedis =  JedisPooleProperties.getJedisPool().getResource();
            jedis.set(key.getBytes(), SerializeUtil.serialize(object));
            if(cacheSeconds != null && cacheSeconds > 0){
                jedis.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
           logger.error("设置缓存异常", e);
           return false;
        }finally {
            JedisPooleProperties.getJedisPool().returnResource(jedis);
        }

        return true;

    }



    /**
     * 设置Map缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static boolean setObjectMap(String key, Map<String, Object> value, int cacheSeconds) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = JedisPooleProperties.getJedisPool().getResource();
            if (jedis.exists(key)) {
                jedis.del(key);
            }
            Map<byte[], byte[]> map =new HashMap<>();
            for (Map.Entry<String, Object> e : value.entrySet()){
                map.put(e.getKey().getBytes("UTF-8"), SerializeUtil.serialize(e.getValue()));
            }
            result = jedis.hmset(key.getBytes("UTF-8"), map);
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
            logger.debug("setObjectMap {} = {}", key, value);
        } catch (Exception e) {
            logger.warn("setObjectMap {} = {}", key, value, e);
            return false;
        } finally {
            JedisPooleProperties.getJedisPool().returnResource(jedis);
        }
        return true;
    }



    /**
     * 获取Map缓存
     * @param key 键
     * @return 值
     */
    public static Map<String, Object> getObjectMap(String key) {
        Map<String, Object> value = null;
        Jedis jedis = null;
        try {
            jedis = JedisPooleProperties.getJedisPool().getResource();
            value = new HashMap<>();
            Map<byte[], byte[]> map = jedis.hgetAll(key.getBytes("UTF-8"));
            for (Map.Entry<byte[], byte[]> e : map.entrySet()){
                value.put(e.getKey().toString(), SerializeUtil.unserialize(e.getValue()));
            }
            logger.debug("getObjectMap {} = {}", key, value);
        } catch (Exception e) {
            logger.warn("getObjectMap {} = {}", key, value, e);
        } finally {
            JedisPooleProperties.getJedisPool().returnResource(jedis);
        }
        return value;
    }

}
