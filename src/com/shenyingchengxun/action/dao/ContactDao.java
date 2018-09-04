package com.shenyingchengxun.action.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shenyingchengxun.action.bean.Agent;
import com.shenyingchengxun.action.bean.Client;

public interface ContactDao {

	public int addContactFilter(@Param("openid")String openid,@Param("toOpenid")String toOpenid);

	public int delContactFilter(@Param("openid")String openid,@Param("toOpenid")String toOpenid);
	
	public List<Agent> getFilterAgentByOpenid(@Param("openid")String openid);

	public List<Client> getFilterClientByOpenid(@Param("openid")String openid);

}
