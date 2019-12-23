package com.example.office_web.shiro;


import com.example.office_web.consts.RedisKeyConsts;
import com.example.office_web.utils.JedisUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.*;

/**
 * 自定义授权会话管理类
 * 说明：采用二级缓存来处理，本地缓存ehcache+redis
 * redis上面采用顶级key来处理，通过expires来设定过期时间,通过不断更新过期时间来实现会话的延时
 * @author wanglei@enersun.com.cn
 * @version 2017-5-4 16:29:23
 */
public class MyJedisSessionDAO extends CachingSessionDAO implements SessionDAO {

    protected Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    protected void doUpdate(Session session) {

    }

    @Override
    protected void doDelete(Session session) {

    }

    /**
     * 第一次访问网站时，sesionId为空（SessionManager的getSessionId方法尝试获取），则进来创建一次
     * 如DefaultSessionManager在创建完session后会调用该方法；
     * 如保存到关系数据库/文件系统/NoSQL数据库；即可以实现会话的持久化；
     * 返回会话ID；主要此处返回的ID.equals(session.getId())；
     */
    @Override
    protected Serializable doCreate(Session session) {
        System.out.println("doCreate进来！！");
        Serializable sessionId = this.generateSessionId(session);
        assignSessionId(session, sessionId);//这个将session和sessionId进行捆绑，不能漏

        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable serializable) {
        System.out.println("doReadSession中的sessionId为："+serializable);
        logger.info("读取session中=====================");
        Map<String, Object> map = JedisUtils.getObjectMap(RedisKeyConsts.USER_INFO_MAP_KEY);
        if(map != null){
           Object object = map.get(RedisKeyConsts.USER_INFO_PREFIX+serializable.toString());
           if(object == null){
               return null;
           }else {
               WetchatSession wetchatSession = (WetchatSession) object;
               wetchatSession.setLastAccessTime(new Date());
               return wetchatSession;
           }
        }
        return null;

    }
}