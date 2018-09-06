package com.shenyingchengxun.action.bean;

/**
 * 公司信息及对应公众号配置
 * @author niufeihu
 *
 */
public class StoreInfo {
	public String storeId;
	public String city;
	public String company;
	public String storeName;
	public String introdu;
	public String imagePath;
	public String appId;
	public String appSecret;
	public String accessToken;
	public long atReg_time;
	public String token;
	public String lookHouse;
	
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
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
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getIntrodu() {
		return introdu;
	}
	public void setIntrodu(String introdu) {
		this.introdu = introdu;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public long getAtReg_time() {
		return atReg_time;
	}
	public void setAtReg_time(long atReg_time) {
		this.atReg_time = atReg_time;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getLookHouse() {
		return lookHouse;
	}
	public void setLookHouse(String lookHouse) {
		this.lookHouse = lookHouse;
	}
	public StoreInfo(String storeId, String city, String company,
			String storeName, String introdu, String imagePath, String appId,
			String appSecret, String accessToken, long atReg_time, String token) {
		super();
		this.storeId = storeId;
		this.city = city;
		this.company = company;
		this.storeName = storeName;
		this.introdu = introdu;
		this.imagePath = imagePath;
		this.appId = appId;
		this.appSecret = appSecret;
		this.accessToken = accessToken;
		this.atReg_time = atReg_time;
		this.token = token;
	}
	public StoreInfo() {
		super();
	}
	@Override
	public String toString() {
		return "StoreInfo [storeId=" + storeId + ", city=" + city
				+ ", company=" + company + ", storeName=" + storeName
				+ ", introdu=" + introdu + ", imagePath=" + imagePath
				+ ", appId=" + appId + ", appSecret=" + appSecret
				+ ", accessToken=" + accessToken + ", atReg_time=" + atReg_time
				+ ", token=" + token + ", lookHouse=" + lookHouse + "]";
	}
}
