package com.shenyingchengxun.action.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shenyingchengxun.action.bean.Client;
public interface ClientDao {

	public void addClient(@Param("client")Client client);
	
	public Client getClientByOpenid(@Param("openid")String openid);
	
	public List<Client> getClientListByAgentOpenid(@Param("openid")String openid,@Param("storeId")String storeId); 
	
	public void delClient(@Param("openid")String openid);

	public List<Client> getClientListByChatAgent(@Param("openid")String openid,@Param("storeId")String storeId);
}
