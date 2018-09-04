package com.shenyingchengxun.house.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shenyingchengxun.action.bean.StoreInfo;
import com.shenyingchengxun.action.dao.StoreInfoDao;
import com.shenyingchengxun.action.util.Config;
import com.shenyingchengxun.house.bean.ClientLike;
import com.shenyingchengxun.house.bean.ClientRecommend;
import com.shenyingchengxun.house.bean.HouseSource;
import com.shenyingchengxun.house.dao.ClientLikeDao;
import com.shenyingchengxun.house.dao.ClientRecommendDao;
import com.shenyingchengxun.house.dao.HouseSourceDao;
import com.shenyingchengxun.house.dao.HouseTagDao;
import com.shenyingchengxun.house.dao.PictureDao;
import com.shenyingchengxun.house.util.WxPostUtil;
@Service("ClientRecommendServiceImp")
public class ClientRecommendServiceImp implements ClientRecommendService{

	@Resource HouseTagService houseTagServiceImp;
	@Autowired PictureDao pictureDao;
	@Autowired HouseTagDao houseTagDao;
	@Autowired StoreInfoDao storeInfoDao;
	@Autowired ClientLikeDao clientLikeDao;
	@Autowired HouseSourceDao houseSourceDao;
	@Autowired ClientRecommendDao clientRecommendDao;
	
