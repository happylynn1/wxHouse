package com.shenyingchengxun.house.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shenyingchengxun.house.bean.Comm;

public interface CommDao {
	
	public Comm getCommByStoreIdAndComm(@Param("storeId")String storeId,@Param("comm")String comm);
	
	public void addComm(@Param("comm")Comm comm);
	
	public void upComm(@Param("comm")Comm comm);
	
	public List<Comm> getCommList(@Param("_st")int _st,@Param("_ed")int _ed,@Param("storeId")String storeId,@Param("comm")String comm,@Param("openid")String openid);
}
