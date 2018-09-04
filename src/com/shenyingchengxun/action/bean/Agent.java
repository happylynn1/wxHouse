package com.shenyingchengxun.action.bean;
/**
 * 客户表
 * @author niufeihu
 *
 */
public class Agent {
	public String openid;
	public String nickname;
	public String gender;
	public String city;
	public String company;
	public String storeId;
	public String storeName;
	public String head_image;
	public String name;
	public String phone;
	public String manageComm;
	public String speciality;
	public String workperiod;
	public String isonjob;
	public String viprole;
	public String houseAmount;
	public String vrAmount;
	public String uuid;
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
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getHead_image() {
		return head_image;
	}
	public void setHead_image(String head_image) {
		this.head_image = head_image;
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
	public String getManageComm() {
		return manageComm;
	}
	public void setManageComm(String manageComm) {
		this.manageComm = manageComm;
	}
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	public String getWorkperiod() {
		return workperiod;
	}
	public void setWorkperiod(String workperiod) {
		this.workperiod = workperiod;
	}
	public String getIsonjob() {
		return isonjob;
	}
	public void setIsonjob(String isonjob) {
		this.isonjob = isonjob;
	}
	public String getViprole() {
		return viprole;
	}
	public void setViprole(String viprole) {
		this.viprole = viprole;
	}
	public String getHouseAmount() {
		return houseAmount;
	}
	public void setHouseAmount(String houseAmount) {
		this.houseAmount = houseAmount;
	}
	public String getVrAmount() {
		return vrAmount;
	}
	public void setVrAmount(String vrAmount) {
		this.vrAmount = vrAmount;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public Agent(String openid, String nickname, String gender, String city,
			String company, String storeId, String storeName,
			String head_image, String name, String phone, String manageComm,
			String speciality, String workperiod, String isonjob,
			String viprole, String houseAmount, String vrAmount, String uuid) {
		super();
		this.openid = openid;
		this.nickname = nickname;
		this.gender = gender;
		this.city = city;
		this.company = company;
		this.storeId = storeId;
		this.storeName = storeName;
		this.head_image = head_image;
		this.name = name;
		this.phone = phone;
		this.manageComm = manageComm;
		this.speciality = speciality;
		this.workperiod = workperiod;
		this.isonjob = isonjob;
		this.viprole = viprole;
		this.houseAmount = houseAmount;
		this.vrAmount = vrAmount;
		this.uuid = uuid;
	}
	public Agent() {
		super();
	}
	@Override
	public String toString() {
		return "Agent [openid=" + openid + ", nickname=" + nickname
				+ ", gender=" + gender + ", city=" + city + ", company="
				+ company + ", storeId=" + storeId + ", storeName=" + storeName
				+ ", head_image=" + head_image + ", name=" + name + ", phone="
				+ phone + ", manageComm=" + manageComm + ", speciality="
				+ speciality + ", workperiod=" + workperiod + ", isonjob="
				+ isonjob + ", viprole=" + viprole + ", houseAmount="
				+ houseAmount + ", vrAmount=" + vrAmount + ", uuid=" + uuid
				+ "]";
	}
}
