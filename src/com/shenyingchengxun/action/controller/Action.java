package com.shenyingchengxun.action.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shenyingchengxun.action.bean.StoreInfo;
import com.shenyingchengxun.action.service.ClientService;
import com.shenyingchengxun.action.service.CoreService;
import com.shenyingchengxun.action.service.StoreInfoService;
import com.shenyingchengxun.action.util.MessageUtil;
import com.shenyingchengxun.action.util.SignUtil;
import com.shenyingchengxun.util.HttpUtil;
import com.shenyingchengxun.util.SHA1Util;

/**
 * 微信访问入口(签名验证、动作判断)
 * 
 * @author niufeihu
 * 
 */
@Controller
@RequestMapping(value = "/action")
public class Action {

	@Resource
	CoreService coreServiceImp;
	@Resource
	ClientService clientServiceImp;
	@Resource
	StoreInfoService storeInfoServiceImp;
	
	/**
	 * 微信接入入口，微信验证和微信动作都会进入此接口
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/wechat", produces = "application/json;charset=UTF-8")
	public void wechat(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
			String storeId = request.getParameter("storeId");
			//微信验证
			if (request.getMethod().equals("GET")) {
				StoreInfo si = storeInfoServiceImp.getStoreInfoByStoreId(storeId);
				System.out.println(request.toString());
				String signature = request.getParameter("signature"), 
						timestamp = request.getParameter("timestamp"),
						nonce = request.getParameter("nonce"),
						echostr = request.getParameter("echostr");
				if (signature!=null&&timestamp!=null&&nonce!=null&&echostr!=null&&SignUtil.checkSignature(si.getToken(), signature, timestamp, nonce)) {
					out.write(echostr);
				}
			//微信动作
			} else {
				String respMessage = "";
				try {
					String openid = request.getParameter("FromUserName");
					Map<String, String> requestMap = MessageUtil.parseXml(request);
					respMessage = coreServiceImp.processRequest(storeId,openid,requestMap);
					out.write(respMessage);
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	@RequestMapping(value = "/redirect")
	public String redirect(HttpServletRequest request,
			HttpServletResponse response) {
		String path = request.getParameter("path");
		String storeId = request.getParameter("storeId");
		if(storeId!=null){
			request.getSession().setAttribute("storeId", storeId);
		}
		StoreInfo si = storeInfoServiceImp.getStoreInfoByStoreId(storeId);
		request.getSession().setAttribute("storeInfo", si);
		return coreServiceImp.snsapi_base(path,null,si);
	}
	
	@RequestMapping(value = "/getJswxapiconf.do")
	public void getJswxapiconf(HttpServletRequest request,
			HttpServletResponse response) throws IOException, DocumentException {
		String url = request.getParameter("url");
		
		String storeId = request.getParameter("storeId");
		if(storeId==null||storeId.equals("")||storeId.equals("null")){
			storeId = (String) request.getSession().getAttribute("storeId");
		}
		
		String nonceStrSe = storeId + "_nonceStr";
		String jsapi_ticket_se = storeId + "_jsapi_ticket";
		
		String nonceStr = (String) request.getSession().getAttribute(nonceStrSe);
		if (nonceStr==null||nonceStr.equals("")||nonceStr.equals("null")) {
			nonceStr = "";
			String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";   
		    Random random = new Random();   
		    for (int i = 0; i < 16; i++) {   
		        int number = random.nextInt(base.length());   
		        nonceStr+=(base.charAt(number));
		    }   
			request.getSession().setAttribute(nonceStrSe, nonceStr);
		}
		
		String jsapi_ticket = (String) request.getSession().getAttribute(jsapi_ticket_se);
		String string1="";
		StoreInfo si = storeInfoServiceImp.getStoreInfoByStoreId(storeId);
		String signature = "";
		if (jsapi_ticket==null||jsapi_ticket.equals("")) {
			JSONObject json =  HttpUtil.httpRequest("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+si.getAccessToken()+"&type=jsapi","GET",null);
			jsapi_ticket = (String) json.get("ticket");
			request.getSession().setAttribute(jsapi_ticket_se,jsapi_ticket);
		}
		
		string1 = "jsapi_ticket="+jsapi_ticket+"&noncestr="+nonceStr+"&timestamp="+si.getAtReg_time()+"&url="+url;
		signature = SHA1Util.hex_sha1(string1);
		Map<String,String> map = new HashMap<String,String>(); 
		map.put("timestamp", si.getAtReg_time()+"");
		map.put("nonceStr", nonceStr);
		map.put("signature", signature);
		map.put("appId", si.getAppId());
		JSONObject json_result = JSONObject.fromObject(map);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(json_result);
		response.getWriter().flush();
		response.getWriter().close();
	}
}
