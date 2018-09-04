package com.shenyingchengxun.house.dao;

import org.apache.ibatis.annotations.Param;

import com.shenyingchengxun.house.bean.ClientHouse;
public interface ClientHouseDao {
	
	public void addClientHouse(@Param("ch")ClientHouse ch);
	
}
