package com.shenyingchengxun.action.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shenyingchengxun.action.bean.Agent;
import com.shenyingchengxun.action.bean.Client;
import com.shenyingchengxun.util.HttpUtil;

/** 
 * 公众平台通用接口工具类 
 *  
 * @date 2018-04-12 
 */  
public class Config {
	
	private static Logger log = LoggerFactory.getLogger(Config.class);
	// 当前根目录
	public static final String Base = "http://wxmp.jjr580.com/wxHouse/";
	//后台管理图片路径
	public static final String WebFileBase = "http://wx.jjr580.com/action/files/";
    // 获取access_token的接口地址（GET） 限200（次/天）  
    public static final String Access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";  
    // 创建菜单URL（POST） 限100（次/天）
    public static final String Menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";  
    //获取用户信息的url
    public static final String Get_userinfo_url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
    //根据用户ticket获取二维码Url
    public static final String Qrcode_create_url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=ticket";  
    /** 
     * 获取access_token 
     * @param appid 凭证 
     * @param appsecret 密钥 
     * @return 
     */  
    public static void main(String[] args) {
		System.out.println(getAccessToken("wx852b69e0da804848","bc996edbd135f535946d776a7329b66d"));
	}
    public static String getAccessToken(String Appid, String Appsecret) { 
    	System.out.println("获取AccessToken---------------------------");
        String accessToken = "";
    	String requestUrl = Access_token_url.replace("APPID", Appid).replace("APPSECRET", Appsecret);  
        JSONObject jsonObject = HttpUtil.httpRequest(requestUrl, "GET", null); 
        System.out.println("请求结果-->"+jsonObject);
        try {
            accessToken  =jsonObject.getString("access_token");  
        } catch (Exception e) {
            accessToken = null;
            log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
        }
        return accessToken;
    }
    /**
     * 用于获取微信用户的信息
     * @param opendID
     * @return
     */
    public static Client getUserInfo(String storeid,String opendID,String AppId,String AppSecret){
    	String token = getAccessToken(AppId,AppSecret);
        String url = Get_userinfo_url.replace("ACCESS_TOKEN" , token);
        url = url.replace("OPENID" ,opendID);
        JSONObject jsonObject = HttpUtil.httpRequest(url, "GET", null);
        Iterator<?> keyIter = jsonObject.keys();         
        String key;         
        Object value;
        Map<String,Object> valueMap = new HashMap<String,Object>();         
        while (keyIter.hasNext()) {         
            key = (String) keyIter.next();         
            value = jsonObject.get(key);         
            valueMap.put(key, value);         
        }
        String nickname=null,gender=null,city=null,headImage=null;
        if(valueMap.containsKey("nickname")){
        	nickname = valueMap.get("nickname").toString();
        }
        if(valueMap.containsKey("sex")){
        	gender = valueMap.get("sex").toString();
        }
        if(valueMap.containsKey("city")){
        	city = valueMap.get("city").toString();
        }
        if(valueMap.containsKey("headimgurl")){
        	headImage = valueMap.get("headimgurl").toString();
        }
        return new Client(storeid,opendID, nickname, gender, city, headImage, "");
    }
    /**
     * 用于获取微信用户的信息
     * @param opendID
     * @return
     */
    public static Agent getAgentInfo(String storeid,String opendID,String AppId,String AppSecret){
    	String token = getAccessToken(AppId,AppSecret);
    	String url = Get_userinfo_url.replace("ACCESS_TOKEN" , token);
    	url = url.replace("OPENID" ,opendID);
    	JSONObject jsonObject = HttpUtil.httpRequest(url, "GET", null);
    	Iterator<?> keyIter = jsonObject.keys();         
    	String key;         
    	Object value;
    	Map<String,Object> valueMap = new HashMap<String,Object>();         
    	while (keyIter.hasNext()) {         
    		key = (String) keyIter.next();         
    		value = jsonObject.get(key);         
    		valueMap.put(key, value);         
    	}
    	String nickname=null,gender=null,city=null,headImage=null;
    	if(valueMap.containsKey("nickname")){
    		nickname = valueMap.get("nickname").toString();
    	}
    	if(valueMap.containsKey("sex")){
    		gender = valueMap.get("sex").toString();
    	}
    	if(valueMap.containsKey("city")){
    		city = valueMap.get("city").toString();
    	}
    	if(valueMap.containsKey("headimgurl")){
    		headImage = valueMap.get("headimgurl").toString();
    	}
    	return new Agent(opendID, nickname, gender, city, "", storeid, "", headImage, "", "", "", "", "", "", "", "", "", "");
    }
}