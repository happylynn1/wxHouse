package com.shenyingchengxun.house.service;

import java.util.List;
import java.util.Map;

import com.shenyingchengxun.house.bean.ClientHouse;
import com.shenyingchengxun.house.bean.Comm;
import com.shenyingchengxun.house.bean.HouseSource;
import com.shenyingchengxun.house.bean.Require;

public interface HouseSourceService {
	
	public String addHouseSource(String storeId,HouseSource hs,String address,String lng,String lat);

	public void updateHouseSourceByHouseid(String storeId,HouseSource hs,String address,String lng,String lat);

	public HouseSource getHouseSourceByHouseid(String houseid);

	public void addClientHouse(ClientHouse ch);
	
	public List<Map<String,Object>> getMyCollect(String page,String limit,String openid,String type);

	public void addMyCollect(String openid,String houseid,String type);
	
	public void delMyCollect(String openid,String houseid);
	
	public boolean isMyCollect(String openid,String houseid);
	
	public List<Map<String,Object>> getHouseListByType(String page,String limit,String storeId,String openid,String type,String keys,String room,String price,String towards,String genre);
	
	public List<Map<String, Object>> getHouseListByCommType(String page,String limit,String storeId,String openid,String type, String keys);

	public List<Map<String, Object>> getMyHouseByType(String page,String limit,String storeId,String openid,String type);
	
	public int delMyHouseById(String houseid);
	
	public List<Comm> getCommList(String page,String limit,String storeId,String openid,String comm);
	
	public List<Map<String, Object>> getStoreHotHouseList(String page,String limit,String openid,String storeId);
	
	public int addRequire(Require require);

	public String getWechatNameByStoreid(String storeId);
	
	public void upOtherNotes(String houseid,String type,String content);

	public int updateClienthouseByAgentopenid(String houseid, String agentopenid);

	public HouseSource getClientHouseByHouseid(String houseid);

	public void updateClientHouseSourceByHouseid(String storeId,HouseSource hs, String address, String lng, String lat);

	public void copyClientHouseToAgentHouse(String houseid);

	public void updateClientHouseIsdeal(String houseid);

	public HouseSource getClientHouseInfoByHouseid(String houseid);

	public int updateRequireAgentByRequireid(String requireid, String agent_openid);

	public com.shenyingchengxun.house.bean.Require getRequireByRequireid(String requireid);

}
