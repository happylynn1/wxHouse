package com.shenyingchengxun.action.service;

import java.util.Map;

import com.shenyingchengxun.action.bean.StoreInfo;

public interface MsgDistribute {
	
	public  String forText(Map<String, String> requestMap);
	
	public  String forImage(Map<String, String> requestMap);
	
	public  String forLocation(Map<String, String> requestMap);
	
	public  String forTLink(Map<String, String> requestMap);
	
	public  String forVoice(Map<String, String> requestMap);
	
	public  String forEvent(StoreInfo si,Map<String, String> requestMap);
	
}
