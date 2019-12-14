/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.example.office_web.entity;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.alibaba.fastjson.JSON;


/**
 * 控制器支持类
 * @author ThinkGem
 * @version 2013-3-23
 */
public abstract class BaseController {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 管理基础路径
	 */
	@Value("${adminPath}")
	protected String adminPath;
	
	/**
	 * 个人中心路径
	 */
	@Value("${pcenterPath}")
	protected String pcenterPath;
	
	/**
	 * 移动端基础路径
	 */
	@Value("${mobilePath}")
	protected String mobilePath;
	
	/**
	 * 前端基础路径
	 */
	@Value("${frontPath}")
	protected String frontPath;
	
	/**
	 * 前端URL后缀
	 */
	@Value("${urlSuffix}")
	protected String urlSuffix;
	
	/**
	 * 验证Bean实例对象
	 */
	@Autowired
	protected Validator validator;


	

	/**
	 * 添加Model消息
	 * @param message
	 */
	protected void addMessage(Model model, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		model.addAttribute("message", sb.toString());
	}
	
	/**
	 * 添加Flash消息
	 * @param message
	 */
	protected void addMessage(RedirectAttributes redirectAttributes, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		redirectAttributes.addFlashAttribute("message", sb.toString());
	}

	/**
	 * 客户端返回字符串
	 * @param response
	 * @param string
	 * @return
	 */
	protected String renderString(HttpServletResponse response, String string, String type) {
		try {
			response.reset();
	        response.setContentType(type);
	        response.setCharacterEncoding("utf-8");
			response.getWriter().print(string);
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 参数绑定异常
	 */
	@ExceptionHandler({BindException.class, ConstraintViolationException.class, ValidationException.class})
    public String bindException() {  
        return "error/400";
    }
	

	
	//AJAX 通用
	private HashMap<String, Object> ajaxMap = new HashMap<>();
	/**
	 * AJAX 成功返回
	 * @param content 返回内容
	 * @return
	 */
	protected String ajaxSuccess(Object content){
		ajaxMap = new HashMap<>();
		ajaxMap.put("success", true);
		ajaxMap.put("content", content);
		return JSON.toJSONString(ajaxMap);
	}
	/**
	 * AJAX 失败返回
	 * @param content 返回内容
	 * @return
	 */
	protected String ajaxFailed(Object content){
		ajaxMap = new HashMap<>();
		ajaxMap.put("success", false);
		ajaxMap.put("content", content);
		return JSON.toJSONString(ajaxMap);
	}

	/**
	 * 异常返回时，封装异常堆栈信息
	 * @param showErrorMsg
	 * @param e
	 * @return
	 */
	protected String ajaxFailedAndMsg(String showErrorMsg, Exception e){
		ajaxMap = new HashMap<>();
		Map errorMap = new HashMap();
		errorMap.put("showErrorMsg", showErrorMsg);
		errorMap.put("errorMsgHide", e.getMessage());
		ajaxMap.put("success", false);
		ajaxMap.put("content", errorMap);
		return JSON.toJSONString(ajaxMap);
	}

	/**
	 * 默认返回
	 * @param flag true/false
	 * @return
	 */
	protected String ajaxReturn(boolean flag){
		ajaxMap = new HashMap<>();
		ajaxMap.put("success", flag);
		ajaxMap.put("content", flag ? "成功" : "失败");
		return JSON.toJSONString(ajaxMap);
	}
	

	/**
	 * 重定向的信息
	 * @param message
	 * @param flag 0 代表转义，1代表反转
	 * @return
	 */
	protected String setRedirectMessage(String message, int flag){
		if(StringUtils.isBlank(message)){
			return  null;
		}
		try {
			if(flag == 0){
				return java.net.URLEncoder.encode(message,"UTF-8");
			}else {
				return java.net.URLDecoder.decode(message,"UTF-8");
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 *
	 * @param paramValue
	 * @return
	 */
	public String decodeParam(String paramValue) {
		if (StringUtils.isNotBlank(paramValue)) {
			try {
				paramValue = new String(paramValue.getBytes("ISO-8859-1"), "UTF-8");
				paramValue = java.net.URLDecoder.decode(paramValue, "UTF-8");
				return paramValue;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return paramValue;
		}
	}
	
}
