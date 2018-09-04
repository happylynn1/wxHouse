package com.shenyingchengxun.action.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface ClickLogDao {
	
	@Insert("insert into wx_clientclick_log values (#{openid},(select id from wx_sys_module where url=#{url}),now())")
	public void addClickLog(@Param("openid")String openid,@Param("url")String url);
	
}
