/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.example.office_web.shiro;


import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * 自定义WEB会话管理类
 * @author ThinkGem
 * @version 2014-7-20
 */
public class SessionManager extends DefaultWebSessionManager {

	private static final Logger logger=LoggerFactory.getLogger(SessionManager.class);



	/**
	 * 重写获取sessionId的方法
	 * @param request
	 * @param response
	 * @return
	 */
	@Override
	protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
		logger.info("==========================进来获取sessId了==============");
		String sid = request.getParameter("officeWebSessionId");
		if(StringUtils.isNotBlank(sid)){
			logger.info("officeWebSessionId获取成功======");
			 return  sid;

		}else {
			Serializable s = 	super.getSessionId(request, response);
			System.out.println("读取到的session为"+s);
			return s;
		}

	}



	protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
		Serializable sessionId = getSessionId(sessionKey);
		ServletRequest request = null;
		if(sessionKey instanceof WebSessionKey) {
			request = ((WebSessionKey) sessionKey).getServletRequest();
		}
		if(request!=null&& sessionId !=null) {
			Session session = (Session)request.getAttribute(sessionId.toString());
			if(session !=null && sessionId !=null) {
				return session;
			}
		}
		Session session = super.retrieveSession(sessionKey);
		if(request!=null && sessionId !=null) {
			request.setAttribute(sessionId.toString(), session);
		}
		return session;
	}


}