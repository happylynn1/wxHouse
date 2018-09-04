package com.shenyingchengxun.house.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shenyingchengxun.house.bean.VrInfo;

public interface VrInfoDao {
	
	public List<VrInfo> getVrlist(@Param("phone")String phone,@Param("comm")String comm,@Param("area")String area,@Param("room")String room,@Param("towards")String towards);

	public VrInfo getMyVr(@Param("vrno")String vrno);
}
