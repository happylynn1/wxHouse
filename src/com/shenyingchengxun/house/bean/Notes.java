package com.shenyingchengxun.house.bean;

public class Notes {
	public String id;
	public String houseid;
	public String times;
	public String notes;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHouseid() {
		return houseid;
	}
	public void setHouseid(String houseid) {
		this.houseid = houseid;
	}
	public String getTimes() {
		return times;
	}
	public void setTimes(String times) {
		this.times = times;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public Notes(String houseid, String notes) {
		super();
		this.houseid = houseid;
		this.notes = notes;
	}
	public Notes() {
		super();
	}
}
