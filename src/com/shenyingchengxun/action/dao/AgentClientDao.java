package com.shenyingchengxun.action.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.shenyingchengxun.action.bean.AgentClient;

public interface AgentClientDao {

	public void addAgentClient(@Param("ac")AgentClient ac);
	
	public Map<String,String> getAgentClient(@Param("ac")AgentClient ac);
	
	public void delAgentClient(@Param("openid")String openid);
}
