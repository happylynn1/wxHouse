package com.shenyingchengxun.house.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.shenyingchengxun.action.util.Config;
import com.shenyingchengxun.house.bean.HouseSource;
import com.shenyingchengxun.util.HttpUtil;

public class WxPostUtil {
	//群发消息接口链接
	public static final String sendNewsUrl  = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN";
	//模板消息发送链接
	public static final String templateUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	//访客来咨询通知 ，模板消息id
	public static final String templateId2 = "s6t7vyrzVlFjWE9ewXze_OB_NU3_wcep-1wuxzSTnSU";
	
	public static Map<String,String> templateMap = new HashMap<String,String>(); 
	static{
		templateMap.put("Store1newHouse", "Nyk40vf4QGtZB3h-P8DUA6Xq-gnfhk_Ot_iKY38Il-8");
		templateMap.put("Store2newHouse", "-rTkSgUgiK5L3vzek0pZdX5N5aLB8tMDLL7ZUX2snxU");
		templateMap.put("Store1newMsg", "s6t7vyrzVlFjWE9ewXze_OB_NU3_wcep-1wuxzSTnSU");
		templateMap.put("Store2newMsg", "_erJxsN-Yox9mfiyOKpp4tOVQ6NQdcZ5qp1CuIuhfKk");
		// 客户委托房源通知
		templateMap.put("Store1entrustMsg", "kkHrhnj-yTYaSqMKFoMzGFRodlJ24hJgXEVC-HW8LbE");
		templateMap.put("Store2entrustMsg", "2T13bstHzRK6STNEyVviPdUpApm_BLoVW0iRb-zUzGg");
		// 求租求购需求提醒
		templateMap.put("Store1requireMsg", "IcEIsRhXLTNY_b5Jnd4ay9GvZ7udYVzjixQlLjgHmks");
		templateMap.put("Store2requireMsg", "5yx5StglRmX3-Iuemd7D7zSoM3UdLEyPTzbyzAd92R8");
	}
	public static JSONObject creatNewHouseTemplate(String storeId,HouseSource hs,String houseid,String openid){
		String url=Config.Base+"action/redirect.do?storeId="+storeId+"&path=page/houseSourceInfo2.do?houseid="+houseid+"&storeId="+storeId+"&openid="+openid;
		String condition=hs.getComm()+" "+hs.getArea()+"㎡  "+hs.getTotal()+("sale".equals(hs.getTrading_type())?"万元":"元/㎡");
		String time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
		Map<String,String> mapFirst  = new HashMap<String,String>();
		mapFirst.put("value", "恭喜！有套新房源符合您的找房条件。");
		mapFirst.put("color", "#173177");
		Map<String,String> mapKey1  = new HashMap<String,String>();
		mapKey1.put("value", houseid);
		mapKey1.put("color", "#173177");
		Map<String,String> mapKey2  = new HashMap<String,String>();
		mapKey2.put("value", condition);
		mapKey2.put("color", "#173177");
		Map<String,String> mapKey3  = new HashMap<String,String>();
		mapKey3.put("value", time);
		mapKey3.put("color", "#173177");
		Map<String,String> mapRemark  = new HashMap<String,String>();
		mapRemark.put("value", "详情");
		mapRemark.put("color", "#173177");
		Map<String,Object> mapData  = new HashMap<String,Object>();
		mapData.put("first", mapFirst);
		mapData.put("keyword1", mapKey1);
		mapData.put("keyword2", mapKey2);
		mapData.put("keyword3", mapKey3);
		mapData.put("remark", mapRemark);
		Map<String,Object> map  = new HashMap<String,Object>();
		map.put("touser", openid);
		map.put("template_id", templateMap.get("Store"+storeId+"newHouse"));
		map.put("data", mapData);
		map.put("color", "#173177");
		map.put("url", url);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	public static JSONObject creatNewMsgTemplate(String storeId,String title,String name,String content,String openid,String url){
		Map<String,String> mapFirst  = new HashMap<String,String>();
		mapFirst.put("value", title);
		mapFirst.put("color", "#173177");
		Map<String,String> mapUser  = new HashMap<String,String>();
		mapUser.put("value", name);
		mapUser.put("color", "#173177");
		Map<String,String> mapAsk  = new HashMap<String,String>();
		mapAsk.put("value", content);
		mapAsk.put("color", "#173177");
		Map<String,String> mapRemark  = new HashMap<String,String>();
		mapRemark.put("value", "详情");
		mapRemark.put("color", "#173177");
		Map<String,Object> mapData  = new HashMap<String,Object>();
		mapData.put("first", mapFirst);
		mapData.put("user", mapUser);
		mapData.put("ask", mapAsk);
		mapData.put("remark", mapRemark);
		Map<String,Object> map  = new HashMap<String,Object>();
		map.put("touser", openid);
		map.put("template_id", templateMap.get("Store"+storeId+"newMsg"));
		map.put("data", mapData);
		map.put("color", "#173177");
		map.put("url", url);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	
	public static JSONObject createEntrustMsgTemplate(String storeId,String comm,String address,String time,String genre,String openid,String url){
		Map<String,String> mapFirst  = new HashMap<String,String>();
		mapFirst.put("value", "您收到一条业主的房源委托。");
		mapFirst.put("color", "#173177");
		Map<String,String> mapComm  = new HashMap<String,String>();
		mapComm.put("value", comm);
		mapComm.put("color", "#173177");
		Map<String,String> mapAddress  = new HashMap<String,String>();
		mapAddress.put("value", address);
		mapAddress.put("color", "#173177");
		Map<String,String> mapTime  = new HashMap<String,String>();
		mapTime.put("value", time);
		mapTime.put("color", "#173177");
		Map<String,String> mapGenre  = new HashMap<String,String>();
		mapGenre.put("value", genre);
		mapGenre.put("color", "#173177");
		Map<String,String> mapRemark  = new HashMap<String,String>();
		mapRemark.put("value", "点击查看详情");
		mapRemark.put("color", "#173177");
		Map<String,Object> mapData  = new HashMap<String,Object>();
		mapData.put("first", mapFirst);
		mapData.put("keyword1", mapComm);
		mapData.put("keyword2", mapAddress);
		mapData.put("keyword3", mapTime);
		mapData.put("keyword4", mapGenre);
		mapData.put("remark", mapRemark);
		Map<String,Object> map  = new HashMap<String,Object>();
		map.put("touser", openid);
		map.put("template_id", templateMap.get("Store"+storeId+"entrustMsg"));
		map.put("data", mapData);
		map.put("color", "#173177");
		map.put("url", url);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	
	public static JSONObject createRequireMsgTemplate(String title,String storeId,String name,String time,String msg,String address,String openid,String url){
		Map<String,String> mapFirst  = new HashMap<String,String>();
		mapFirst.put("value", title);
		mapFirst.put("color", "#173177");
		Map<String,String> mapName  = new HashMap<String,String>();
		mapName.put("value", name);
		mapName.put("color", "#173177");
		Map<String,String> mapTime  = new HashMap<String,String>();
		mapTime.put("value", time);
		mapTime.put("color", "#173177");
		Map<String,String> mapMsg  = new HashMap<String,String>();
		mapMsg.put("value", msg);
		mapMsg.put("color", "#173177");
		Map<String,String> mapAddress  = new HashMap<String,String>();
		mapAddress.put("value", address);
		mapAddress.put("color", "#173177");
		Map<String,String> mapRemark  = new HashMap<String,String>();
		mapRemark.put("value", "点击查看详情");
		mapRemark.put("color", "#173177");
		Map<String,Object> mapData  = new HashMap<String,Object>();
		mapData.put("first", mapFirst);
		mapData.put("keyword1", mapName);
		mapData.put("keyword2", mapTime);
		mapData.put("keyword3", mapMsg);
		mapData.put("keyword4", mapAddress);
		mapData.put("remark", mapRemark);
		Map<String,Object> map  = new HashMap<String,Object>();
		map.put("touser", openid);
		map.put("template_id", templateMap.get("Store"+storeId+"requireMsg"));
		map.put("data", mapData);
		map.put("color", "#173177");
		map.put("url", url);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	
	public static String sendTemplate(String access_token,JSONObject json){
		String urlStr = templateUrl.replace("ACCESS_TOKEN", access_token);
		JSONObject jsons =  HttpUtil.httpRequest(urlStr,"POST",json);
		String errcode =  jsons.getString("errcode"); 
		return errcode;
	}
}
