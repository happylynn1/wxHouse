package com.shenyingchengxun.house.bean;

import java.util.Random;

import com.shenyingchengxun.util.Md5;

public class ClientHouse {
	public String houseid;
	public String openid;
	public String type;
	public String name;
	public String phone;
	public String remark;
	public String storeId;
	public String getHouseid() {
		return houseid;
	}
	public void setHouseid(String houseid) {
		this.houseid = houseid;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public ClientHouse(String houseid, String openid, String type,
			String name, String phone, String remark,String storeId) {
		super();
		if(houseid==null){
			String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";   
		    String houseidStr = "";
			Random random = new Random();   
		    for (int i = 0; i < 16; i++) {   
		        int number = random.nextInt(base.length());   
		        houseidStr+=(base.charAt(number));
		    }
		    this.houseid = Md5.string2MD5(houseidStr);
		}else{
			this.houseid = houseid;
		}
		this.openid = openid;
		this.type = type;
		this.name = name;
		this.phone = phone;
		this.remark = remark;
		this.storeId = storeId;
	}
	
}
