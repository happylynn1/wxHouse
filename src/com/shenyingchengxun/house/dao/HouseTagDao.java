package com.shenyingchengxun.house.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
public interface HouseTagDao {
	
	public List<Map<String,String>> getHouseTagList(@Param("type")String type);
	
	public List<String> getLikeTagList();

}
