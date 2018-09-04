package com.shenyingchengxun.action.service;

import java.util.List;

import com.shenyingchengxun.action.bean.Client;

public interface ClientService {
	public Client getClientByOpenid(String openid);
	
	public List<Client> getClientListByAgentOpenid(String openid,String storeId);

	public List<Client> getClientListByChatAgent(String openid,String storeId);
}
