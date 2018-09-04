package com.shenyingchengxun.house.bean;

import com.shenyingchengxun.action.util.Config;

public class Picture {
	public String houseid;
	public String imagepath;
	public String filename;
	public String openid;
	public String getHouseid() {
		return houseid;
	}
	public void setHouseid(String houseid) {
		this.houseid = houseid;
	}
	public String getImagepath() {
		return imagepath;
	}
	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public Picture(String houseid, String imagepath, String filename,
			String openid) {
		super();
		this.houseid = houseid;
		this.imagepath = imagepath;
		this.filename = filename;
		this.openid = openid;
	}
	public Picture(String filename,
			String openid) {
		super();
		this.imagepath = Config.Base+"housePicture/"+filename;
		this.filename = filename;
		this.openid = openid;
	}
	public Picture() {
		super();
	}
	@Override
	public String toString() {
		return "Picture [houseid=" + houseid + ", imagepath=" + imagepath
				+ ", filename=" + filename + ", openid=" + openid + "]";
	}
	
}
