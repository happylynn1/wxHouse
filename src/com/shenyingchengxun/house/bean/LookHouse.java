package com.shenyingchengxun.house.bean;

public class LookHouse {
	public String id;
	public String storeId;
	public String openid;
	public String houseid;
	public String name;
	public String phone;
	public String time;
	public String addTime;
	public String deal;
	public String dealBy;
	public String reason;
	public String delByClient;
	public String delByAgent;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getHouseid() {
		return houseid;
	}
	public void setHouseid(String houseid) {
		this.houseid = houseid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getDeal() {
		return deal;
	}
	public void setDeal(String deal) {
		this.deal = deal;
	}
	public String getDealBy() {
		return dealBy;
	}
	public void setDealBy(String dealBy) {
		this.dealBy = dealBy;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getDelByClient() {
		return delByClient;
	}
	public void setDelByClient(String delByClient) {
		this.delByClient = delByClient;
	}
	public String getDelByAgent() {
		return delByAgent;
	}
	public void setDelByAgent(String delByAgent) {
		this.delByAgent = delByAgent;
	}
	public LookHouse() {
		super();
	}
	public LookHouse(String id, String storeId, String openid, String houseid,
			String name, String phone, String time, String addTime,
			String deal, String dealBy, String reason, String delByClient,
			String delByAgent) {
		super();
		this.id = id;
		this.storeId = storeId;
		this.openid = openid;
		this.houseid = houseid;
		this.name = name;
		this.phone = phone;
		this.time = time;
		this.addTime = addTime;
		this.deal = deal;
		this.dealBy = dealBy;
		this.reason = reason;
		this.delByClient = delByClient;
		this.delByAgent = delByAgent;
	}
}
