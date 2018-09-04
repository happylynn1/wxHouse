package com.shenyingchengxun.action.dao;

import org.apache.ibatis.annotations.Param;

import com.shenyingchengxun.action.bean.StoreInfo;

public interface StoreInfoDao {
	
	public StoreInfo getStoreInfoByStoreId(@Param("storeId")String storeId);
	
	public void upStoreAtByStoreId(@Param("storeId")String storeId,@Param("accessToken")String accessToken);

	public String getWaterMarkByStoreid(@Param("storeid")String storeid);
	
}
