package com.example.office_web.shiro;

import com.example.office_web.entity.User;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

public class WetchatSession implements Session{

    public static ThreadLocal<User> openIdThreaadLocal = new ThreadLocal();//存储传过滤的openId


    private String id;

    private String code;

    private String openId;

    @Override
    public Serializable getId() {
        return null;
    }

    @Override
    public Date getStartTimestamp() {
        return null;
    }

    @Override
    public Date getLastAccessTime() {
        return null;
    }

    @Override
    public long getTimeout() throws InvalidSessionException {
        return 0;
    }

    @Override
    public void setTimeout(long l) throws InvalidSessionException {

    }

    @Override
    public String getHost() {
        return null;
    }

    @Override
    public void touch() throws InvalidSessionException {

    }

    @Override
    public void stop() throws InvalidSessionException {

    }

    @Override
    public Collection<Object> getAttributeKeys() throws InvalidSessionException {
        return null;
    }

    @Override
    public Object getAttribute(Object o) throws InvalidSessionException {
        return null;
    }

    @Override
    public void setAttribute(Object o, Object o1) throws InvalidSessionException {

    }

    @Override
    public Object removeAttribute(Object o) throws InvalidSessionException {
        return null;
    }


    public void setId(String id) {
        this.id = id;
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
