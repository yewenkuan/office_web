package com.example.office_web.shiro;

import com.example.office_web.entity.User;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.ValidatingSession;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;


/**
 * 不要全部重寫SimpleSession的屬性，如果重寫返回null，就會一大堆bug
 */
public class WetchatSession extends SimpleSession {

    private static final long serialVersionUID = 1L;

    public static ThreadLocal<User> openIdThreaadLocal = new ThreadLocal();//存储传过滤的openId


    private String code;

    private String openId;








    @Override
    public Collection<Object> getAttributeKeys() throws InvalidSessionException {
        return super.getAttributeKeys();
    }

    @Override
    public Object getAttribute(Object o) throws InvalidSessionException {
        return super.getAttribute(o);
    }

    @Override
    public void setAttribute(Object o, Object o1) throws InvalidSessionException {
        super.setAttribute(o, o1);
    }

    @Override
    public Object removeAttribute(Object o) throws InvalidSessionException {
        return super.removeAttribute(o);
    }




    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
