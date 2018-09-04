package com.shenyingchengxun.action.service;

import java.util.List;

import com.shenyingchengxun.action.bean.Agent;

public interface AgentService {
	
	public Agent getAgentByOpenid(String openid);
	
	public List<Agent> getAgentListByClientOpenid(String openid,String storeId);

	public List<Agent> getAgentListByStoreId(String storeId,String keys);

	public String getDelPermission(String openid);

	public List<Agent> getAgentListByChatClient(String openid,String storeId);

	public List<Agent> getRandAgent(String storeid);
	
}
