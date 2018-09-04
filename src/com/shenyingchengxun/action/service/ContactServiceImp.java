package com.shenyingchengxun.action.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shenyingchengxun.action.bean.Agent;
import com.shenyingchengxun.action.bean.Client;
import com.shenyingchengxun.action.dao.ContactDao;

@Service("ContactServiceImp")
public class ContactServiceImp implements ContactService {
	
	@Autowired	ContactDao contactDao;

	@Override
	public int addContactFilter(String openid, String toOpenid) {
		// TODO Auto-generated method stub
		return contactDao.addContactFilter(openid,toOpenid);
	}

	@Override
	public List<Agent> getFilterAgentByOpenid(String openid) {
		// TODO Auto-generated method stub
		return contactDao.getFilterAgentByOpenid(openid);
	}

	@Override
	public List<Client> getFilterClientByOpenid(String openid) {
		// TODO Auto-generated method stub
		return contactDao.getFilterClientByOpenid(openid);
	}
	
}
