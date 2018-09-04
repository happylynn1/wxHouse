package com.shenyingchengxun.house.service;

import java.util.List;
import java.util.Map;

import com.shenyingchengxun.house.bean.HouseSource;

public interface HouseTagService {
	
	public List<Map<String,Object>> getHouseTagList(String type);
	
	public List<String> getLikeTags(List<String> tagList,HouseSource hs);

}
