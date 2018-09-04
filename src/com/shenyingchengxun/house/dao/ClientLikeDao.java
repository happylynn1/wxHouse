package com.shenyingchengxun.house.dao;

import org.apache.ibatis.annotations.Param;

import com.shenyingchengxun.house.bean.ClientLike;
public interface ClientLikeDao {
	
	
	public void addClientLike(@Param("cl")ClientLike cl);
	
	public ClientLike getMyClientLikeByOpneid(@Param("openid")String openid);
	
}
