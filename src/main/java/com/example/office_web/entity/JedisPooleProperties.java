package com.example.office_web.entity;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
public class JedisPooleProperties {

    private static JedisPool jedisPool;

//    @Value("${redis.host}")
//    private String host;
//
//    @Value("${redis.port}")
//    private String port;



    public JedisPooleProperties(@Value("${redis.host}") String host, @Value("${redis.port}")Integer port, @Value("${redis.pwd}")String pwd){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxWaitMillis(60 * 1000);
        config.setMaxIdle(50);
        config.setTestOnBorrow(false);
        config.setMinIdle(20);
        config.setMaxTotal(200);
        if(StringUtils.isBlank(pwd)){
            jedisPool = new JedisPool(config, host);
        }else {
            jedisPool = new JedisPool(config, host, port, 10000, pwd);
        }
    }


    public static JedisPool getJedisPool(){
        return jedisPool;
    }
//
//    public String getHost() {
//        return host;
//    }
//
//    public void setHost(String host) {
//        this.host = host;
//    }
//
//    public String getPort() {
//        return port;
//    }
//
//    public void setPort(String port) {
//        this.port = port;
//    }
}
