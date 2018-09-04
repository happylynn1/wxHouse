package com.shenyingchengxun.house.bean;

public class ClientRecommend {
	public String openid;
	public String nickname;
	public String headImage;
	public String comm;
	public String room;
	public String maxArea;
	public String minArea;
	public String rentMaxPrice;
	public String rentMinPrice;
	public String saleMaxPrice;
	public String saleMinPrice;
	public String price;
	public String area;
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
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	public String getComm() {
		return comm;
	}
	public void setComm(String comm) {
		this.comm = comm;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public String getMaxArea() {
		return maxArea;
	}
	public void setMaxArea(String maxArea) {
		this.maxArea = maxArea;
	}
	public String getMinArea() {
		return minArea;
	}
	public void setMinArea(String minArea) {
		this.minArea = minArea;
	}
	public String getRentMaxPrice() {
		return rentMaxPrice;
	}
	public void setRentMaxPrice(String rentMaxPrice) {
		this.rentMaxPrice = rentMaxPrice;
	}
	public String getRentMinPrice() {
		return rentMinPrice;
	}
	public void setRentMinPrice(String rentMinPrice) {
		this.rentMinPrice = rentMinPrice;
	}
	public String getSaleMaxPrice() {
		return saleMaxPrice;
	}
	public void setSaleMaxPrice(String saleMaxPrice) {
		this.saleMaxPrice = saleMaxPrice;
	}
	public String getSaleMinPrice() {
		return saleMinPrice;
	}
	public void setSaleMinPrice(String saleMinPrice) {
		this.saleMinPrice = saleMinPrice;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public ClientRecommend(String openid, String nickname, String headImage,
			String comm, String room, String maxArea, String minArea,
			String rentMaxPrice, String rentMinPrice, String saleMaxPrice,
			String saleMinPrice, String price, String area) {
		super();
		this.openid = openid;
		this.nickname = nickname;
		this.headImage = headImage;
		this.comm = comm;
		this.room = room;
		this.maxArea = maxArea;
		this.minArea = minArea;
		this.rentMaxPrice = rentMaxPrice;
		this.rentMinPrice = rentMinPrice;
		this.saleMaxPrice = saleMaxPrice;
		this.saleMinPrice = saleMinPrice;
		this.price = price;
		this.area = area;
	}
	public ClientRecommend() {
		super();
	}
}
