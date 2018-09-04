package com.shenyingchengxun.action.bean;
/**
 * 经纪人客户关系
 * @author niufeihu
 *
 */
public class AgentClient {
	public String agentOpenid;
	public String clientOpenid;
	public String getAgentOpenid() {
		return agentOpenid;
	}
	public void setAgentOpenid(String agentOpenid) {
		this.agentOpenid = agentOpenid;
	}
	public String getClientOpenid() {
		return clientOpenid;
	}
	public void setClientOpenid(String clientOpenid) {
		this.clientOpenid = clientOpenid;
	}
	public AgentClient(String agentOpenid, String clientOpenid) {
		super();
		this.agentOpenid = agentOpenid;
		this.clientOpenid = clientOpenid;
	}
}
