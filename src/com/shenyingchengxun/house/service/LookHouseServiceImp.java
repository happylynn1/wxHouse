package com.shenyingchengxun.house.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shenyingchengxun.action.bean.Agent;
import com.shenyingchengxun.action.bean.StoreInfo;
import com.shenyingchengxun.action.dao.AgentDao;
import com.shenyingchengxun.action.service.StoreInfoService;
import com.shenyingchengxun.action.util.Config;
import com.shenyingchengxun.house.bean.HouseSource;
import com.shenyingchengxun.house.bean.LookHouse;
import com.shenyingchengxun.house.dao.HouseSourceDao;
import com.shenyingchengxun.house.dao.LookHouseDao;
import com.shenyingchengxun.house.util.WxPostUtil;
@Service("LookHouseServiceImp")
public class LookHouseServiceImp implements LookHouseService{

	@Resource StoreInfoService storeInfoServiceImp;
	@Autowired LookHouseDao lookHouseDao;
	@Autowired AgentDao agentDao;
	@Autowired HouseSourceDao houseSourceDao;
	
	@Override
	public Map<String, String> addLookHouse(final String storeId, String openid,
			String name, String phone, String time, String houseid) {
		// TODO Auto-generated method stub
		Map<String,String> map = new HashMap<String,String>();
		int num = 0;
		String msg = "";
		boolean success = true;
		if(storeId==null||"".equals(storeId)){
			success = false;
			msg = "门店id未获取到";
		}
		if(openid==null||"".equals(openid)){
			success = false;
			msg = "openid未获取到";
		}
		if(success){
			LookHouse en = new LookHouse("", storeId, openid, houseid, name, phone, time, null, "0", "", "", "0", "0");
			num = lookHouseDao.addLookHouse(en);
			if(num==1){
				//发送模板
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateString = formatter.format(new Date());
				StoreInfo si = storeInfoServiceImp.getStoreInfoByStoreId(storeId);
				HouseSource  hs = houseSourceDao.getHouseSourceByHouseid(houseid);
				String url=Config.Base+"page/toLookHouseList.do?storeId="+storeId+"&openid="+hs.getOpenid()+"&id="+en.getId();
				String houseInfo = hs.getComm()+"  "+ hs.getRoom().charAt(0)+"室"+ hs.getRoom().charAt(1)+"厅"+ hs.getRoom().charAt(3)+"卫";
				JSONObject json = WxPostUtil.createLookHouseMsgTemplate("有新的客户预约看房，请尽快安排回访。", storeId, name, phone, dateString, houseInfo, hs.getOpenid(), url);
				String code = WxPostUtil.sendTemplate(si.getAccessToken(),json);	
				System.out.println("-----------------------------"+(code.equals("0")?"预约看房模板发送成功":"预约看房模板发送失败")+":"+code+"----------------------------------");
			}
		}
		map.put("success", num+"");
		map.put("msg", msg);
		return map;
	}

	@Override
	public Map<String,Object> getLookHouseList(String page,String limit,String openid) {
		// TODO Auto-generated method stub
		int _page = 0,_limit = 10;
		if(limit!=null&&!"".equals(limit))_limit = Integer.parseInt(limit);
		if(page!=null&&!"".equals(page))_page = Integer.parseInt(page);
		int _st = _limit*_page,_ed = _limit;
		Map<String,Object> map  = new HashMap<String,Object>();
		Agent agent = agentDao.getAgentByOpenid(openid);
		List<Map<String,String>> list  =new ArrayList<Map<String,String>>();
		if(agent==null){
			map.put("isAgent", "0");
			list = lookHouseDao.getLookHouseListByClient(_st,_ed,openid);
		}else{
			map.put("isAgent", "1");
			list = lookHouseDao.getLookHouseListByAgent(_st,_ed,openid);
		}
		map.put("list", list);
		return map;
	}

	@Override
	public int delLookHouse(String id, String isAgent) {
		// TODO Auto-generated method stub
		int num = 0;
		if("1".equals(isAgent)){
			num = lookHouseDao.delByAgent(id);
		}else{
			num = lookHouseDao.delByClient(id);
		}
		return num;
	}

	@Override
	public int cancelLookHouse(String id,String storeId, String openid,String toOpenid,String isAgent, String reason) {
		// TODO Auto-generated method stub
		int num = lookHouseDao.cancelLookHouse(id,openid,reason);
		if(num==1){
			//发送模板
			String url=Config.Base+"page/lookHouseInfo.do?storeId="+storeId+"&openid="+toOpenid+"&id="+id;
			StoreInfo si = storeInfoServiceImp.getStoreInfoByStoreId(storeId);
			LookHouse lh = lookHouseDao.getLookHouseById(id); 
			HouseSource  hs = houseSourceDao.getHouseSourceByHouseid(lh.getHouseid());
			String houseInfo = hs.getComm()+"  "+ hs.getRoom().charAt(0)+"室"+ hs.getRoom().charAt(1)+"厅"+ hs.getRoom().charAt(3)+"卫";
			String title="1".equals(isAgent)?"您好，您的看房预约被取消了！":"您好，有客户取消了看房预约！";
			JSONObject json = WxPostUtil.createCancelLookHouseMsgTemplate(title, storeId, lh.getTime().substring(0,16),houseInfo,reason,toOpenid, url);
			String code = WxPostUtil.sendTemplate(si.getAccessToken(),json);	
			System.out.println("-----------------------------"+(code.equals("0")?"取消预约看房模板发送成功":"取消委托模板发送失败")+":"+code+"----------------------------------");
		}
		return num;
	}

	@Override
	public Map<String, String> getLookHouseInfo(String id, String openid,
			String storeId) {
		// TODO Auto-generated method stub
		Map<String,String> result = new HashMap<String,String>();
		LookHouse lh = lookHouseDao.getLookHouseById(id);
		HouseSource  hs = houseSourceDao.getHouseSourceByHouseid(lh.getHouseid());
		String houseInfo = hs.getComm()+"  "+ hs.getRoom().charAt(0)+"室"+ hs.getRoom().charAt(1)+"厅"+ hs.getRoom().charAt(3)+"卫";
		result.put("houseid", lh.getHouseid());
		result.put("houseInfo", houseInfo);
		result.put("time", lh.getTime().substring(0,16));
		result.put("reason", lh.getReason());
		if(openid.equals(lh.getOpenid())){
			Agent agent = agentDao.getAgentByOpenid(hs.getOpenid());
			result.put("phone", agent.getPhone());
		}else{
			result.put("phone", lh.getPhone());
		}
		return result;
	}
}
