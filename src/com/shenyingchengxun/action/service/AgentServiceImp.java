package com.shenyingchengxun.action.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shenyingchengxun.action.bean.Agent;
import com.shenyingchengxun.action.dao.AgentDao;

@Service("AgentServiceImp")
public class AgentServiceImp implements AgentService {
	
	@Autowired	AgentDao agentDao;
	public Agent getAgentByOpenid(String openid){
		return agentDao.getAgentByOpenid(openid);
	}
	public List<Agent> getAgentListByClientOpenid(String openid,String storeId) {
		// TODO Auto-generated method stub
		return agentDao.getAgentListByClientOpenid(openid,storeId);
	}
	public List<Agent> getAgentListByStoreId(String storeId,String keys) {
		// TODO Auto-generated method stub
		return agentDao.getAgentListByStoreId(storeId,keys);
	}
	@Override
	public List<Agent> getAgentListByChatClient(String openid,String storeId) {
		// TODO Auto-generated method stub
		return agentDao.getAgentListByChatClient(openid,storeId);
	}
	@Override
	public String getDelPermission(String openid) {
		// TODO Auto-generated method stub
		return agentDao.getDelPermission(openid);
	}
	@Override
	public List<Agent> getRandAgent(String sotreid) {
		// TODO Auto-generated method stub
		return agentDao.getRandAgent(sotreid);
	}
}
