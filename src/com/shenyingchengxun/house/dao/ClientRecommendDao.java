package com.shenyingchengxun.house.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shenyingchengxun.house.bean.ClientRecommend;
public interface ClientRecommendDao {
	
	public List<ClientRecommend> getClientRecommendListByOpenid(@Param("openid")String openid);
}
