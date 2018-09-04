package com.shenyingchengxun.house.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.shenyingchengxun.house.bean.Collect;
public interface CollectDao {
	
	@Select("select houseid from wx_housecollect where openid = #{openid} and type = #{type} group by houseid")
	public List<String> getMyCollectHouseidListByOpenid(@Param("openid")String openid,@Param("type")String type);
	
	@Insert(" insert into wx_housecollect (openid,houseid,entry_time,type) values (#{co.openid},#{co.houseid},now(),#{co.type}) ")
	public void addMyCollect(@Param("co")Collect co);
	
	@Delete("delete from wx_housecollect where openid = #{co.openid} and houseid = #{co.houseid} ")
	public void delMyCollect(@Param("co")Collect co);
	
	@Select("select count(*) from wx_housecollect where openid = #{openid} and houseid = #{houseid}")
	public int isMyCollect(@Param("openid")String openid,@Param("houseid")String houseid);
	
	@Delete("delete from wx_housecollect where houseid = #{houseid}")
	public void deCollectByHouseid(@Param("houseid")String houseid);
	
}
