package com.shenyingchengxun.house.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.shenyingchengxun.house.bean.HouseSource;
import com.shenyingchengxun.house.bean.Require;
public interface HouseSourceDao {
	
	public void addHouseSource(@Param("hs")HouseSource hs);

	public void upHouseSourceById(@Param("hs")HouseSource hs);
	
	public HouseSource getHouseSourceByHouseid(@Param("houseid")String houseid);

	public List<HouseSource> getHouseSourceListByHouseidList(@Param("_st")int _st,@Param("_ed")int _ed,@Param("houseidList")List<String> houseidList);

	public List<HouseSource> getAllHouseListByStoreId(@Param("storeId")String storeId,@Param("openid")String openid,@Param("type")String type);

	public List<HouseSource> getHouseListByType(@Param("_st")int _st,@Param("_ed")int _ed,@Param("storeId")String storeId,@Param("openid")String openid,@Param("type")String type,@Param("keys")String keys,@Param("room")String  room,@Param("priceMin")String priceMin,@Param("priceMax")String priceMax,@Param("towards")String  towards,@Param("genre")String genre);

	public List<HouseSource> getMyHouseByType(@Param("_st")int _st,@Param("_ed")int _ed,@Param("storeId")String storeId,@Param("openid")String openid,@Param("type")String type);
	
	public int delMyHouseById(@Param("houseid")String houseid);
	
	public List<HouseSource> getHouseListByCommType(@Param("_st")int _st,@Param("_ed")int _ed,@Param("storeId")String storeId,@Param("openid")String openid,@Param("type")String type,@Param("keys")String keys);
	
	public List<HouseSource> getStoreHotHouseList(@Param("_st")int _st,@Param("_ed")int _ed,@Param("storeId")String storeId,@Param("openid")String openid);
	
	public int addRequire(@Param("require")Require require);

	@Select("SELECT wechatname FROM wx_storeinfo WHERE storeId = #{storeId}")
	public String getWechatNameByStoreid(@Param("storeId")String storeId);
	
	@Update("update wx_housesource set centralnotes = #{content},centralnotesTime =now() where houseid=#{houseid}")
	public void upCentralnotes(@Param("houseid")String houseid,@Param("content")String content);
	
	@Update("update wx_housesource set matingnotes = #{content},matingnotesTime =now() where houseid=#{houseid}")
	public void upMatingnotes(@Param("houseid")String houseid,@Param("content")String content);

	public void addClientHouseSource(@Param("hs")HouseSource hs);

	@Update("UPDATE `wx_clienthouse1` SET agent_openid = #{agentopenid} WHERE houseid = #{houseid}")
	public int updateClienthouseByAgentopenid(@Param("houseid")String houseid, @Param("agentopenid")String agentopenid);

	public HouseSource getClientHouseByHouseid(@Param("houseid")String houseid);

	public void upClientHouseSourceById(@Param("hs")HouseSource hs);

	public void copyClientHouseToAgentHouse(@Param("houseid")String houseid);

	public void updateClientHouseIsdeal(@Param("houseid")String houseid);

	public HouseSource getClientHouseInfoByHouseid(@Param("houseid")String houseid);

	public int updateRequireAgentByRequireid(@Param("requireid")String requireid,@Param("agent_openid")String agent_openid);

	public Require getRequireByRequireid(@Param("requireid")String requireid);

}
