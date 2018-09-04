package com.shenyingchengxun.action.service;

import com.shenyingchengxun.action.bean.StoreInfo;


public interface StoreInfoService {
	
	public StoreInfo getStoreInfoByStoreId(String storeId);
	
	public void upStoreAtByStoreId(String storeId,String accessToken);

	public String getWaterMarkByStoreid(String storeid); 
}
