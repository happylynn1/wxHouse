package com.shenyingchengxun.action.service;

import java.util.Date;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shenyingchengxun.action.bean.StoreInfo;
import com.shenyingchengxun.action.dao.StoreInfoDao;
import com.shenyingchengxun.action.util.Config;
import com.shenyingchengxun.util.HttpUtil;

@Service("StoreInfoServiceImp")
public class StoreInfoServiceImp implements StoreInfoService{
	
	@Autowired  StoreInfoDao storeInfoDao;
	
	public  StoreInfo getStoreInfoByStoreId(String storeId) {
		// TODO Auto-generated method stub
		StoreInfo si = storeInfoDao.getStoreInfoByStoreId(storeId);
		if(si.getAccessToken()==null||"".equals(si.getAccessToken())||((new Date().getTime()/1000-si.getAtReg_time())>3600)){
			String accessToken = Config.getAccessToken(si.appId,si.appSecret);
			si.setAccessToken(accessToken);
			si.setAtReg_time(new Date().getTime());
			if(accessToken!=null&&!"".equals(accessToken)&&storeId!=null&&!"".equals(storeId)){
				storeInfoDao.upStoreAtByStoreId(storeId,accessToken);
			}
		}else{
			/*JSONObject json =  HttpUtil.httpRequest("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+si.getAccessToken()+"&type=jsapi","GET",null);
			System.out.println("-----------------------getticket--------------------------------");
			System.out.println(json);
			if(json != null){
				String errcode = json.getString("errcode");
				System.out.println(errcode);
				if(!errcode.equals("0")){
					String accessToken = Config.getAccessToken(si.appId,si.appSecret);
					si.setAccessToken(accessToken);
					si.setAtReg_time(new Date().getTime());
					if(accessToken!=null&&!"".equals(accessToken)&&storeId!=null&&!"".equals(storeId)){
						storeInfoDao.upStoreAtByStoreId(storeId,accessToken);
					}
				}
			}*/
		}
		return si;
	}

	public void upStoreAtByStoreId(String storeId, String accessToken) {
		// TODO Auto-generated method stub
		if(accessToken!=null&&!"".equals(accessToken)&&storeId!=null&&!"".equals(storeId)){
			storeInfoDao.upStoreAtByStoreId(storeId, accessToken);
		}
	}

	@Override
	public String getWaterMarkByStoreid(String storeid) {
		// TODO Auto-generated method stub
		return storeInfoDao.getWaterMarkByStoreid(storeid);
	}
}
