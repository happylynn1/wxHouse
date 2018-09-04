package com.shenyingchengxun.house.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shenyingchengxun.house.bean.VisitLog;
public interface VisitLogDao {
	
	public void addVisitLog(@Param("vl")VisitLog vl);
	
	public List<VisitLog> getMyVisitLogList(@Param("openid")String openid);
}
