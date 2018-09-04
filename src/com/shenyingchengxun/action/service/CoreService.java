package com.shenyingchengxun.action.service;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.shenyingchengxun.action.bean.StoreInfo;

/**
 * 核心处理业务
 * @author 钮飞虎
 *
 */
public interface CoreService{
	
	/** 
     * 处理微信发来的请求 
     * @param request 
     * @return 
     */  
    public String processRequest(String storeId,String openid,Map<String,String> requestMap);
    /**
     * 转发获取openid
     * @return
     */
    public String snsapi_base(String path,String openid,StoreInfo si);
    /**
     * 获取用户openid信息
     * @param request
     * @return
     */
    public String getOpenid(StoreInfo si,HttpServletRequest request);
}

