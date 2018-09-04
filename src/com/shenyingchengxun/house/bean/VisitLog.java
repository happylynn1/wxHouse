package com.shenyingchengxun.house.bean;

public class VisitLog {
	public String openid;
	public String houseid;
	public String click_time;
	public String comm;
	public String price;
	public String room;
	public String towards;
	public String area;
	public String years;
	public String floor;
	public String property_type;
	public String type;
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
	public String getClick_time() {
		return click_time;
	}
	public void setClick_time(String click_time) {
		this.click_time = click_time;
	}
	public String getComm() {
		return comm;
	}
	public void setComm(String comm) {
		this.comm = comm;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public String getTowards() {
		return towards;
	}
	public void setTowards(String towards) {
		this.towards = towards;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getYears() {
		return years;
	}
	public void setYears(String years) {
		this.years = years;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getProperty_type() {
		return property_type;
	}
	public void setProperty_type(String property_type) {
		this.property_type = property_type;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public VisitLog() {
		super();
	}
	public VisitLog(String openid, String houseid, String click_time,
			String comm, String price, String room, String towards,
			String area, String years, String floor, String property_type,
			String type) {
		super();
		this.openid = openid;
		this.houseid = houseid;
		this.click_time = click_time;
		this.comm = comm;
		this.price = price;
		this.room = room;
		this.towards = towards;
		this.area = area;
		this.years = years;
		this.floor = floor;
		this.property_type = property_type;
		this.type = type;
	}
}
