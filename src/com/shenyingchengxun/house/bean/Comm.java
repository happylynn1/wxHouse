package com.shenyingchengxun.house.bean;

public class Comm {
	public String houseid;
	public String id;
	public String name;
	public String city;
	public String area;
	public String street;
	public String storeId;
	public String avg_price;
	public String saleNum;
	public String rentNum;
	public String picPath;
	public String address;
	public String lng;
	public String lat;
	
	public Comm(String name, String storeId,String city,String area,String street,String address,String lng,String lat) {
		super();
		this.name = name;
		this.city = city;
		this.storeId = storeId;
		this.area = area;
		this.street = street;
		this.address = address;
		this.lng = lng;
		this.lat = lat;
	}
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getAvg_price() {
		return avg_price;
	}
	public void setAvg_price(String avg_price) {
		this.avg_price = avg_price;
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
	public String getSaleNum() {
		return saleNum;
	}
	public void setSaleNum(String saleNum) {
		this.saleNum = saleNum;
	}
	public String getRentNum() {
		return rentNum;
	}
	public void setRentNum(String rentNum) {
		this.rentNum = rentNum;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	public Comm(String houseid, String id, String name, String city,
			String area, String street, String storeId, String avg_price,
			String saleNum, String rentNum, String picPath) {
		super();
		this.houseid = houseid;
		this.id = id;
		this.name = name;
		this.city = city;
		this.area = area;
		this.street = street;
		this.storeId = storeId;
		this.avg_price = avg_price;
		this.saleNum = saleNum;
		this.rentNum = rentNum;
		this.picPath = picPath;
	}
	public Comm() {
		super();
	}
}
