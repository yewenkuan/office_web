package com.example.office_web.shiro.cache;

import com.example.office_web.entity.JedisPooleProperties;
import com.example.office_web.utils.JedisUtils;
import com.example.office_web.utils.SerializeUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

public class ShiroJedisUtils<K, V> {

    private final String SHIRO_CACHE_PREFIX = "shiro_cache";
    private static final Logger logger = LoggerFactory.getLogger(ShiroJedisUtils.class);

    /**
     * 设置Map缓存
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public  boolean setObjectMap(K key, V value, int cacheSeconds) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = JedisPooleProperties.getJedisPool().getResource();
            jedis.hset(SHIRO_CACHE_PREFIX.getBytes("UTF-8"), SerializeUtil.serialize(key), SerializeUtil.serialize(value));
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
    public  V getObjectMap(K key) {

        Jedis jedis = null;
        try {
            jedis = JedisPooleProperties.getJedisPool().getResource();
            return   (V)SerializeUtil.unserialize(jedis.hget(SHIRO_CACHE_PREFIX.getBytes("UTF-8"),  SerializeUtil.serialize(key)));
        } catch (Exception e) {
            logger.error("getObjectMap {} = {}", key, e);
        } finally {
            JedisPooleProperties.getJedisPool().returnResource(jedis);
        }
        return null;
    }



    /**
     *
     * @param key
     */
    public  void del(K key){

        Jedis jedis = null;
        try {
            jedis = JedisPooleProperties.getJedisPool().getResource();
            Long l = jedis.hdel(SHIRO_CACHE_PREFIX.getBytes("UTF-8"),  SerializeUtil.serialize(key));
            System.out.println("删除的数量为："+l);
        } catch (Exception e) {
            logger.error("setObjectMap {}", key, e);
        } finally {
            JedisPooleProperties.getJedisPool().returnResource(jedis);
        }
    }

}