	public List<ClientRecommend> getClientRecommendListByOpenid(String houseid,String openid) {
		// TODO Auto-generated method stub
		HouseSource hs = houseSourceDao.getHouseSourceByHouseid(houseid);
		String room = hs.getRoom();
		if(room!=null&&room.contains("室")){
			hs.setRoom(room.split("室")[0]);
		}else{
			hs.setRoom("");
		}
		List<ClientRecommend> clientList = clientRecommendDao.getClientRecommendListByOpenid(openid);
		for (int i = 0; i < clientList.size(); i++) {
			ClientRecommend cr = clientList.get(i);
			if(cr.getComm()!=null&&hs.getComm()!=null&&cr.getComm().contains(hs.getComm())){
				cr.setComm("1");
			}else{
				cr.setComm("0");
			}
			if(cr.getRoom()!=null&&cr.getRoom().contains(hs.getRoom())){
				cr.setRoom("1");
			}else{
				cr.setRoom("0");
			}
			if(hs.getTrading_type().equals("sale")){
				if(cr.getSaleMaxPrice()!=null&&cr.getSaleMinPrice()!=null&&hs.getTotal()!=null&&
						(Integer.parseInt(cr.getSaleMaxPrice())*1.2>=(Integer.parseInt(hs.getTotal())))&&(Integer.parseInt(cr.getSaleMinPrice())*0.8<=(Integer.parseInt(hs.getTotal())))){
					cr.setPrice("1");
				}else{
					cr.setPrice("0");
				}
			}else{
				if(cr.getRentMaxPrice()!=null&&cr.getRentMinPrice()!=null&&hs.getTotal()!=null&&
						(Integer.parseInt(cr.getRentMaxPrice())*1.2>=(Integer.parseInt(hs.getTotal())))&&(Integer.parseInt(cr.getRentMinPrice())*0.8<=(Integer.parseInt(hs.getTotal())))){
					cr.setPrice("1");
				}else{
					cr.setPrice("0");
				}
			}
		}
		return clientList;
	}
	public void batchSend(StoreInfo si,String openid, String houseid,String[] openidArr,boolean flag) {
		// TODO Auto-generated method stub
		HouseSource hs = houseSourceDao.getHouseSourceByHouseid(houseid);
		String errcode = "0";
		for (int i = 0; i < openidArr.length; i++) {
			JSONObject json = WxPostUtil.creatNewHouseTemplate(si.getStoreId(),hs, houseid, openidArr[i]);
			errcode = WxPostUtil.sendTemplate(si.getAccessToken(),json);
		}
		if(errcode.equals("42001")&&flag){
			String accessToken = Config.getAccessToken(si.getAppId(),si.getAppSecret());
			si.setAccessToken(accessToken);
			si.setAtReg_time(new Date().getTime());
			storeInfoDao.upStoreAtByStoreId(si.getStoreId(),accessToken);
			batchSend(si,openid,houseid,openidArr,false);
		}
	}
	/**
	 * 推荐房源
	 * 规则：租房和买房分开推荐，分别根据用户最近浏览的10条记录（没有10条则取全部）为用户推荐相似的房源信息。
	 * 比如在相同的小区、相同的居室、价格上下浮动20%，满足其中一个条件就可以。
	 * 展示的先后顺序依据为：三个条件同时满足的在前，其次是满足两个条件、最后是满足一个条件。
	 */
	public List<Map<String, Object>> getRecommendListByClientOpenid(String page,String limit,String storeId,
			String openid,String type) {
		// TODO Auto-generated method stub
		int _page = 0,_limit = 10;
		if(limit!=null&&!"".equals(limit))_limit = Integer.parseInt(limit);
		if(page!=null&&!"".equals(page))_page = Integer.parseInt(page);
		int _st = _limit*_page,_ed = _limit*(_page+1);
		List<Map<String, Object>> resultList= new ArrayList<Map<String,Object>>();
		ClientLike cl = clientLikeDao.getMyClientLikeByOpneid(openid);
		List<HouseSource> hsList = houseSourceDao.getAllHouseListByStoreId(storeId,openid,type);
		List<String> tagList = houseTagDao.getLikeTagList();
		if(cl==null){
			for (int i = _st; i < Math.min(_ed, hsList.size()); i++) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("hs", hsList.get(i));
				map.put("pl", pictureDao.getPictureByHouseid(hsList.get(i).getHouseid()));
				map.put("htl", houseTagServiceImp.getLikeTags(tagList,hsList.get(i)));
				resultList.add(map);
			}
			return resultList;
		}else{
			List<Map<String, Object>> resultList3= new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> resultList2= new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> resultList1= new ArrayList<Map<String,Object>>();
			for (int i = 0; i < hsList.size(); i++) {
				boolean flagComm = cl.getComm()!=null&&hsList.get(i).getComm()!=null&&cl.getComm().contains(hsList.get(i).getComm()); 
				boolean flagRoom = cl.getRoom()!=null&&hsList.get(i).getRoom()!=null&&cl.getRoom().contains(hsList.get(i).getRoom()); 
				boolean flagSalePrice = cl.getSaleMaxPrice()!=null&&cl.getSaleMinPrice()!=null&&hsList.get(i).getTotal()!=null
						&&Double.parseDouble(hsList.get(i).getTotal())>=((Double.parseDouble(cl.getSaleMinPrice()))*0.8)
						&&Double.parseDouble(hsList.get(i).getTotal())<=((Double.parseDouble(cl.getSaleMaxPrice()))*1.2);
				boolean flagRentPrice = cl.getRentMaxPrice()!=null&&cl.getRentMinPrice()!=null&&hsList.get(i).getTotal()!=null
						&&Double.parseDouble(hsList.get(i).getTotal())>=((Double.parseDouble(cl.getRentMinPrice()))*0.8)
						&&Double.parseDouble(hsList.get(i).getTotal())<=((Double.parseDouble(cl.getRentMaxPrice()))*1.2);
				if("sale".equals(type)){
					if(flagComm&&flagRoom&&flagSalePrice){
						Map<String,Object> map = new HashMap<String,Object>();
						map.put("hs", hsList.get(i));
						map.put("pl", pictureDao.getPictureByHouseid(hsList.get(i).getHouseid()));
						map.put("htl", houseTagServiceImp.getLikeTags(tagList,hsList.get(i)));
						resultList3.add(map);
					}else if((flagComm&&flagRoom&&!flagSalePrice)||(flagComm&&!flagRoom&&flagSalePrice)||(!flagComm&&flagRoom&&flagSalePrice)){
						Map<String,Object> map = new HashMap<String,Object>();
						map.put("hs", hsList.get(i));
						map.put("pl", pictureDao.getPictureByHouseid(hsList.get(i).getHouseid()));
						map.put("htl", houseTagServiceImp.getLikeTags(tagList,hsList.get(i)));
						resultList2.add(map);
					}else if((flagComm&&!flagRoom&&!flagSalePrice)||(!flagComm&&flagRoom&&!flagSalePrice)||(!flagComm&&!flagRoom&&flagSalePrice)){
						Map<String,Object> map = new HashMap<String,Object>();
						map.put("hs", hsList.get(i));
						map.put("pl", pictureDao.getPictureByHouseid(hsList.get(i).getHouseid()));
						map.put("htl", houseTagServiceImp.getLikeTags(tagList,hsList.get(i)));
						resultList1.add(map);
					}
				}else{
					if(flagComm&&flagRoom&&flagRentPrice){
						Map<String,Object> map = new HashMap<String,Object>();
						map.put("hs", hsList.get(i));
						map.put("pl", pictureDao.getPictureByHouseid(hsList.get(i).getHouseid()));
						map.put("htl", houseTagServiceImp.getLikeTags(tagList,hsList.get(i)));
						resultList3.add(map);
					}else if((flagComm&&flagRoom&&!flagRentPrice)||(flagComm&&!flagRoom&&flagRentPrice)||(!flagComm&&flagRoom&&flagRentPrice)){
						Map<String,Object> map = new HashMap<String,Object>();
						map.put("hs", hsList.get(i));
						map.put("pl", pictureDao.getPictureByHouseid(hsList.get(i).getHouseid()));
						map.put("htl", houseTagServiceImp.getLikeTags(tagList,hsList.get(i)));
						resultList2.add(map);
					}else if((flagComm&&!flagRoom&&!flagRentPrice)||(!flagComm&&flagRoom&&!flagRentPrice)||(!flagComm&&!flagRoom&&flagRentPrice)){
						Map<String,Object> map = new HashMap<String,Object>();
						map.put("hs", hsList.get(i));
						map.put("pl", pictureDao.getPictureByHouseid(hsList.get(i).getHouseid()));
						map.put("htl", houseTagServiceImp.getLikeTags(tagList,hsList.get(i)));
						resultList1.add(map);
					}
				}
			}
			for (int j = 0; j < resultList3.size(); j++) {
				resultList.add(resultList3.get(j));
			}
			for (int j = 0; j < resultList2.size(); j++) {
				resultList.add(resultList2.get(j));
			}
			for (int j = 0; j < resultList1.size(); j++) {
				resultList.add(resultList1.get(j));
			}
		}
		return resultList.subList(_st, Math.min(_ed,resultList.size()));
	}
}
