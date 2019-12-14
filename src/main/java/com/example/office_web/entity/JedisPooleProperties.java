package com.example.office_web.entity;

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



    public JedisPooleProperties(@Value("${redis.host}") String host, @Value("${redis.port}")String port){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxWaitMillis(60 * 1000);
        config.setMaxIdle(50);
        config.setTestOnBorrow(false);
        config.setMinIdle(20);
        config.setMaxTotal(200);
        jedisPool = new JedisPool(config, host);
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
