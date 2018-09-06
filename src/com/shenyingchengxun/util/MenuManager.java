package com.shenyingchengxun.util;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shenyingchengxun.action.bean.AccessToken;
import com.shenyingchengxun.action.bean.Button;
import com.shenyingchengxun.action.bean.ComplexButton;
import com.shenyingchengxun.action.bean.Menu;
import com.shenyingchengxun.action.bean.ViewButton;
import com.shenyingchengxun.action.util.Config;

public class MenuManager {
	
	//access_token
	//保证最新    位置在数据库中wx_storeinfo表中，如果为空或过期，请在手机微信中操作一下即可更新
	public static final String access_token = "13_Nc1ewN3jLRGeXuWNF7Lb3733QYidJcQ_S1BnGw_W4L6bXuBIrcGhFmcSHAcEH4UGmN4FE7p8soHi4G3foofRD79WmcjxZZRM2DcsIxT0X7YaYgy-qXANsel4JSesq8yFbJ0LRiPJ7GRxeNeLLNNgADAXZZ";
    // 获取access_token的接口地址（GET） 限200（次/天）  
    public static final String Access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";  
    // 创建菜单URL（POST） 限100（次/天）
    public static final String Menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";  
    
	private static Logger log = LoggerFactory.getLogger(MenuManager.class);

