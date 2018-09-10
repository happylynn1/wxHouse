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
		// 房源发布成功通知
		templateMap.put("Store1newHouse", "Nyk40vf4QGtZB3h-P8DUA6Xq-gnfhk_Ot_iKY38Il-8");
		templateMap.put("Store2newHouse", "-rTkSgUgiK5L3vzek0pZdX5N5aLB8tMDLL7ZUX2snxU");
		// 访客来咨询通知 
		templateMap.put("Store1newMsg", "s6t7vyrzVlFjWE9ewXze_OB_NU3_wcep-1wuxzSTnSU");
		templateMap.put("Store2newMsg", "_erJxsN-Yox9mfiyOKpp4tOVQ6NQdcZ5qp1CuIuhfKk");
		// 客户委托房源通知
		templateMap.put("Store1entrustMsg", "kkHrhnj-yTYaSqMKFoMzGFRodlJ24hJgXEVC-HW8LbE");
		templateMap.put("Store2entrustMsg", "2T13bstHzRK6STNEyVviPdUpApm_BLoVW0iRb-zUzGg");
		// 求租求购需求提醒
		templateMap.put("Store1requireMsg", "IcEIsRhXLTNY_b5Jnd4ay9GvZ7udYVzjixQlLjgHmks");
		templateMap.put("Store2requireMsg", "5yx5StglRmX3-Iuemd7D7zSoM3UdLEyPTzbyzAd92R8");
		// 预约看房提醒 
		templateMap.put("Store1lookHouseMsg", "DuiRxz0sMdKlrxloidtvyU9oY6lqTzQOaTY0FGLXTyk");
		templateMap.put("Store2lookHouseMsg", "9x02enUOQrLKa2k9EqKq3ixIe6rkKKL8gebJX8HwETI");
		// 预约看房取消提醒 
		templateMap.put("Store1CancellookHouseMsg", "PAh4L68avZaeLei8iCIPjKuX59sIfIzg1tsSrXxcfA0");
		templateMap.put("Store2CancellookHouseMsg", "9x02enUOQrLKa2k9EqKq3ixIe6rkKKL8gebJX8HwETI");
	}
	public static JSONObject creatNewHouseTemplate(String storeId,HouseSource hs,String houseid,String openid){
		String url=Config.Base+"action/redirect.do?storeId="+storeId+"&path=page/houseSourceInfo2.do?houseid="+houseid+"&storeId="+storeId+"&openid="+openid;
		String condition=hs.getComm()+" "+hs.getArea()+"㎡  "+hs.getTotal()+("sale".equals(hs.getTrading_type())?"万元":"元/㎡");
		String time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
		Map<String,String> columnMap = new HashMap<String,String>();
		columnMap.put("first", "恭喜！有套新房源符合您的找房条件。");
		columnMap.put("keyword1", houseid);
		columnMap.put("keyword2", condition);
		columnMap.put("keyword3", time);
		columnMap.put("remark",  "详情");
		Map<String,Object> dataMap = CreateDataMap(columnMap);
		Map<String,Object> map  = new HashMap<String,Object>();
		map.put("touser", openid);
		map.put("template_id", templateMap.get("Store"+storeId+"newHouse"));
		map.put("data", dataMap);
		map.put("color", "#173177");
		map.put("url", url);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	public static JSONObject creatNewMsgTemplate(String storeId,String title,String name,String content,String openid,String url){
		Map<String,String> columnMap = new HashMap<String,String>();
		columnMap.put("first", title);
		columnMap.put("user", name);
		columnMap.put("ask", content);
		columnMap.put("remark", "详情");
		Map<String,Object> dataMap = CreateDataMap(columnMap);
		Map<String,Object> map  = new HashMap<String,Object>();
		map.put("touser", openid);
		map.put("template_id", templateMap.get("Store"+storeId+"newMsg"));
		map.put("data", dataMap);
		map.put("color", "#173177");
		map.put("url", url);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	
	public static JSONObject createEntrustMsgTemplate(String storeId,String comm,String address,String time,String genre,String openid,String url){
		Map<String,String> columnMap = new HashMap<String,String>();
		columnMap.put("first", "您收到一条业主的房源委托。");
		columnMap.put("keyword1", comm);
		columnMap.put("keyword2", address);
		columnMap.put("keyword3", time);
		columnMap.put("keyword4", genre);
		columnMap.put("remark", "点击查看详情");
		Map<String,Object> dataMap = CreateDataMap(columnMap);
		Map<String,Object> map  = new HashMap<String,Object>();
		map.put("touser", openid);
		map.put("template_id", templateMap.get("Store"+storeId+"entrustMsg"));
		map.put("data", dataMap);
		map.put("color", "#173177");
		map.put("url", url);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	
	public static JSONObject createRequireMsgTemplate(String title,String storeId,String name,String time,String msg,String address,String openid,String url){
		Map<String,String> columnMap = new HashMap<String,String>();
		columnMap.put("first", title);
		columnMap.put("keyword1", name);
		columnMap.put("keyword2", time);
		columnMap.put("keyword3", msg);
		columnMap.put("keyword4", address);
		columnMap.put("remark", "点击查看详情");
		Map<String,Object> dataMap = CreateDataMap(columnMap);
		Map<String,Object> map  = new HashMap<String,Object>();
		map.put("touser", openid);
		map.put("template_id", templateMap.get("Store"+storeId+"requireMsg"));
		map.put("data", dataMap);
		map.put("color", "#173177");
		map.put("url", url);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	public static JSONObject createLookHouseMsgTemplate(String title,String storeId,String name,String phone,String time,String houseInfo,String openid,String url){
		Map<String,String> columnMap = new HashMap<String,String>();
		columnMap.put("first", title);
		columnMap.put("keyword1", name);
		columnMap.put("keyword2", phone);
		columnMap.put("keyword3", time);
		columnMap.put("keyword4", houseInfo);
		columnMap.put("remark", "点击查看详情");
		Map<String,Object> dataMap = CreateDataMap(columnMap);
		Map<String,Object> map  = new HashMap<String,Object>();
		map.put("touser", openid);
		map.put("template_id", templateMap.get("Store"+storeId+"lookHouseMsg"));
		map.put("data", dataMap);
		map.put("color", "#173177");
		map.put("url", url);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	public static JSONObject createCancelLookHouseMsgTemplate(String title,String storeId,String time,String houseInfo,String reason,String toOpenid,String url){
		Map<String,String> columnMap = new HashMap<String,String>();
		columnMap.put("first", title);
		columnMap.put("keyword1", houseInfo);
		columnMap.put("keyword2", time);
		columnMap.put("keyword3", reason);
		columnMap.put("remark", "点击查看详情");
		Map<String,Object> dataMap = CreateDataMap(columnMap);
		Map<String,Object> map  = new HashMap<String,Object>();
		map.put("touser", toOpenid);
		map.put("template_id", templateMap.get("Store"+storeId+"CancellookHouseMsg"));
		map.put("data", dataMap);
		map.put("color", "#173177");
		map.put("url", url);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	public static Map<String,Object> CreateDataMap(Map<String,String> columnMap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		for (String key :columnMap.keySet()) {
			Map<String,String> map = new HashMap<String,String>();
			map.put("value", columnMap.get(key));
			map.put("color", "#173177");
			dataMap.put(key,map);
		}
		return dataMap;
	} 
	public static String sendTemplate(String access_token,JSONObject json){
		String urlStr = templateUrl.replace("ACCESS_TOKEN", access_token);
		JSONObject jsons =  HttpUtil.httpRequest(urlStr,"POST",json);
		String errcode =  jsons.getString("errcode"); 
		return errcode;
	}
}
