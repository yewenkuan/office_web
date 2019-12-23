package com.example.office_web.service.webchat;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.office_web.consts.RedisKeyConsts;
import com.example.office_web.entity.User;
import com.example.office_web.entity.WechatAuthProperties;
import com.example.office_web.mapper.UserMapper;
import com.example.office_web.service.impl.UserServiceImpl;
import com.example.office_web.shiro.WetchatSession;
import com.example.office_web.utils.JedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;

import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@Transactional
public class WechatService {


    private static final Logger logger = LoggerFactory.getLogger(WechatService.class);

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private WechatAuthProperties wechatAuthProperties;

    /**
     * 服务器第三方session有效时间，单位秒, 默认1天
     */
    private static final Integer EXPIRES = 86400;

    private RestTemplate wxAuthRestTemplate = new RestTemplate();




    /*
       根据code 调用微信接口登陆
     */
    public User auth(String code){
        // 创建Httpclient对象
//        Map codeMap  = (Map) JSON.parse(code);
//        code = (String) codeMap.get("code");
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;
        String strurl="https://api.weixin.qq.com/sns/jscode2session?appid="+wechatAuthProperties.getAppId()+"&secret="+wechatAuthProperties.getSecret()+"&js_code="+code+"&grant_type=authorization_code";
        try {
            // 创建uri
//            URIBuilder builder = new URIBuilder(url);
//            URI uri = builder.build();
//
//            // 创建http GET请求
//            HttpGet httpGet = new HttpGet(uri);
            URL url = new URL(strurl);
            URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
            HttpClient client    = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(uri);


            // 执行请求
            response = httpclient.execute(httpget);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
                // 解析json
                JSONObject jsonObject = (JSONObject) JSONObject.parse(resultString);
                String session_key = jsonObject.get("session_key")+"";
                String  openid = jsonObject.get("openid")+"";
                User user = new User();
                user.setOpenId(openid);
                user.setSessionKey(session_key);
                return user;
            }else {
                logger.error("获取用户登陆状态异常,状态码:{}", response.getStatusLine().getStatusCode());
            }
            return null;
        } catch (Exception e) {
            logger.error("获取用户登陆异常", e);
            return null;
        }







    }


    /**
     * 微信小程序初次进来入口，验证，记录，更新访问者信息
     * @param user
     * @return
     */
    public String login(User user){
        if(StringUtils.isBlank(user.getOpenId())){
            return null;
        }


        Subject currentUser = SecurityUtils.getSubject();
        //这里将user.getSessionKey(), user.getOpenId()作为用户名和密码
        UsernamePasswordToken token = new UsernamePasswordToken(user.getOpenId(), user.getOpenId());

        // 4、认证
        try {
            currentUser.login(token);// 传到MyAuthorizingRealm类中的方法进行认证
        }catch (UnknownAccountException e)
        {
            logger.error("UnknownAccountException===账号不存在", e);
            return null;
        } catch (IncorrectCredentialsException e)
        {
            logger.error("IncorrectCredentialsException===密码不正确", e);
            return null;
        }
        catch (AuthenticationException e) {
            logger.error("认证异常", e);
            return null;
        }

        Subject subject = SecurityUtils.getSubject();
        String  sessionId = subject.getSession().getId().toString();
        try {


            user.setCreateDate(new Date());
            user.setId(UUID.randomUUID().toString());
            userService.insertUser(user);
        } catch (Exception e) {
            logger.info("===========>插入用户异常", e);
            logger.info("========进行用户更新操作===========");
            user.setUpdateDate(new Date());
            userService.updateUser(user);
        }

           return sessionId;
        }



        /*
        pc端登陆
         */
    public String loginPc(User user){
        Subject currentUser = SecurityUtils.getSubject();
        //这里将user.getSessionKey(), user.getOpenId()作为用户名和密码
        UsernamePasswordToken token = new UsernamePasswordToken(user.getAccount()+":pc", user.getPwd());

        // 4、认证
        try {
            currentUser.login(token);// 传到MyAuthorizingRealm类中的方法进行认证
        }catch (UnknownAccountException e)
        {
            logger.error("UnknownAccountException===账号不存在", e);
            return null;
        } catch (IncorrectCredentialsException e)
        {
            logger.error("IncorrectCredentialsException===密码不正确", e);
            return null;
        }
        catch (AuthenticationException e) {
            logger.error("认证异常", e);
            return null;
        }

        Subject subject = SecurityUtils.getSubject();
        String  sessionId = subject.getSession().getId().toString();



        return sessionId;
    }


}
