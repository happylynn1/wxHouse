package com.shenyingchengxun.house.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shenyingchengxun.action.bean.Agent;
import com.shenyingchengxun.action.bean.Client;
import com.shenyingchengxun.action.bean.StoreInfo;
import com.shenyingchengxun.action.dao.StoreInfoDao;
import com.shenyingchengxun.action.service.AgentService;
import com.shenyingchengxun.action.service.ClientService;
import com.shenyingchengxun.house.bean.ClientHouse;
import com.shenyingchengxun.house.bean.Collect;
import com.shenyingchengxun.house.bean.Comm;
import com.shenyingchengxun.house.bean.HouseSource;
import com.shenyingchengxun.house.bean.Picture;
import com.shenyingchengxun.house.bean.Require;
import com.shenyingchengxun.house.dao.ClientHouseDao;
import com.shenyingchengxun.house.dao.CollectDao;
import com.shenyingchengxun.house.dao.CommDao;
import com.shenyingchengxun.house.dao.HouseSourceDao;
import com.shenyingchengxun.house.dao.HouseTagDao;
import com.shenyingchengxun.house.dao.NotesDao;
import com.shenyingchengxun.house.dao.PictureDao;
import com.shenyingchengxun.util.GaodeUtil;

@Service("HouseSourceServiceImp")
public class HouseSourceServiceImp implements HouseSourceService{

	@Resource HouseTagService houseTagServiceImp;
	@Resource AgentService agentServiceImp;
	@Resource ClientService clientServiceImp;
	@Autowired HouseSourceDao houseSourceDao;
	@Autowired ClientHouseDao clientHouseDao;
	@Autowired CollectDao collectDao;
	@Autowired HouseTagDao houseTagDao;
	@Autowired PictureDao pictureDao;
	@Autowired CommDao commDao;
	@Autowired NotesDao notesDao;
	@Autowired  StoreInfoDao storeInfoDao;
	public String addHouseSource(String storeId,HouseSource hs,String address,String lng,String lat) {
		// TODO Auto-generated method stub
		//先判断该门店下小区是否存在
		Comm comm = commDao.getCommByStoreIdAndComm(storeId, hs.getComm());
		String commid = ""; 
		if(comm==null){
			//根据小区名获取区域街道
			StoreInfo store = storeInfoDao.getStoreInfoByStoreId(storeId);
			Map<String,String> zoneStreetMap = GaodeUtil.getAreaStreetByCityCommName(store.getCity(), hs.getComm());
			String zone = zoneStreetMap.get("zone");
			String street = zoneStreetMap.get("street");
			Comm commNew = new Comm(hs.getComm(),storeId,store.getCity(),zone,street,address,lng,lat);
			commDao.addComm(commNew);
			commid = commNew.getId();
		}else{
			commid = comm.getId();
			String lngs = comm.getLng();
			String lats = comm.getLat();
			if(lngs == null||"".equals(lngs)||lats == null||"".equals(lats)){
				comm.setLng(lng);
				comm.setLat(lat);
				commDao.upComm(comm);
			}
		}
		hs.setCommid(commid);
		
		String role = null;
		// 根据openid判断是经纪人还是客户，然后插入不同表
		Agent ag = agentServiceImp.getAgentByOpenid(hs.getOpenid());
		if(ag!=null){
			//经纪人用户，添加到经纪人房源库
			houseSourceDao.addHouseSource(hs);
			role = "agent";
		} else {
			Client cl = clientServiceImp.getClientByOpenid(hs.getOpenid());
			if(cl!=null){
				// 客户，添加到客户房源库
				houseSourceDao.addClientHouseSource(hs);
				role = "client";
			}
		}
				
		//更新wx_housevideo表houseid
		pictureDao.upVideoByOpenid(hs.getOpenid(),storeId,hs.getHouseid());
		
		//将房源图片表中的openid下的图片update为houseid;
		pictureDao.upPictureByPicName(hs.getPicPath(),hs.getOpenid());
		//将房源图片表中houseid为null的openid下的图片update为houseid;
		pictureDao.upPictureByOpenid(new Picture(hs.getHouseid(), null, null, hs.getOpenid()));
		
		return role;
	}
	@Override
	public void updateHouseSourceByHouseid(String storeId, HouseSource hs,String address,String lng,String lat) {
		// TODO Auto-generated method stub
		//先判断该门店下小区是否存在
		Comm comm = commDao.getCommByStoreIdAndComm(storeId, hs.getComm());
		String commid = ""; 
		if(comm==null){
			//根据小区名获取区域街道
			StoreInfo store = storeInfoDao.getStoreInfoByStoreId(storeId);
			Map<String,String> zoneStreetMap = GaodeUtil.getAreaStreetByCityCommName(store.getCity(), hs.getComm());
			String zone = zoneStreetMap.get("zone");
			String street = zoneStreetMap.get("street");
			Comm commNew = new Comm(hs.getComm(),storeId,store.getCity(),zone,street,address,lng,lat);
			commDao.addComm(commNew);
			commid = commNew.getId();
		}else{
			commid = comm.getId();
			String lngs = comm.getLng();
			String lats = comm.getLat();
			if(lngs == null||"".equals(lngs)||lats == null||"".equals(lats)){
				comm.setLng(lng);
				comm.setLat(lat);
				commDao.upComm(comm);
			}
		}
		hs.setCommid(commid);
		houseSourceDao.upHouseSourceById(hs);
	}
	public HouseSource getHouseSourceByHouseid(String houseid) {
		// TODO Auto-generated method stub
		return houseSourceDao.getHouseSourceByHouseid(houseid);
	}

