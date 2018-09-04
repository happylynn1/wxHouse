package com.shenyingchengxun.house.bean;

public class Collect {
	public String id;
	public String openid;
	public String houseid;
	public String entry_time;
	public String type;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getEntry_time() {
		return entry_time;
	}
	public void setEntry_time(String entry_time) {
		this.entry_time = entry_time;
	}
	public Collect(String id, String openid, String houseid, String entry_time,
			String type) {
		super();
		this.id = id;
		this.openid = openid;
		this.houseid = houseid;
		this.entry_time = entry_time;
		this.type = type;
	}
	public Collect() {
		super();
	}
}
