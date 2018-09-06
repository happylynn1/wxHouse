package com.shenyingchengxun.house.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.shenyingchengxun.house.bean.LookHouse;
public interface LookHouseDao {
	
	public int addLookHouse(@Param("en")LookHouse en);
	
	public LookHouse getLookHouseById(@Param("id")String id); 
	
	public List<Map<String,String>> getLookHouseListByClient(@Param("_st")int _st,@Param("_ed")int _ed,@Param("openid")String openid);

	public List<Map<String,String>> getLookHouseListByAgent(@Param("_st")int _st,@Param("_ed")int _ed,@Param("openid")String openid);
	
	public int delByAgent(@Param("id")String id);

	public int delByClient(@Param("id")String id);
	
	public int cancelLookHouse(@Param("id")String id,@Param("openid")String openid,@Param("reason")String reason);
	
}
