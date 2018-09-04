package com.shenyingchengxun.action.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.shenyingchengxun.action.bean.Agent;
public interface AgentDao {
	
	public Agent getAgentByOpenid(@Param("openid")String openid);
	
	public List<Agent> getAgentListByClientOpenid(@Param("openid")String openid,@Param("storeId")String storeId);

	public List<Agent> getAgentListByStoreId(@Param("storeId")String storeId,@Param("keys")String keys);
	
	public void addAgent(@Param("agent")Agent agent);

	@Select("select delpermi from wx_agentinfo where agent_openid = #{openid}")
	public String getDelPermission(@Param("openid")String openid);

	public List<Agent> getAgentListByChatClient(@Param("openid")String openid,@Param("storeId")String storeId);

	@Select("SELECT agent_openid openid,NAME,nickname,head_image,storeId FROM `wx_agentinfo` WHERE storeId = #{storeid} AND isonjob = '是' ORDER BY RAND() LIMIT 3")
	public List<Agent> getRandAgent(@Param("storeid")String storeid);
	
}
