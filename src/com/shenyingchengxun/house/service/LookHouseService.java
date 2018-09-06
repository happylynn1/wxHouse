package com.shenyingchengxun.house.service;

import java.util.Map;

public interface LookHouseService {

	public Map<String,String> addLookHouse(String storeId,String openid,String name,String phone,String time,String houseid);
	
	public Map<String,Object> getLookHouseList(String page,String limit,String openid);
	
	public int delLookHouse(String id,String isAgent);

	public int cancelLookHouse(String id,String storeId,String openid,String toOpenid,String isAgent,String reason);
	
	public Map<String,String> getLookHouseInfo(String id,String openid,String storeId);
	
}