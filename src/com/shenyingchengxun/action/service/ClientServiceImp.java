package com.shenyingchengxun.action.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shenyingchengxun.action.bean.Client;
import com.shenyingchengxun.action.dao.ClientDao;

@Service("ClientServiceImp")
public class ClientServiceImp implements ClientService {
	
	@Autowired	ClientDao clientDao;
	public Client getClientByOpenid(String openid){
		return clientDao.getClientByOpenid(openid);
	}
	public List<Client> getClientListByAgentOpenid(String openid,String storeId) {
		// TODO Auto-generated method stub
		return clientDao.getClientListByAgentOpenid(openid,storeId);
	}
	@Override
	public List<Client> getClientListByChatAgent(String openid,String storeId) {
		// TODO Auto-generated method stub
		return clientDao.getClientListByChatAgent(openid,storeId);
	}
}
