package com.shenyingchengxun.house.bean;

public class Require {
	private String id;
	private String openid;
	private String agent_openid;
	private String storeId;
	private String type;
	private String name;
	private String phone;
	private String remark;
	private String city;
	private String district;
	private String street;
	private String usedto;
	private String decorate;
	private String rent_type;
	private String price;
	private String room;
	private String area;
	private String isdealed;
	private String times;
	
	public Require(){}
	
	public Require(String openid,String agent_openid,String storeId,String type,String name,String phone,String remark,String city,
			String district,String street,String usedto,String decorate,String rent_type,String price,String room,String area,
			String isdealed,String times){
		this.openid = openid;
		this.agent_openid = agent_openid;
		this.storeId = storeId;
		this.type = type;
		this.name = name;
		this.phone = phone;
		this.remark = remark;
		this.city = city;
		this.district = district;
		this.street = street;
		this.usedto = usedto;
		this.decorate = decorate;
		this.rent_type = rent_type;
		this.price = price;
		this.room = room;
		this.area = area;
		this.isdealed = isdealed;
		this.times = times;
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

	public String getAgent_openid() {
		return agent_openid;
	}

	public void setAgent_openid(String agent_openid) {
		this.agent_openid = agent_openid;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getUsedto() {
		return usedto;
	}

	public void setUsedto(String usedto) {
		this.usedto = usedto;
	}

	public String getDecorate() {
		return decorate;
	}

	public void setDecorate(String decorate) {
		this.decorate = decorate;
	}

	public String getRent_type() {
		return rent_type;
	}

	public void setRent_type(String rent_type) {
		this.rent_type = rent_type;
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

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getIsdealed() {
		return isdealed;
	}

	public void setIsdealed(String isdealed) {
		this.isdealed = isdealed;
	}

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}
	
}
