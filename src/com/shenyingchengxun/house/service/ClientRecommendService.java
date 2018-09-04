package com.shenyingchengxun.house.service;

import java.util.List;
import java.util.Map;

import com.shenyingchengxun.action.bean.StoreInfo;
import com.shenyingchengxun.house.bean.ClientRecommend;

public interface ClientRecommendService {

	public List<ClientRecommend> getClientRecommendListByOpenid(String houseid,String openid);
	
	public List<Map<String,Object>> getRecommendListByClientOpenid(String page,String limit,String storeId,String openid,String type);
	
	public void  batchSend(StoreInfo si,String openid, String houseid,String[] openidArr,boolean flag);
	
}