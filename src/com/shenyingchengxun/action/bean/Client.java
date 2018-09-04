package com.shenyingchengxun.action.bean;

public class Client {
	public String storeid;
	public String openid;
	public String nickname;
	public String gender;
	public String city;
	public String headImage;
	public String required;
	public String status;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStoreid() {
		return storeid;
	}
	public void setStoreid(String storeid) {
		this.storeid = storeid;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	public String getRequired() {
		return required;
	}
	public void setRequired(String required) {
		this.required = required;
	}
	public Client(String storeid, String openid, String nickname,
			String gender, String city, String headImage, String required) {
		super();
		this.storeid = storeid;
		this.openid = openid;
		this.nickname = nickname;
		this.gender = gender;
		this.city = city;
		this.headImage = headImage;
		this.required = required;
	}
	public Client(String openid) {
		super();
		this.openid = openid;
	}
	public Client() {
		super();
	}
	@Override
	public String toString() {
		return "Client [storeid=" + storeid + ", openid=" + openid
				+ ", nickname=" + nickname + ", gender=" + gender + ", city="
				+ city + ", headImage=" + headImage + ", required=" + required
				+ "]";
	}
}
