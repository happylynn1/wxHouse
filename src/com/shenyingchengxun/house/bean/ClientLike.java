package com.shenyingchengxun.house.bean;

public class ClientLike {
	public String openid;
	public String comm;
	public String room;
	public String saleMaxPrice;
	public String saleMinPrice;
	public String rentMaxPrice;
	public String rentMinPrice;
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
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
	public ClientLike(String openid, String comm, String room,
			String saleMaxPrice, String saleMinPrice, String rentMaxPrice,
			String rentMinPrice) {
		super();
		this.openid = openid;
		this.comm = comm;
		this.room = room;
		this.saleMaxPrice = saleMaxPrice;
		this.saleMinPrice = saleMinPrice;
		this.rentMaxPrice = rentMaxPrice;
		this.rentMinPrice = rentMinPrice;
	}
	public ClientLike() {
		super();
	}
	@Override
	public String toString() {
		return "ClientLike [openid=" + openid + ", comm=" + comm + ", room="
				+ room + ", saleMaxPrice=" + saleMaxPrice + ", saleMinPrice="
				+ saleMinPrice + ", rentMaxPrice=" + rentMaxPrice
				+ ", rentMinPrice=" + rentMinPrice + "]";
	}
}
