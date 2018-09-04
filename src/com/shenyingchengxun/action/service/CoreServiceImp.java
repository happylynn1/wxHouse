package com.shenyingchengxun.action.service;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shenyingchengxun.action.bean.StoreInfo;
import com.shenyingchengxun.action.dao.StoreInfoDao;
import com.shenyingchengxun.action.util.Config;
import com.shenyingchengxun.action.util.MessageUtil;
import com.shenyingchengxun.util.HttpUtil;

/**
 * 核心处理业务
 * 
 * @author 钮飞虎
 * 
 */
@Service("CoreServiceImp")
public class CoreServiceImp implements CoreService {

	@Resource
	StoreInfoService storeInfoServiceImp;

	@Resource
	ClientService clientServiceImp;

	@Resource
	MsgDistribute msgDistributeImp;

	@Autowired
	StoreInfoDao storeInfoDao;

	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public String processRequest(String storeId, String openid,
			Map<String, String> requestMap) {
		String respMessage = null;
		try {
			StoreInfo si = storeInfoServiceImp.getStoreInfoByStoreId(storeId);
			// 消息类型
			String msgType = requestMap.get("MsgType");
			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				return msgDistributeImp.forText(requestMap);
			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				return msgDistributeImp.forImage(requestMap);
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				return msgDistributeImp.forLocation(requestMap);
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				return msgDistributeImp.forTLink(requestMap);
			}
			// 音频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				return msgDistributeImp.forVoice(requestMap);
			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				return msgDistributeImp.forEvent(si, requestMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respMessage;
	}

	public String snsapi_base(String path, String openid, StoreInfo si) {
		// TODO Auto-generated method stub
		String redirect_uri = null;
		if (openid == null || "".equals(openid)) {
			String url = HttpUtil.urlEncodeUTF8(Config.Base + path);
			redirect_uri = "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid="
					+ si.getAppId()
					+ "&redirect_uri="
					+ url
					+ "&response_type=code&scope=snsapi_userinfo&state=1&connect_redirect=1#wechat_redirect";
		} else {
			redirect_uri = "redirect:" + Config.Base + path;
		}
		return redirect_uri;
	}

	/**
	 * 获取用户openid
	 * 
	 * @param request
	 * @return
	 */
	public String getOpenid(StoreInfo si, HttpServletRequest request) {
		String openid = request.getParameter("openid");
		System.out.println("--------------------------"+si.getAppId()+"---------------------------------------");
		System.out.println("--------------------------"+si.getAppSecret()+"---------------------------------------");
		if (openid == null || openid.equals("") || openid.equals("null")) {
			String code = request.getParameter("code");
			System.out.println("--------------------------"+code+"--------------------------------------");
			if (code != null) {
				String URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code"
						.replace("APPID", si.getAppId())
						.replace("SECRET", si.getAppSecret())
						.replace("CODE", code);
				JSONObject jsonObj = HttpUtil.httpRequest(URL, "GET", null);
				Object obj = jsonObj.get("openid");
				if(obj==null){
					openid =  (String) request.getSession().getAttribute("openid");
				}else{
					openid = jsonObj.get("openid").toString();
				}
				System.out.println("--------------------------"+openid+"---------------------------------------");
			}else{
				openid = (String) request.getSession().getAttribute("openid");
			}
		}
		System.out.println("-----------------------------------------------------------------");
		System.out.println("-----------------------------------------------------------------");
		request.getSession().setAttribute("openid", openid);
		return openid;
	}
}
