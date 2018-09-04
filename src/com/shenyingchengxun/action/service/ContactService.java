package com.shenyingchengxun.action.service;

import java.util.List;

import com.shenyingchengxun.action.bean.Agent;
import com.shenyingchengxun.action.bean.Client;


public interface ContactService {
	
	public int addContactFilter(String openid,String toOpenid);

	public List<Agent> getFilterAgentByOpenid(String openid);

	public List<Client> getFilterClientByOpenid(String openid);
	
}