	public static void main(String[] args) {
		int result = createMenu(getMenu(), access_token);
		// 判断菜单创建结果
		if (0 == result) {
			System.out.println("菜单创建成功！");
			log.info("菜单创建成功！");
		} else {
			System.out.println("菜单创建失败，错误码：" + result);
			log.warn("菜单创建失败，错误码：" + result);
		}	
	}
	/** 
     * 创建菜单 
     *  
     * @param menu 菜单实例 
     * @param accessToken 有效的access_token
     * @return 0表示成功，其他值表示失败 
     */  
    public static int createMenu(Menu menu, String AccessToken) {  
        int result = 0;  
      
        // 拼装创建菜单的url  
        String url = Menu_create_url.replace("ACCESS_TOKEN", AccessToken);  
        // 将菜单对象转换成json字符串  
        JSONObject jsonMenu = JSONObject.fromObject(menu);  
        // 调用接口创建菜单
        JSONObject jsonObject = HttpUtil.httpRequest(url, "POST", jsonMenu);  
      
        if (null != jsonObject) {  
            if (0 != jsonObject.getInt("errcode")) {
                result = jsonObject.getInt("errcode"); 
                log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
            }  
        }  
      
        return result;  
    }  
    /** 
     * 获取access_token 
     *  
     * @param appid 凭证 
     * @param appsecret 密钥 
     * @return 
     */  
    public static AccessToken getAccessToken(String Appid, String Appsecret) {  
        AccessToken accessToken = null;  
      
        String requestUrl = Access_token_url.replace("APPID", Appid).replace("APPSECRET", Appsecret);  
        JSONObject jsonObject = HttpUtil.httpRequest(requestUrl, "GET", null);  
        System.out.println(jsonObject);
        // 如果请求成功  
        if (null != jsonObject) {
            try {
                accessToken = new AccessToken();  
                accessToken.setToken(jsonObject.getString("access_token"));  
                accessToken.setExpiresIn(jsonObject.getInt("expires_in"));  
            } catch (JSONException e) {
                accessToken = null;
                // 获取token失败
                log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
            }
        }
        return accessToken;
    }
	/**
	 * 组装菜单数据
	 * 
	 * @return
	 */
	private static Menu getMenu() {
		ViewButton btn21 = new ViewButton();
		btn21.setName("小区信息");
		btn21.setType("view");
		btn21.setUrl(Config.Base+"action/redirect.do?storeId=1&path=page/toComm.do");

		ViewButton btn22 = new ViewButton();
		btn22.setName("出租房源");
		btn22.setType("view");
		btn22.setUrl(Config.Base+"action/redirect.do?storeId=1&path=page/toRent.do");
		
		ViewButton btn23 = new ViewButton();
		btn23.setName("出售房源");
		btn23.setType("view");
		btn23.setUrl(Config.Base+"action/redirect.do?storeId=1&path=page/toSale.do");
		
		ViewButton btn24 = new ViewButton();
		btn24.setName("我要租房");
		btn24.setType("view");
		btn24.setUrl(Config.Base+"action/redirect.do?storeId=1&path=page/toRenting.do");
		
		ViewButton btn25 = new ViewButton();
		btn25.setName("我要买房");
		btn25.setType("view");
		btn25.setUrl(Config.Base+"action/redirect.do?storeId=1&path=page/toBuy.do");


		ViewButton btn31 = new ViewButton();
		btn31.setName("我的房源");
		btn31.setType("view");
		btn31.setUrl(Config.Base+"action/redirect.do?storeId=1&path=page/toMyHouse.do");
		

		ViewButton btn32 = new ViewButton();
		btn32.setName("沟通记录");
		btn32.setType("view");
		btn32.setUrl(Config.Base+"action/redirect.do?storeId=1&path=page/toMyAgent.do");

		ViewButton btn33 = new ViewButton();
		btn33.setName("推荐房源");
		btn33.setType("view");
		btn33.setUrl(Config.Base+"action/redirect.do?storeId=1&path=page/toCommend.do");

		ViewButton btn34 = new ViewButton();
		btn34.setName("预约看房");
		btn34.setType("view");
		btn34.setUrl(Config.Base+"action/redirect.do?storeId=1&path=page/toLookHouseList.do");
		
		ViewButton btn35 = new ViewButton();
		btn35.setName("房源委托");
		btn35.setType("view");
		btn35.setUrl(Config.Base+"action/redirect.do?storeId=1&path=page/toSelect.do");

		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("店铺热点");
		mainBtn1.setType("view");
		mainBtn1.setUrl(Config.Base+"action/redirect.do?storeId=1&path=page/toStoreHot.do");
		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("房源查看");
		mainBtn2.setSub_button(new Button[] { btn25,btn24,btn23,btn22, btn21 });
		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("信息管理");
		mainBtn3.setSub_button(new Button[] { btn35,btn34, btn33, btn32, btn31 });
		
		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });
		return menu;
	}
	
	/**
	 * 组装菜单数据--万管家
	 * 
	 * @return
	 */
	private static Menu getMenu1() {
		ViewButton btn11 = new ViewButton();
		btn11.setName("降价急售房");
		btn11.setType("view");
		btn11.setUrl(Config.Base+"action/redirect.do?storeId=2&path=page/toSale.do?genre=recommend");		

		ViewButton btn12 = new ViewButton();
		btn12.setName("精品二手房");
		btn12.setType("view");
		btn12.setUrl(Config.Base+"action/redirect.do?storeId=2&path=page/toSecondH.do");
		
		ViewButton btn13 = new ViewButton();
		btn13.setName("重庆新房");
		btn13.setType("view");
		btn13.setUrl(Config.Base+"action/redirect.do?storeId=2&path=page/toSale.do?genre=new");
		
		ViewButton btn14 = new ViewButton();
		btn14.setName("优质出租房");
		btn14.setType("view");
		btn14.setUrl(Config.Base+"action/redirect.do?storeId=2&path=page/toRent.do");
		
		ViewButton btn15 = new ViewButton();
		btn15.setName("我的房源");
		btn15.setType("view");
		btn15.setUrl(Config.Base+"action/redirect.do?storeId=2&path=page/toMyHouse.do");

		
		ViewButton btn21 = new ViewButton();
		btn21.setName("我要租房");
		btn21.setType("view");
		btn21.setUrl(Config.Base+"action/redirect.do?storeId=2&path=page/toRenting.do");

		ViewButton btn22 = new ViewButton();
		btn22.setName("我要买房");
		btn22.setType("view");
		btn22.setUrl(Config.Base+"action/redirect.do?storeId=2&path=page/toBuy.do");
		
		ViewButton btn23 = new ViewButton();
		btn23.setName("我要出租");
		btn23.setType("view");
		btn23.setUrl(Config.Base+"action/redirect.do?storeId=2&path=page/sellr.do?type=rent");

		ViewButton btn24 = new ViewButton();
		btn24.setName("我要卖房");
		btn24.setType("view");
		btn24.setUrl(Config.Base+"action/redirect.do?storeId=2&path=page/sellr.do?type=sale");

		ViewButton btn25 = new ViewButton();
		btn25.setName("联系顾问");
		btn25.setType("view");
		btn25.setUrl(Config.Base+"action/redirect.do?storeId=2&path=page/toMyAgent.do");
		

		ViewButton btn31 = new ViewButton();
		btn31.setName("房产资讯");
		btn31.setType("view");
		btn31.setUrl(Config.Base+"action/redirect.do?storeId=2&path=page/toMessage.do");

		ViewButton btn32 = new ViewButton();
		btn32.setName("高薪诚聘");
		btn32.setType("view");
		btn32.setUrl(Config.Base+"action/redirect.do?storeId=2&path=page/toMessage.do");
		
		ViewButton btn33 = new ViewButton();
		btn33.setName("房贷计算");
		btn33.setType("view");
		btn33.setUrl(Config.Base+"action/redirect.do?storeId=2&path=page/toMessage.do");
		
		ViewButton btn34 = new ViewButton();
		btn34.setName("购房必看");
		btn34.setType("view");
		btn34.setUrl("http://wx.jjr580.com/action/staichtml/gfbk.html");
		
		ViewButton btn35 = new ViewButton();
		btn35.setName("企业风采");
		btn35.setType("view");
		btn35.setUrl(Config.Base+"action/redirect.do?storeId=2&path=page/toMessage.do");

		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("VR视频看房");
		mainBtn1.setSub_button(new Button[] {btn11,btn12,btn13,btn14,btn15});
		
		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("在线委托");
		mainBtn2.setSub_button(new Button[] {btn21,btn22,btn23,btn24,btn25});
		
		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("房事秘籍");
		mainBtn3.setSub_button(new Button[] {btn31,btn32,btn33,btn34,btn35});
		
		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });
		return menu;
	}
}