	public void addClientHouse(ClientHouse ch) {
		// TODO Auto-generated method stub
		clientHouseDao.addClientHouse(ch);
	}

	public List<Map<String,Object>> getMyCollect(String page,String limit,String openid,String type) {
		// TODO Auto-generated method stub
		int _page = 0,_limit = 10;
		if(limit!=null&&!"".equals(limit))_limit = Integer.parseInt(limit);
		if(page!=null&&!"".equals(page))_page = Integer.parseInt(page);
		int _st = _limit*_page,_ed = _limit;
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		List<String> houseidList = collectDao.getMyCollectHouseidListByOpenid(openid,type);
		if(houseidList.size()>0){
			List<String> tagList = houseTagDao.getLikeTagList();
			List<HouseSource> hsList = houseSourceDao.getHouseSourceListByHouseidList(_st,_ed,houseidList);
			for (int i = 0; i < hsList.size(); i++) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("hs", hsList.get(i));
				map.put("pl", pictureDao.getPictureByHouseid(hsList.get(i).getHouseid()));
				map.put("vl", pictureDao.getVideoByHouseid(hsList.get(i).getHouseid()));
				map.put("htl", houseTagServiceImp.getLikeTags(tagList,hsList.get(i)));
				resultList.add(map);
			}
		}
		return resultList;
	}

	public void addMyCollect(String openid, String houseid,String type) {
		// TODO Auto-generated method stub
		collectDao.addMyCollect(new Collect(null, openid, houseid, null,type));
	}

	public void delMyCollect(String openid, String houseid) {
		// TODO Auto-generated method stub
		collectDao.delMyCollect(new Collect(null, openid, houseid, null,null));
	}

	public boolean isMyCollect(String openid, String houseid) {
		// TODO Auto-generated method stub
		return collectDao.isMyCollect(openid,houseid)>0;
	}

	public List<Map<String, Object>> getHouseListByType(String page,String limit,String storeId,String openid,String type,
			String keys, String room, String price, String towards, String genre) {
		// TODO Auto-generated method stub
		int _page = 0,_limit = 10;
		if(limit!=null&&!"".equals(limit))_limit = Integer.parseInt(limit);
		if(page!=null&&!"".equals(page))_page = Integer.parseInt(page);
		int _st = _limit*_page,_ed = _limit;
		List<Map<String, Object>> resultList= new ArrayList<Map<String,Object>>();
		List<HouseSource> hsList = new ArrayList<HouseSource>();
		String priceMin = "",priceMax="";
		if(price!=null&&price.contains("-")){
			priceMin = price.split("-")[0];
			priceMax = price.split("-")[1];
		}
		//房源获取需要获取openid关注的经纪人房源或者isshow为1的
		hsList = houseSourceDao.getHouseListByType(_st,_ed,storeId,openid,type,keys, room, priceMin,priceMax, towards,genre);
		List<String> tagList = houseTagDao.getLikeTagList();
		for (int i = 0; i < hsList.size(); i++) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("hs", hsList.get(i));
			map.put("pl", pictureDao.getPictureByHouseid(hsList.get(i).getHouseid()));
			map.put("vl", pictureDao.getVideoByHouseid(hsList.get(i).getHouseid()));
			map.put("htl", houseTagServiceImp.getLikeTags(tagList,hsList.get(i)));
			resultList.add(map);
		}
		return resultList;
	}
	public List<Map<String, Object>> getHouseListByCommType(String page,String limit,String storeId,String openid,String type,
			String keys) {
		// TODO Auto-generated method stub
		int _page = 0,_limit = 10;
		if(limit!=null&&!"".equals(limit))_limit = Integer.parseInt(limit);
		if(page!=null&&!"".equals(page))_page = Integer.parseInt(page);
		int _st = _limit*_page,_ed = _limit;
		List<Map<String, Object>> resultList= new ArrayList<Map<String,Object>>();
		List<HouseSource> hsList = new ArrayList<HouseSource>();
		//房源获取需要获取openid关注的经纪人房源或者isshow为1的
		hsList = houseSourceDao.getHouseListByCommType(_st,_ed,storeId,openid,type,keys);
		List<String> tagList = houseTagDao.getLikeTagList();
		for (int i = 0; i < hsList.size(); i++) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("hs", hsList.get(i));
			map.put("pl", pictureDao.getPictureByHouseid(hsList.get(i).getHouseid()));
			map.put("vl", pictureDao.getVideoByHouseid(hsList.get(i).getHouseid()));
			map.put("htl", houseTagServiceImp.getLikeTags(tagList,hsList.get(i)));
			resultList.add(map);
		}
		return resultList;
	}
	public List<Comm> getCommList(String page,String limit,String storeId,String openid,String comm) {
		// TODO Auto-generated method stub
		int _page = 0,_limit = 10;
		if(limit!=null&&!"".equals(limit))_limit = Integer.parseInt(limit);
		if(page!=null&&!"".equals(page))_page = Integer.parseInt(page);
		int _st = _limit*_page,_ed = _limit;
		List<Comm> resultList = new ArrayList<Comm>();
		resultList = commDao.getCommList(_st,_ed,storeId,comm,openid);
		return resultList;
	}

	public List<Map<String, Object>> getStoreHotHouseList(String page,String limit,String openid, String storeId) {
		// TODO Auto-generated method stub
		int _page = 0,_limit = 10;
		if(limit!=null&&!"".equals(limit))_limit = Integer.parseInt(limit);
		if(page!=null&&!"".equals(page))_page = Integer.parseInt(page);
		int _st = _limit*_page,_ed = _limit;
		List<Map<String, Object>> resultList= new ArrayList<Map<String,Object>>();
		List<HouseSource> hsList = new ArrayList<HouseSource>();
		hsList = houseSourceDao.getStoreHotHouseList(_st,_ed,storeId,openid);
		List<String> tagList = houseTagDao.getLikeTagList();
		for (int i = 0; i < hsList.size(); i++) {
			List<String> htl = houseTagServiceImp.getLikeTags(tagList,hsList.get(i));
			if(hsList.get(i).getIshot()!=null&&hsList.get(i).getIshot().equals("1")){
				htl.add(0,"热门");
			}else if(hsList.get(i).getIsnew()!=null&&hsList.get(i).getIsnew().equals("1")){
				htl.add(0,"新上");
			}
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("hs", hsList.get(i));
			map.put("pl", pictureDao.getPictureByHouseid(hsList.get(i).getHouseid()));
			map.put("vl", pictureDao.getVideoByHouseid(hsList.get(i).getHouseid()));
			map.put("htl", htl);
			resultList.add(map);
		}
		return resultList;
	}
	
	public int addRequire(Require require){
		// TODO Auto-generated method stub
		return houseSourceDao.addRequire(require);
	}

	@Override
	public List<Map<String, Object>> getMyHouseByType(String page,String limit,String storeId,String openid, String type) {
		// TODO Auto-generated method stub
		int _page = 0,_limit = 10;
		if(limit!=null&&!"".equals(limit))_limit = Integer.parseInt(limit);
		if(page!=null&&!"".equals(page))_page = Integer.parseInt(page);
		int _st = _limit*_page,_ed = _limit;
		List<Map<String, Object>> resultList= new ArrayList<Map<String,Object>>();
		//房源获取需要获取openid关注的经纪人房源或者isshow为1的
		List<HouseSource> hsList = houseSourceDao.getMyHouseByType(_st,_ed,storeId,openid,type);
		List<String> tagList = houseTagDao.getLikeTagList();
		for (int i = 0; i < hsList.size(); i++) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("hs", hsList.get(i));
			map.put("pl", pictureDao.getPictureByHouseid(hsList.get(i).getHouseid()));
			map.put("vl", pictureDao.getVideoByHouseid(hsList.get(i).getHouseid()));
			map.put("htl", houseTagServiceImp.getLikeTags(tagList,hsList.get(i)));
			resultList.add(map);
		}
		return resultList;
	}

	@Override
	public int delMyHouseById(String houseid) {
		// TODO Auto-generated method stub
		int num = houseSourceDao.delMyHouseById(houseid);
		notesDao.delNotesByHouseid(houseid);
		pictureDao.delPictureByHouseid(houseid);
		collectDao.deCollectByHouseid(houseid);
		return num;
	}
	
	@Override
	public String getWechatNameByStoreid(String storeId) {
		// TODO Auto-generated method stub
		return houseSourceDao.getWechatNameByStoreid(storeId);
	}
	@Override
	public void upOtherNotes(String houseid, String type, String content) {
		// TODO Auto-generated method stub
		if("1".equals(type)){
			houseSourceDao.upCentralnotes(houseid,content);
		}else{
			houseSourceDao.upMatingnotes(houseid,content);
		}
	}
	@Override
	public int updateClienthouseByAgentopenid(String houseid, String agentopenid) {
		return houseSourceDao.updateClienthouseByAgentopenid(houseid,agentopenid);
	}
	@Override
	public HouseSource getClientHouseByHouseid(String houseid) {
		// TODO Auto-generated method stub
		return houseSourceDao.getClientHouseByHouseid(houseid);
	}
	@Override
	public void updateClientHouseSourceByHouseid(String storeId,
			HouseSource hs, String address, String lng, String lat) {
		//先判断该门店下小区是否存在
		Comm comm = commDao.getCommByStoreIdAndComm(storeId, hs.getComm());
		String commid = ""; 
		if(comm==null){
			//根据小区名获取区域街道
			StoreInfo store = storeInfoDao.getStoreInfoByStoreId(storeId);
			Map<String,String> zoneStreetMap = GaodeUtil.getAreaStreetByCityCommName(store.getCity(), hs.getComm());
			String zone = zoneStreetMap.get("zone");
			String street = zoneStreetMap.get("street");
			Comm commNew = new Comm(hs.getComm(),storeId,store.getCity(),zone,street,address,lng,lat);
			commDao.addComm(commNew);
			commid = commNew.getId();
		}else{
			commid = comm.getId();
			String lngs = comm.getLng();
			String lats = comm.getLat();
			if(lngs == null||"".equals(lngs)||lats == null||"".equals(lats)){
				comm.setLng(lng);
				comm.setLat(lat);
				commDao.upComm(comm);
			}
		}
		hs.setCommid(commid);
		houseSourceDao.upClientHouseSourceById(hs);
	}
	@Override
	public void copyClientHouseToAgentHouse(String houseid) {
		// TODO Auto-generated method stub
		houseSourceDao.copyClientHouseToAgentHouse(houseid);
	}
	@Override
	public void updateClientHouseIsdeal(String houseid) {
		// TODO Auto-generated method stub
		houseSourceDao.updateClientHouseIsdeal(houseid);
	}
	@Override
	public HouseSource getClientHouseInfoByHouseid(String houseid) {
		// TODO Auto-generated method stub
		return houseSourceDao.getClientHouseInfoByHouseid(houseid);
	}
	@Override
	public int updateRequireAgentByRequireid(String requireid,
			String agent_openid) {
		// TODO Auto-generated method stub
		return houseSourceDao.updateRequireAgentByRequireid(requireid,agent_openid);
	}
	@Override
	public Require getRequireByRequireid(String requireid) {
		// TODO Auto-generated method stub
		return houseSourceDao.getRequireByRequireid(requireid);
	}
}
